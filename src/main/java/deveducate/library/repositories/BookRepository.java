package deveducate.library.repositories;

import deveducate.library.models.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    boolean existsByBookNameAndBookAuthor(String bookName, String author);

    BookEntity findByBookNameAndBookAuthor(String bookName, String bookAuthor);

//    @Query("select b from BookEntity b where b.bookName = ,, b.bookAuthor)")
//    List<BookEntity> findAllByBookNameAndAuthorName(@Param("param")List<Pair<String, String>> param);
}
