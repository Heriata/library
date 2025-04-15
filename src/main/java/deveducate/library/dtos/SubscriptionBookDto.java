package deveducate.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SubscriptionBookDto {
    private String username;
    private String userFullName;
    private boolean userActive;
    private String bookName;
    private String bookAuthor;
}
