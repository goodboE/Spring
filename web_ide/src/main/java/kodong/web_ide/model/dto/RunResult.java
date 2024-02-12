package kodong.web_ide.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RunResult<T> {
    private T input;
    private String expected;
    private String output;

    // new
    private long performance;
    private String errorMsg;

    public RunResult(T input, String expected, String output, long performance) {
        this.input = input;
        this.expected = expected;
        this.output = output;
        this.performance = performance;
    }

    public RunResult(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}


