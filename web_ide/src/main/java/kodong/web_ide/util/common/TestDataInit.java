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

        problemRepository.save(problem);
        problemRepository.save(problem2);

        // TestCase
        // @Text 다 보니 전부 String,
        TestCase testCase1 = new TestCase("int:12", "28", false);
        TestCase testCase2 = new TestCase("int:5", "6", false);
        TestCase testCase3 = new TestCase("int:0", "0", false);
        TestCase testCase6 = new TestCase("int:17", "18", true);
        TestCase testCase7 = new TestCase("int:12", "28", true);
        TestCase testCase8 = new TestCase("int:5", "6", true);
        TestCase testCase9 = new TestCase("int:0", "0", true);

        TestCase testCase4 = new TestCase("List:[1,4,2]#List:[5,4,4]", "29", false);
        TestCase testCase5 = new TestCase("List:[1,2]#List:[3,4]", "10", false);

        testCase1.setProblem(problem);
        testCase2.setProblem(problem);
        testCase3.setProblem(problem);
        testCase6.setProblem(problem);
        testCase7.setProblem(problem);
        testCase8.setProblem(problem);
        testCase9.setProblem(problem);

        testCase4.setProblem(problem2);
        testCase5.setProblem(problem2);

        testCaseRepository.save(testCase1);
        testCaseRepository.save(testCase2);
        testCaseRepository.save(testCase3);
        testCaseRepository.save(testCase6);
        testCaseRepository.save(testCase7);
        testCaseRepository.save(testCase8);
        testCaseRepository.save(testCase9);

        testCaseRepository.save(testCase4);
        testCaseRepository.save(testCase5);

    }
}
