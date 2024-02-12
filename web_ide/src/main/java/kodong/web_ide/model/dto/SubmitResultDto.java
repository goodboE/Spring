package kodong.web_ide.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmitResultDto<T> {

    // 공개 테케 실행 리스트 (입력값, 기댓값, 실행 결과)
    private T runResult;

    // 비공개 테케 실행 리스트 Ex) 통과 (0.02ms, 73MB)
    private T submitResult;

    // Ex) 2개 중 2개 성공
    // private String result;
}
