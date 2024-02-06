package kodong.web_ide.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Problem {

    @Id @GeneratedValue
    @Column(name = "PROBLEM_ID")
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "problem")
    private List<Submit> submits = new ArrayList<>();

    @OneToMany(mappedBy = "problem")
    private List<TestCase> testCases = new ArrayList<>();

    @Lob
    private String solutionCode;

    public Problem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }
}
