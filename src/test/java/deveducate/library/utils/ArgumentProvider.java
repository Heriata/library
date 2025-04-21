package deveducate.library.utils;

import org.junit.jupiter.params.provider.Arguments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class ArgumentProvider {

    private static final String BOOK_AUTHOR_1 = "bookAuthor1";
    private static final String BOOK_AUTHOR_2 = "bookAuthor2";
    private static final String BOOK_NAME_1 = "bookName1";
    private static final String BOOK_NAME_2 = "bookName2";
    private static final String USERNAME_1 = "username1";
    private static final String USERNAME_2 = "username2";
    private static final String USER_FULL_NAME_1 = "userFullName1";
    private static final String USER_FULL_NAME_2 = "userFullName2";
    private static final String SAME_BOOK_AUTHOR = "sameAuthor";
    private static final String SAME_BOOK_NAME = "sameName";
    private static final String SAME_USERNAME = "sameUsername";

    public static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("{data:[{\"username\":\"mboyseqw\",\"userFullName\":\"Eada Lattimer\",\"userActive\":true,\"bookName\":\"The Da Vinci Code\",\"bookAuthor\":\"Melba Boyse\"}]}")
        );
    }

    public static Stream<Arguments> provideSubscriptionBookDtoList() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                TestUtils.getSubscriptionBookDto(USERNAME_1, USER_FULL_NAME_1, BOOK_NAME_1, BOOK_AUTHOR_1),
                                TestUtils.getSubscriptionBookDto(USERNAME_2, USER_FULL_NAME_2, BOOK_NAME_2, BOOK_AUTHOR_2)
                        )
                )
        );
    }

    public static Stream<Arguments> provideSubscriptionBookDtoListWithBookDuplicates() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                TestUtils.getSubscriptionBookDto(USERNAME_1, USER_FULL_NAME_1, SAME_BOOK_NAME, SAME_BOOK_AUTHOR),
                                TestUtils.getSubscriptionBookDto(USERNAME_2, USER_FULL_NAME_2, SAME_BOOK_NAME, SAME_BOOK_AUTHOR)
                        )
                )
        );
    }

    public static Stream<Arguments> provideSubscriptionBookDtoListWithUsernameDuplicates() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                TestUtils.getSubscriptionBookDto(SAME_USERNAME, USER_FULL_NAME_1, BOOK_NAME_1, BOOK_AUTHOR_1),
                                TestUtils.getSubscriptionBookDto(SAME_USERNAME, USER_FULL_NAME_2, BOOK_NAME_2, BOOK_AUTHOR_2)
                        )
                )
        );
    }
}
