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
        Problem problem2 = new Problem("최솟값 만들기", "추후 추가");
        Problem problem3 = new Problem("지형 편집", "추후 추가");
        Problem problem4 = new Problem("asdf", "추후 추가");

        problemRepository.save(problem);
        problemRepository.save(problem2);
        problemRepository.save(problem3);
        problemRepository.save(problem4);

        // TestCase
        // @Text 다 보니 전부 String,
        TestCase testCase1 = new TestCase("int:12", "int:28", false);
        TestCase testCase2 = new TestCase("int:5", "int:6", false);
        TestCase testCase3 = new TestCase("int:0", "int:0", false);

        TestCase testCase4 = new TestCase("int[]:[1,4,2]#int[]:[5,4,4]", "int:29", false);
        TestCase testCase5 = new TestCase("int[]:[1,2]#int[]:[3,4]", "int:10", false);

        TestCase testCase10 = new TestCase("int[][]:[[1,2],[2,3]]#int:3#int:2", "int:5", false);
        TestCase testCase11 = new TestCase("int[][]:[[4,4,3],[3,2,2],[2,1,0]]#int:5#int:3", "int:33", false);

        TestCase testCase12 = new TestCase("String:1 2 3 4", "String:1 4", false);
        TestCase testCase13 = new TestCase("String:-1 -2 -3 -4", "String:-4 -1", false);
        TestCase testCase14 = new TestCase("String:-1 -1", "String:-1 -1", false);


        testCase1.setProblem(problem);
        testCase2.setProblem(problem);
        testCase3.setProblem(problem);

        testCase4.setProblem(problem2);
        testCase5.setProblem(problem2);

        testCase10.setProblem(problem3);
        testCase11.setProblem(problem3);

        testCase12.setProblem(problem4);
        testCase13.setProblem(problem4);
        testCase14.setProblem(problem4);

        testCaseRepository.save(testCase1);
        testCaseRepository.save(testCase2);
        testCaseRepository.save(testCase3);

        testCaseRepository.save(testCase4);
        testCaseRepository.save(testCase5);

        testCaseRepository.save(testCase10);
        testCaseRepository.save(testCase11);

        testCaseRepository.save(testCase12);
        testCaseRepository.save(testCase13);
        testCaseRepository.save(testCase14);

    }
}
