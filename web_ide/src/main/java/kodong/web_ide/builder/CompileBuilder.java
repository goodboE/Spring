package kodong.web_ide.builder;


import kodong.web_ide.execute.MethodExecutation;
import kodong.web_ide.model.result.ApiResponseResult;
import kodong.web_ide.util.common.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CompileBuilder {

    private final String path = "C:/Users/ko/Desktop/test/compile/";

    public Object compileCode(String body) throws Exception {
        String uuid = UUIDUtil.createUUID();
        String uuidPath = path + uuid + "/";

        File newFolder = new File(uuidPath);
        File sourceFile = new File(uuidPath + "DynamicClass.java");
        File classFile = new File(uuidPath + "DynamicClass.class");

        Class<?> clazz;

        ByteArrayOutputStream err= new ByteArrayOutputStream();
        PrintStream origErr = System.err;

        try {

            newFolder.mkdir();
            new FileWriter(sourceFile).append(body).close();

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            System.setErr(new PrintStream(err));

            // compile
            int compileResult = compiler.run(null, null, null, sourceFile.getPath());
            if (compileResult == 1) {
                return err.toString();
            }

            // compile 된 클래스 가져오기
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {new File(uuidPath).toURI().toURL()});
            clazz = Class.forName("DynamicClass", true, classLoader);
            log.error("[CompileBuilder] 컴파일 완료 :: {}", clazz);

            return clazz.getConstructor().newInstance();

        } catch (Exception e) {
            log.error("[CompileBuilder] 소스 컴파일 중 에러 발생 :: {}", e.getMessage());
            return null;

        } finally {
            // system error stream 원상태로 전환
            System.setErr(origErr);

            if (sourceFile.exists())
                sourceFile.delete();
            if (classFile.exists())
                classFile.delete();
            if (newFolder.exists())
                newFolder.delete();
        }

    }


    public Map<String, Object> runObject(Object obj, Object[] params) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        // 실행할 메소드 명
        String methodName = "runMethod";

        log.info("params: {}", params);

        // 파라미터 타입 개수만큼 지정
        Class[] arguments = new Class[params.length];
        for(int i = 0; i < params.length; i++) {
            arguments[i] = params[i].getClass();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream originOut = System.out;
        PrintStream originErr = System.err;

        log.info("arguments: {}", arguments);

        try {
            log.info("[1]");
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));
            log.info("[2]");

            // timeout 을 체크하며 실행, 15초 초과시 강제 종료
            Map<String, Object> result;
            result = MethodExecutation.timeOutCall(obj, methodName, params, arguments);

            log.info("[CompileBuilder, runObject] result = {}", result);

            // stream 정보 저장
            if ((Boolean) result.get("result")) {
                returnMap.put("result", ApiResponseResult.SUCCESS.getText());
                returnMap.put("return", result.get("return"));
                if(err.toString() != null && !err.toString().equals("")) {
                    returnMap.put("SystemOut", err.toString());
                }else {
                    returnMap.put("SystemOut", out.toString());
                }
            }
            else {
                returnMap.put("result", ApiResponseResult.FAIL.getText());
                if(err.toString() != null && !err.toString().equals("")) {
                    returnMap.put("SystemOut", err.toString());
                }else {
                    returnMap.put("SystemOut", "제한 시간 초과");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setOut(originOut);
            System.setErr(originErr);
        }
        return returnMap;
    }
}
