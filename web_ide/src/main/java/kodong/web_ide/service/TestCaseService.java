package kodong.web_ide.service;


import com.google.gson.Gson;
import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.TestCase;
import kodong.web_ide.model.dto.ParamDto;
import kodong.web_ide.model.dto.RunResult;
import kodong.web_ide.model.dto.SubmitResult;
import kodong.web_ide.model.dto.TestcaseDto;
import kodong.web_ide.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TestCaseService {

    private final CompileBuilder compileBuilder;
    private final TestCaseRepository testCaseRepository;

    public ArrayList<RunResult> compileAndRun(Long problemId, String inputCode) throws Exception {
        ArrayList<RunResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);

        if (obj instanceof String) {
            log.info("obj is String!");
            returnList.add(new RunResult(obj.toString()));
            return returnList;
        }

        List<TestCase> testCases = testCaseRepository.findByProblemIdAndHiddenIsFalse(problemId);
        for (TestCase testCase : testCases) {
            boolean passed = false;
            long beforeTime = System.currentTimeMillis();
            String[] inputs = testCase.getInput().split("#");
            String[] resultDtoInput = new String[inputs.length];
            Class[] methodParamClass = new Class[inputs.length];
            Object[] methodParamObject = new Object[inputs.length];

            int i = 0;
            String rr;
            
            for (String input : inputs) {
                String[] _input = input.split(":");
                log.info("_input[0] : {}", _input[0]);
                switch (_input[0]) {

                    case "int" -> {
                        methodParamClass[i] = int.class;
                        methodParamObject[i++] = Integer.parseInt(_input[1]);
                    }

                    case "String" -> {
                        methodParamClass[i] = String.class;
                        methodParamObject[i++] = _input[1];
                    }

                    case "int[]" -> {
                        log.info("int[]");
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }

                    case "int[][]" -> {
                        methodParamClass[i] = int[][].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[][].class);
                    }

                    case "long" -> {
                        methodParamClass[i] = long.class;
                        methodParamObject[i++] = Long.parseLong(_input[1]);
                    }

                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            String[] outputTypeAndValue = testCase.getOutput().split(":");
            if (result.toString().equals(outputTypeAndValue[1])) {
                rr = "테스트를 통과하였습니다.";
                passed = true;
            } else {
                rr = String.format("실행한 결괏값 %s이 기댓값 %s과 다릅니다.", result, testCase.getOutput());
            }

            returnList.add(new RunResult(resultDtoInput, testCase.getOutput(), rr, (afterTime - beforeTime), passed));
        }


        return returnList;
    }


    public List<SubmitResult> submit(Long problemId, String inputCode) throws Exception {
        List<SubmitResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);
        if (obj instanceof String) {
            returnList.add(new SubmitResult("컴파일 에러", null, false));
            return returnList;
        }

        // todo 쿼리메소드
        List<TestCase> testCases = testCaseRepository.findByProblemIdAndHiddenIsTrue(problemId);
        for (TestCase testCase : testCases) {
            boolean passed = false;
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
                    case "int[]" -> {
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }
                    case "int[][]" -> {
                        methodParamClass[i] = int[][].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[][].class);

                    }
                    case "String[][]" -> {
                        methodParamClass[i] = String[][].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], String[][].class);

                    }
                    case "long" -> {
                        methodParamClass[i] = long.class;
                        methodParamObject[i++] = Long.parseLong(_input[1]);
                    }
                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            String[] outputTypeAndValue = testCase.getOutput().split(":");
            if (result.toString().equals(outputTypeAndValue[1])) {
                rr = String.format("통과 (수행시간 : %s)", (afterTime - beforeTime));
                passed = true;
                // successCount++;
            } else {
                rr = String.format("실패 (수행시간 : %s)", (afterTime - beforeTime));
            }

            // t[1]: 입력값, output: 기댓값, result: 결과값
            returnList.add(new SubmitResult(null, rr, passed));
        }
        return returnList;
    }

    // 공개 테스트 케이스의 첫 번째 값에 대해 타입, 테케 전송
    // params
    public ParamDto getParamTypesAndTestCases(Long problemId) {
        List<TestCase> testCases = testCaseRepository.findByProblemIdAndHiddenIsFalse(problemId);
        // params : { int[][], int, int }

        TestcaseDto testcaseDto = new TestcaseDto();
        List<TestcaseDto> testcaseValues = new ArrayList<>();

        for (TestCase testCase : testCases) {
            String[] testcaseValueAndType = testCase.getOutput().split(":");
            String[] inputs = testCase.getInput().split("#");

            List<String> testcaseType = new ArrayList<>();
            List<String> testcaseValue = new ArrayList<>();

            for (String input : inputs) {
                String[] _input = input.split(":");
                testcaseType.add(_input[0]);
                testcaseValue.add(_input[1]);
            }

            if (testcaseDto.getInput() == null) {
                testcaseDto.setInput(testcaseType);
                testcaseDto.setExpected(testcaseValueAndType[0]);
            }


            testcaseValues.add(new TestcaseDto(testcaseValue,testcaseValueAndType[1]));
        }


        return new ParamDto(testcaseDto, testcaseValues);
    }
}
