package deveducate.library.repositories.projections;

import java.time.LocalDate;

public interface ReturnReminderProjection {

    String getUsername();

    String getUserFullName();

    String getUserActive();

    String getUserEmail();

    LocalDate getTakenDate();

    String getBookName();

    String getBookAuthor();

    LocalDate getPublishingDate();
}
