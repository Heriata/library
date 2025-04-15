package deveducate.library.repositories;

import deveducate.library.models.BookEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookEntryRepository extends JpaRepository<BookEntryEntity, Long> {

    BookEntryEntity findByBookBookId(Long book_bookId);
}
