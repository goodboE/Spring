package kodong.web_ide.execute;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class MethodExecutation {

    private final static long TIMEOUT_LONG = 15_000;

    public static Map<String, Object> timeOutCall(Object obj, String methodName, Object[] params, Class[] arguments) throws Exception {

        log.info("timeoutcall");

        // return Map
        Map<String, Object> returnMap = new HashMap<>();

        // Source 를 만들 때 지정한 Method
        Method objMethod;
//        if (arguments.length == 1)
//            objMethod = obj.getClass().getMethod(methodName, arguments[0]);
//        else if (arguments.length == 2)
//            objMethod = obj.getClass().getMethod(methodName, arguments[0], arguments[1]);
//        else
//            objMethod = obj.getClass().getMethod(methodName);
        objMethod = obj.getClass().getMethod(methodName, arguments);
        log.info("objMethod: {}", objMethod);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<Map<String, Object>> task = () -> {
            Map<String, Object> callMap = new HashMap<>();

            callMap.put("return", objMethod.invoke(obj, params));

            callMap.put("result", true);
            return callMap;
        };

        log.info("[MethodExecution] task = {}", task);

        Future<Map<String, Object>> future = executorService.submit(task);

        try {
            returnMap = future.get(TIMEOUT_LONG, TimeUnit.MICROSECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            returnMap.put("result", false);
        } finally {
            executorService.shutdown();
        }

        return returnMap;
    }
}
