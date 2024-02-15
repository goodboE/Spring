package kodong.web_ide.repository;

import kodong.web_ide.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByProblemIdAndHiddenIsTrue(Long problemId);
    List<TestCase> findByProblemIdAndHiddenIsFalse(Long problemId);
}
