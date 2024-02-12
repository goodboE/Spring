package kodong.web_ide.service;


import com.google.gson.Gson;
import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.TestCase;
import kodong.web_ide.model.dto.RunResult;
import kodong.web_ide.model.dto.SubmitResult;
import kodong.web_ide.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TestCaseService {

    private final CompileBuilder compileBuilder;
    private final TestCaseRepository testCaseRepository;

    // 히든 테케만 가져옴
    public List<TestCase> findHiddenTestCasesById(Long problemId) {
        return testCaseRepository.findAll().stream()
                .filter(tc -> tc.getProblem().getId().equals(problemId))
                .filter(tc -> tc.getHidden().equals(true))
                .collect(Collectors.toList());
    }

    // 공개 테케만 가져옴 (제출X, 실행)
    public List<TestCase> findOpenTestCaseById(Long problemId) {
        return testCaseRepository.findAll().stream()
                .filter(tc -> tc.getProblem().getId().equals(problemId))
                .filter(tc -> tc.getHidden().equals(false))
                .collect(Collectors.toList());
    }

    public ArrayList<RunResult> compileAndRun(Long problemId, String inputCode) throws Exception {
        ArrayList<RunResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);

        // todo 컴파일 실패 에러 메세지 출력
        if (obj instanceof String) {
            log.info("obj is String!");
            returnList.add(new RunResult(obj.toString()));
            return returnList;
        }

        List<TestCase> testCases = findOpenTestCaseById(problemId);
        for (TestCase testCase : testCases) {

            long beforeTime = System.currentTimeMillis();
            String[] inputs = testCase.getInput().split("#");
            String[] resultDtoInput = new String[inputs.length];
            Class[] methodParamClass = new Class[inputs.length];
            Object[] methodParamObject = new Object[inputs.length];

            int i = 0;
            String rr;
            
            for (String input : inputs) {
                String[] _input = input.split(":");
                switch (_input[0]) {
                    case "int" -> {
                        methodParamClass[i] = int.class;
                        methodParamObject[i++] = Integer.parseInt(_input[1]);
                    }

                    case "String" -> {
                        methodParamClass[i] = String.class;
                        methodParamObject[i++] = _input[1];
                    }

                    case "List" -> {
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }

                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            if (result.toString().equals(testCase.getOutput())) {
                rr = "테스트를 통과하였습니다.";
            } else {
                rr = String.format("실행한 결괏값 %s이 기댓값 %s과 다릅니다.", result, testCase.getOutput());
            }

            // t[1]: 입력값, output: 기댓값, result: 결과값
            returnList.add(new RunResult(resultDtoInput, testCase.getOutput(), rr, (afterTime - beforeTime)));
        }


        return returnList;
    }


    public List<SubmitResult> submit(Long problemId, String inputCode) throws Exception {
        List<SubmitResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);
        if (obj instanceof String) {
            returnList.add(new SubmitResult("컴파일 에러", null));
            return returnList;
        }

        List<TestCase> testCases = findHiddenTestCasesById(problemId);
        for (TestCase testCase : testCases) {

            long beforeTime = System.currentTimeMillis();
            String[] inputs = testCase.getInput().split("#");
            String[] resultDtoInput = new String[inputs.length];
            Class[] methodParamClass = new Class[inputs.length];
            Object[] methodParamObject = new Object[inputs.length];

            int i = 0;
            // int successCount = 0;
            String rr;

            for (String input : inputs) {
                String[] _input = input.split(":");
                switch (_input[0]) {
                    case "int" -> {
                        methodParamClass[i] = int.class;
                        methodParamObject[i++] = Integer.parseInt(_input[1]);
                    }

                    case "String" -> {
                        methodParamClass[i] = String.class;
                        methodParamObject[i++] = _input[1];
                    }

                    case "List" -> {
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }

                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            if (result.toString().equals(testCase.getOutput())) {
                rr = String.format("통과 (수행시간 : %s)", (afterTime - beforeTime));
                // successCount++;
            } else {
                rr = String.format("실패 (수행시간 : %s)", (afterTime - beforeTime));
            }

            // t[1]: 입력값, output: 기댓값, result: 결과값
            returnList.add(new SubmitResult(null, rr));
        }
        return returnList;
    }

}
