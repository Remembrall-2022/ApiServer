package com.stella.rememberall.diary.tripLog.entity;

import com.stella.rememberall.diary.common.DurationValidator;
import com.stella.rememberall.diary.common.Validator;

import java.time.LocalDate;

public class TripLogValidation {

    public static void checkParamsValid(String title, LocalDate tripStartDate, LocalDate tripEndDate, Long userId) {
        TripLogValidation.checkTitleNotEmpty(title);
        TripLogValidation.checkStartDateIsEarlierThanEndDate(tripStartDate, tripEndDate);
        TripLogValidation.checkEndDateIsLaterThanStartDate(tripStartDate, tripEndDate);
        TripLogValidation.checkUserIdIsNotNull(userId);
    }

    public static void checkTitleNotEmpty(String title) {
        if (Validator.isBlank(title))
            throw new IllegalArgumentException("title은 빈 값일 수 없습니다.");
    }

    public static void checkUserIdIsNotNull(Long userId) {
        if (Validator.isNull(userId))
            throw new IllegalArgumentException("userId은 null일 수 없습니다.");
    }

    public static void checkDateIsNotNull(LocalDate date) {
        if (Validator.isNull(date))
            throw new IllegalArgumentException("startDate 혹은 endDate는 null일 수 없습니다.");
    }

    public static void checkStartDateIsEarlierThanEndDate(LocalDate startDate, LocalDate endDate) {
        TripLogValidation.checkDateIsNotNull(startDate);
        TripLogValidation.checkDateIsNotNull(endDate);
        if (!DurationValidator.isDurationValid(startDate, endDate))
            throw new IllegalArgumentException(String.format("startDate는 endDate보다 늦을 수 없습니다. startDate : %s, endDate : %s", startDate.toString(), endDate.toString()));
    }

    public static void checkEndDateIsLaterThanStartDate(LocalDate startDate, LocalDate endDate) {
        TripLogValidation.checkDateIsNotNull(startDate);
        TripLogValidation.checkDateIsNotNull(endDate);
        if (!DurationValidator.isDurationValid(startDate, endDate))
            throw new IllegalArgumentException(String.format("endDate는 startDate보다 빠를 수 없습니다. startDate : %s, endDate : %s", startDate.toString(), endDate.toString()));
    }

}
