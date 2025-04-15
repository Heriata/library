package deveducate.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BookDto {
    private String bookName;
    private String bookAuthor;
    private LocalDateTime publishedDate;
}
