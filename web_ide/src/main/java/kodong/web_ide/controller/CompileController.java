package kodong.web_ide.controller;


import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.TestCase;
import kodong.web_ide.model.dto.*;
import kodong.web_ide.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CompileController {

    private final TestCaseService testCaseService;

    @PostMapping("/{problemId}")
    public ResponseEntity<RunResultDto> run(@RequestBody Map<String, Object> input, @PathVariable(name = "problemId") Long problemId) throws Exception {
        ArrayList<RunResult> result = testCaseService.compileAndRun(problemId, input.get("code").toString());
        return ResponseEntity.ok(new RunResultDto("run", result));
    }

    @PostMapping("/{problemId}/submit")
    public ResponseEntity<SubmitResultDto> submit(@RequestBody Map<String, Object> input, @PathVariable(name = "problemId") Long problemId) throws Exception {
        ArrayList<RunResult> result = testCaseService.compileAndRun(problemId, input.get("code").toString());
        List<SubmitResult> submits = testCaseService.submit(problemId, input.get("code").toString());
        return ResponseEntity.ok(new SubmitResultDto("submit", result, submits));
    }

    @GetMapping("/{problemId}/testcase")
    public ResponseEntity<ParamDto> getParamAndTypes(@PathVariable(name = "problemId") Long problemId) {
        return ResponseEntity.ok(testCaseService.getParamTypesAndTestCases(problemId));
    }
}
