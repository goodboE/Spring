package kodong.web_ide.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmitResult<T> {
    private String errorMsg;
    private String accuracyTest;

}
