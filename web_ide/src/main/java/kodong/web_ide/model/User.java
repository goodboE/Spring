package kodong.web_ide.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Users")
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Submit> submits = new ArrayList<>();
}
