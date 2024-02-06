package kodong.web_ide.model;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Submit {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROBLEM_ID")
    private Problem problem;

    @Lob
    private String submitCode;

}
