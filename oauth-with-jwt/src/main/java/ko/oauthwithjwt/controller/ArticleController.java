package ko.oauthwithjwt.controller;

import ko.oauthwithjwt.dto.ArticleRequestDto;
import ko.oauthwithjwt.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/article")
    public void addArticle(@RequestBody ArticleRequestDto articleRequestDto) {
        articleService.addArticle(null, articleRequestDto.getContent());
    }

    @PostMapping("/article/{commentId}")
    public void addComment(@PathVariable("commentId") Long commentId, @RequestBody ArticleRequestDto articleRequestDto) {
        articleService.addArticle(commentId, articleRequestDto.getContent());
    }

}
