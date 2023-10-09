package com.stella.rememberall.diary.tripLog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "trip_log")
@Entity
public class TripLog extends BaseTimeEntity {
    @Id
    @Column(name = "trip_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=1)
    private String title;

    @Column(name = "trip_start_date")
    private LocalDate tripStartDate;

    @Column(name = "trip_end_date")
    private LocalDate tripEndDate;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Builder
    public TripLog(final String title, final LocalDate tripStartDate, final LocalDate tripEndDate, final Long userId){
        TripLog.checkParamsValid(title, tripStartDate, tripEndDate, userId);
        this.title = title;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.userId = userId;
    }

    private static void checkParamsValid(String title, LocalDate tripStartDate, LocalDate tripEndDate, Long userId) {
        TripLog.checkTitleNotEmpty(title);
        TripLog.checkStartDateIsEarlierThanEndDate(tripStartDate, tripEndDate);
        TripLog.checkEndDateIsLaterThanStartDate(tripStartDate, tripEndDate);
        TripLog.checkUserIdIsNotNull(userId);
    }

    public void updateTitle(final String title) {
        TripLog.checkTitleNotEmpty(title);
        if (!this.title.equals(title))
            this.title = title;
    }

    public void updateStartDate(final LocalDate tripStartDate) {
        TripLog.checkStartDateIsEarlierThanEndDate(tripStartDate, this.tripEndDate);
        if (!this.tripStartDate.isEqual(tripStartDate))
            this.tripStartDate = tripStartDate;
    }

    public void updateEndDate(final LocalDate tripEndDate) {
        TripLog.checkEndDateIsLaterThanStartDate(this.tripStartDate, tripEndDate);
        if (!this.tripEndDate.isEqual(tripEndDate))
            this.tripEndDate = tripEndDate;
    }

    private static void checkTitleNotEmpty(String title) {
        if (title == null || title.isEmpty())
            throw new IllegalArgumentException("title은 빈 값일 수 없습니다.");
    }

    private static void checkUserIdIsNotNull(Long userId) {
        if (userId == null)
            throw new IllegalArgumentException("userId은 null일 수 없습니다.");
    }

    private static void checkDateIsNotNull(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException("startDate 혹은 endDate는 null일 수 없습니다.");
    }

    private static void checkStartDateIsEarlierThanEndDate(LocalDate startDate, LocalDate endDate) {
        TripLog.checkDateIsNotNull(startDate);
        TripLog.checkDateIsNotNull(endDate);
        if (startDate.isBefore(endDate) || startDate.isEqual(endDate)) return;
        throw new IllegalArgumentException(String.format("startDate는 endDate보다 늦을 수 없습니다. startDate : %s, endDate : %s", startDate.toString(), endDate.toString()));
    }

    private static void checkEndDateIsLaterThanStartDate(LocalDate startDate, LocalDate endDate) {
        TripLog.checkDateIsNotNull(startDate);
        TripLog.checkDateIsNotNull(endDate);
        if (endDate.isAfter(startDate) || endDate.isEqual(startDate)) return;
        throw new IllegalArgumentException(String.format("endDate는 startDate보다 빠를 수 없습니다. startDate : %s, endDate : %s", startDate.toString(), endDate.toString()));
    }

}
