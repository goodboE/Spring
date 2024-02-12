package kodong.web_ide.controller;


import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.dto.RunResult;
import kodong.web_ide.model.dto.RunResultDto;
import kodong.web_ide.model.dto.SubmitResult;
import kodong.web_ide.model.dto.SubmitResultDto;
import kodong.web_ide.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CompileController {

    private final TestCaseService testCaseService;

    @PostMapping("/{problemId}")
    public ResponseEntity<RunResultDto> test(@RequestBody Map<String, Object> input, @PathVariable(name = "problemId") Long problemId) throws Exception {
        ArrayList<RunResult> result = testCaseService.compileAndRun(problemId, input.get("code").toString());
        return ResponseEntity.ok(new RunResultDto(result));
    }

    // todo 제출 -> 공개테케 + 히든테케 + 테스트결과 + 채점 결과
    @PostMapping("/{problemId}/submit")
    public ResponseEntity<SubmitResultDto> submit(@RequestBody Map<String, Object> input, @PathVariable(name = "problemId") Long problemId) throws Exception {
        ArrayList<RunResult> result = testCaseService.compileAndRun(problemId, input.get("code").toString());
        List<SubmitResult> submits = testCaseService.submit(problemId, input.get("code").toString());
        return ResponseEntity.ok(new SubmitResultDto(result, submits));
    }
}
