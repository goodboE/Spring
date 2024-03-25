package ko.oauthwithjwt.repository;

import ko.oauthwithjwt.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT MAX(a.ref) FROM Article a")
    Long findMaxRef();

    @Query("SELECT MAX(a.step) FROM Article a WHERE a.ref = :ref")
    Long findMaxStep(@Param("ref") Long ref);

    @Query("SELECT SUM(a.childCount) FROM Article a WHERE a.ref = :ref")
    Long findChildSum(@Param("ref") Long ref);

    @Query(value = "UPDATE Article a " +
            "SET a.refOrder = a.refOrder + 1 " +
            "WHERE a.ref = :ref AND a.refOrder > :temp",
            nativeQuery = true
    )
    @Modifying(clearAutomatically = true)
    void updateRefOrderForSameRef(@Param("ref") Long ref, @Param("temp") Long temp);

}
