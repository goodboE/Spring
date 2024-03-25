package ko.oauthwithjwt.service;

import jakarta.persistence.EntityManager;
import ko.oauthwithjwt.dto.CustomOAuth2User;
import ko.oauthwithjwt.model.Article;
import ko.oauthwithjwt.model.User;
import ko.oauthwithjwt.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CustomOAuth2UserService userService;
    private final EntityManager em;

    @Transactional
    public void addArticle(Long parentId, String content) {

        User currentUser = userService.getCurrentUser();

        // 게시글
        if (parentId == null) {
            Long maxRef = Optional.ofNullable(articleRepository.findMaxRef()).orElse(0L);

            Article article = Article.builder()
                    .user(currentUser)
                    .content(content)
                    .ref(maxRef+1)
                    .step(0L)
                    .refOrder(0L)
                    .childCount(0L)
                    .parentId(0L)
                    .build();

            articleRepository.save(article);
        }
        // 댓글
        else {
            // 부모 글 데이터
            Article parentArticle = articleRepository.findById(parentId).orElseThrow();
            // 부모글의 자식 수 + 1

            boolean contains = em.contains(parentArticle);
            log.info("parentArticle1 영속상태 ? {}", contains);

            Article article = Article.builder()
                    .user(currentUser)
                    .content(content)
                    .ref(parentArticle.getRef())
                    .step(parentArticle.getStep() + 1)
                    .refOrder(getRefOrder(parentArticle.getStep(), parentArticle.getRefOrder(), parentArticle.getRef(), parentArticle.getChildCount()))
                    .childCount(0L)
                    .parentId(parentId)
                    .build();

            boolean contains2 = em.contains(parentArticle);
            log.info("parentArticle2 영속상태 ? {}", contains2);

            articleRepository.save(article);
            boolean contains3 = em.contains(parentArticle);
            log.info("parentArticle3 영속상태 ? {}", contains3);

            Article parentArticle2 = articleRepository.findById(parentId).orElseThrow();
            // 부모글의 자식 수 + 1
            parentArticle2.addChild();
        }
    }

    @Transactional
    private Long getRefOrder(Long step, Long parentRefOrder, Long ref, Long parentChild) {

        Long maxStep = Optional.ofNullable(articleRepository.findMaxStep(ref)).orElse(0L);
        Long childSum = Optional.ofNullable(articleRepository.findChildSum(ref)).orElse(0L);

        if (step < maxStep) {
            return childSum + 1L;
        }
        else if (step.equals(maxStep)) {
            articleRepository.updateRefOrderForSameRef(ref, parentRefOrder + parentChild);
            return parentRefOrder + parentChild + 1L;
        }
        else {
            articleRepository.updateRefOrderForSameRef(ref, parentRefOrder);
            return parentRefOrder + 1L;
        }

    }
}
