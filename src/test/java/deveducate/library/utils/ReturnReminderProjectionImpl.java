package deveducate.library.utils;

import deveducate.library.repositories.projections.ReturnReminderProjection;

import java.time.LocalDate;

public class ReturnReminderProjectionImpl implements ReturnReminderProjection {
    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getUserFullName() {
        return "";
    }

    @Override
    public String getUserActive() {
        return "";
    }

    @Override
    public String getUserEmail() {
        return "";
    }

    @Override
    public LocalDate getTakenDate() {
        return LocalDate.now();
    }

    @Override
    public String getBookName() {
        return "";
    }

    @Override
    public String getBookAuthor() {
        return "";
    }

    @Override
    public LocalDate getPublishingDate() {
        return LocalDate.now();
    }
}
