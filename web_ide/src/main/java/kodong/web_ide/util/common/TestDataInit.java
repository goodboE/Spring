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
        Problem problem2 = new Problem("미정", "미정");

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
        String solutionCode2 = "미정";

        problem.setSolutionCode(solutionCode);
        problem2.setSolutionCode(solutionCode2);

        problemRepository.save(problem);
        problemRepository.save(problem2);

        // TestCase
        // @Text 다 보니 전부 String,
        TestCase testCase1 = new TestCase("int:12", "int:28");
        TestCase testCase2 = new TestCase("int:5", "int:6");
        TestCase testCase3 = new TestCase("int:0", "int:0");

        TestCase testCase4 = new TestCase("list:[1,2,3]", "int:0");

//        TestCase testCase4 = new TestCase("[1,2,3]", "[1,2,3]");
//        TestCase testCase5 = new TestCase("[1,2,3], [4,5,6]", "[1,2,3]");

        testCase1.setProblem(problem);
        testCase2.setProblem(problem);
        testCase3.setProblem(problem);

        testCase4.setProblem(problem2);

        testCaseRepository.save(testCase1);
        testCaseRepository.save(testCase2);
        testCaseRepository.save(testCase3);

        testCaseRepository.save(testCase4);
//        testCaseRepository.save(testCase4);
//        testCaseRepository.save(testCase5);


    }
}
