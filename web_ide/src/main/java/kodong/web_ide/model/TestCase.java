package kodong.web_ide.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TestCase {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROBLEM_ID")
    private Problem problem;

    @Column(columnDefinition = "TEXT")
    private String input;

    @Column(columnDefinition = "TEXT")
    private String output;

    private Boolean hidden;

    public void setProblem(Problem problem) {
        this.problem = problem;
        problem.getTestCases().add(this);
    }

    public TestCase(String input, String output, Boolean hidden) {
        this.input = input;
        this.output = output;
        this.hidden = hidden;
    }
}
