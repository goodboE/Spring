package kodong.web_ide.util.common;


import jakarta.annotation.PostConstruct;
import kodong.web_ide.model.Problem;
import kodong.web_ide.model.TestCase;
import kodong.web_ide.model.User;
import kodong.web_ide.repository.ProblemRepository;
import kodong.web_ide.repository.SubmitRepository;
import kodong.web_ide.repository.TestCaseRepository;
import kodong.web_ide.repository.UserRepository;
import kodong.web_ide.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataInit {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    @PostConstruct
    public void dataInit() {

        // Users
        User user = new User();
        userRepository.save(user);

        // Problem
        Problem problem = new Problem("약수의 합", "n의 약수의 합을 출력하는 코드를 작성하시오. n은 0이상 3000이하인 정수입니다.");

        // Solution code
        String solutionCode = "class Solution {\n" +
                "    public int solution(int n) {\n" +
                "        int answer = 0;\n" +
                "        \n" +
                "        if (n == 0) {\n" +
                "            return 0;\n" +
                "        }\n" +
                "        \n" +
                "        for(int i = 1; i <= n; i++) {\n" +
                "            if (n % i == 0) {\n" +
                "                answer += i;\n" +
                "            }\n" +
                "        }\n" +
                "        return answer;\n" +
                "    }\n" +
                "}";
        problem.setSolutionCode(solutionCode);
        problemRepository.save(problem);

        // TestCase
        TestCase testCase1 = new TestCase("{'input' : '12'}", "{'output' : '28'}");
        TestCase testCase2 = new TestCase("{'input' : '5'}", "{'output' : '6'}");
        TestCase testCase3 = new TestCase("{'input' : '0'}", "{'output' : '0'}");

        testCase1.setProblem(problem);
        testCase2.setProblem(problem);
        testCase3.setProblem(problem);

        testCaseRepository.save(testCase1);
        testCaseRepository.save(testCase2);
        testCaseRepository.save(testCase3);


    }
}
