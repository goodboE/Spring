package kodong.web_ide.controller;


import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.TestCase;
import kodong.web_ide.model.result.ApiResponseResult;
import kodong.web_ide.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompileController {

    private final CompileBuilder builder;
    private final TestCaseService testCaseService;

    @PostMapping("/compile/{problemId}")
    public Map<String, Object> compileCode(@RequestBody Map<String, Object> input, @PathVariable(name = "problemId") Long problemId) throws Exception {


        // 1. 사용자의 code 를 compile -> run


        Map<String, Object> returnMap = new HashMap<>();

        // compile input code
        // {"code" : Object} ?
        Object obj = builder.compileCode(input.get("code").toString());
        log.info("obj: {}", obj);

        // compile 결과 타입이 String 일 경우, compile 실패 후 메세지 반환으로 판단하여 처리 ?
        if (obj instanceof String) {
            log.info("type is string: compileError");
            returnMap.put("result", ApiResponseResult.FAIL.getText());
            returnMap.put("SystemOut", obj.toString());
            return returnMap;
        }

        // 시간 체크 시작
        long beforeTime = System.currentTimeMillis();

        // 예시 parameters
        String[] participants = new String[] {"marina", "joshua", "nikola", "vinko", "filipa"};
        String[] completions = new String[] {"joshua", "filipa", "marina", "nikola"};
        Object[] params = {participants, completions};

        // todo [testcases -> DB 에서 가져와야 함]
        // testcase 의 input 을 사용자 코드에 삽입 -> output 과 결과가 같은지.
        List<TestCase> testCases = testCaseService.findTestCasesById(problemId);


        // run input code
        Map<String, Object> output = builder.runObject(obj, params);
        log.info("[실행 후] output = {}", output);

        long afterTime = System.currentTimeMillis();

        // run 결과 저장
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
