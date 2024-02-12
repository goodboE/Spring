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
import java.util.UUID;

@Slf4j
@Component
public class CompileBuilder {

    private final String path = "C:/Users/ko/Desktop/ide_path/";

    public Object compileCode(String body) throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String uuidPath = path + uuid + "/";

        File newFolder = new File(uuidPath);
        File sourceFile = new File(uuidPath + "Solution.java");

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
                log.info("compileResult: {}", compileResult);
                log.info("err.toString: {}", err);
                return err.toString();
            }

            // compile 된 클래스 가져오기
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {new File(uuidPath).toURI().toURL()});
            clazz = Class.forName("Solution", true, classLoader);
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
            if (newFolder.exists())
                newFolder.delete();
        }

    }

}
