package kodong.web_ide.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RunResultDto<T> {

    private String submitType;
    // 공개 테케 실행 리스트 (입력값, 기댓값, 실행 결과)
    private T runResult;

}
