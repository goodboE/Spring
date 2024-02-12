package kodong.web_ide.controller;


import kodong.web_ide.builder.CompileBuilder;
import kodong.web_ide.model.dto.RunResult;
import kodong.web_ide.model.dto.RunResultDto;
import kodong.web_ide.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
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

}
