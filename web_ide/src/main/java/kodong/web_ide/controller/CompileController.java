package kodong.web_ide.controller;


import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.result.ApiResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompileController {

    private final CompileBuilder builder;

    @PostMapping("/compile")
    public Map<String, Object> compileCode(@RequestBody Map<String, Object> input) throws Exception {

        Map<String, Object> returnMap = new HashMap<>();

        // compile input code
        // {"code" : Object} ?
        log.info("input.get(\"code\") = {}", input.get("code"));
        Object obj = builder.compileCode(input.get("code").toString());

        // compile 결과 타입이 String 일 경우, compile 실패 후 메세지 반환으로 판단하여 처리 ?
        if (obj instanceof String) {
            log.info("type is string");
            returnMap.put("result", ApiResponseResult.FAIL.getText());
            returnMap.put("SystemOut", obj.toString());
            return returnMap;
        }

        // 시간 체크 시작
        long beforeTime = System.currentTimeMillis();

        // parameters
        String[] participants = new String[] {"marina", "joshua", "nikola", "vinko", "filipa"};
        String[] completions = new String[] {"joshua", "filipa", "marina", "nikola"};
        Object[] params = {participants, completions};


        // run input code
        /**
         * @param
         * obj : compile 의 결과
         */
        Map<String, Object> output = builder.runObject(obj, params);
        log.info("[실행 후] output = {}", output);

        long afterTime = System.currentTimeMillis();

        // run 결과 저장..
        returnMap.putAll(output);

        

        // 소요 시간
        returnMap.put("performance", (afterTime - beforeTime));

        // s :: 결과 체크 :: //
        try {
            // 정답 결과가 null 이 아니고 "vinko" 가 아닌 경우 Fail
            if (returnMap.get("return") != null & returnMap.get("return").equals("vinko")) {
                returnMap.put("result", ApiResponseResult.FAIL.getText());
                returnMap.put("SystemOut", returnMap.get("SystemOut").toString() + "\r\n결과 기댓값이 일치하지 않습니다.");
            }

        } catch (Exception e) {
            returnMap.put("result", ApiResponseResult.FAIL.getText());
            returnMap.put("SystemOut", returnMap.get("SystemOut").toString() + "예상치 못한 오류로 검사에 실패했습니다.");
        }

        return returnMap;
    }


}
