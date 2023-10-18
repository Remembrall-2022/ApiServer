package com.stella.rememberall.diary.common;

import java.time.LocalDate;

public class DurationValidator implements Validator {
    public static boolean isDurationValid(LocalDate startDate, LocalDate endDate) {
        return startDate.isBefore(endDate) || startDate.isEqual(endDate);
    }
}
