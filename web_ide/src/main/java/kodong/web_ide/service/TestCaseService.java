package kodong.web_ide.service;


import kodong.web_ide.model.TestCase;
import kodong.web_ide.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    // 문제에 맞는 tc 만 가져옴.
    public List<TestCase> findTestCasesById(Long problemId) {
        return testCaseRepository.findAll().stream()
                .filter(tc -> tc.getProblem().getId().equals(problemId))
                .collect(Collectors.toList());
    }

}
