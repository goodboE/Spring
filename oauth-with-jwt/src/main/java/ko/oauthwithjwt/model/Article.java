package ko.oauthwithjwt.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
    private Long ref;
    private Long step;
    private Long refOrder;
    private Long childCount;
    private Long parentId;

    @Builder
    public Article(String content, User user, Long ref, Long step, Long refOrder, Long childCount, Long parentId) {
        this.content = content;
        this.user = user;
        this.ref = ref;
        this.step = step;
        this.refOrder = refOrder;
        this.childCount = childCount;
        this.parentId = parentId;
    }

    public void addChild() {
        this.childCount++;
    }
}
