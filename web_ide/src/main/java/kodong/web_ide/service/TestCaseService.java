package kodong.web_ide.service;


import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.TestCase;
import kodong.web_ide.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
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

    // 문제에 맞는 tc 만 가져옴.
    public List<TestCase> findTestCasesById(Long problemId) {
        return testCaseRepository.findAll().stream()
                .filter(tc -> tc.getProblem().getId().equals(problemId))
                .collect(Collectors.toList());
    }

    public void compileAndRun(Long problemId, String inputCode) throws Exception {
        Object obj = compileBuilder.compileCode(inputCode);

        List<TestCase> testCases = findTestCasesById(problemId);
        for (TestCase testCase : testCases) {

            String[] tcs = testCase.getInput().split(",");

            Class[] methodParamClass = new Class[tcs.length];
            Object[] methodParamObject = new Object[tcs.length];
            int i = 0;

            for (String tc : tcs) {
                String[] t = tc.split(":");

                log.info("n = {}", t[1]);

                if (t[0].equals("int"))
                    methodParamClass[i] = int.class;

                else if (t[0].equals("String"))
                    methodParamClass[i] = String.class;

                else if (t[0].equals("List"))
                    methodParamClass[i] = List.class;

                // todo 수정
                methodParamObject[i] = Integer.parseInt(t[1]);
                i++;
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            log.info("result {}", result);
        }


    }



}
