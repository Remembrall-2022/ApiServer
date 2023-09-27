package com.stella.rememberall.tripLog.entity;

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
        this.title = titleIfNotEmpty(title);
        this.tripStartDate = startDateIfEarlierThanEndDate(tripStartDate, tripEndDate);
        this.tripEndDate = endDateIfLaterThanEndDate(tripStartDate, tripEndDate);
        this.userId = userIdIfNotNull(userId);
    }

    public void updateTitle(final String title) {
        this.title = titleIfNotEmpty(title);
    }

    public void updateStartDate(final LocalDate tripStartDate) {
        this.tripStartDate = startDateIfEarlierThanEndDate(tripStartDate, tripEndDate);
    }

    public void updateEndDate(final LocalDate tripEndDate) {
        this.tripEndDate = endDateIfLaterThanEndDate(tripStartDate, tripEndDate);
    }


    private Long userIdIfNotNull(Long userId) {
        if (userId != null) return userId;
        throw new IllegalArgumentException("userId은 null일 수 없습니다.");
    }

    private static LocalDate startDateIfEarlierThanEndDate(LocalDate startDate, LocalDate endDate) {
        checkDatesAreNotNull(startDate, endDate);
        if (startDate.isBefore(endDate) || startDate.isEqual(endDate)) return startDate;
        throw new IllegalArgumentException(String.format("startDate는 endDate보다 늦을 수 없습니다. startDate : %s, endDate : %s", startDate.toString(), endDate.toString()));
    }

    private static LocalDate endDateIfLaterThanEndDate(LocalDate startDate, LocalDate endDate) {
        checkDatesAreNotNull(startDate, endDate);
        if (endDate.isAfter(startDate) || endDate.isEqual(startDate)) return endDate;
        throw new IllegalArgumentException(String.format("endDate는 startDate보다 빠를 수 없습니다. startDate : %s, endDate : %s", startDate.toString(), endDate.toString()));
    }

    private static void checkDatesAreNotNull(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null)
            throw new IllegalArgumentException("startDate 혹은 endDate는 null일 수 없습니다.");
    }

    private static String titleIfNotEmpty(String title) {
        if (title != null && !title.isEmpty()) return title;
        throw new IllegalArgumentException("title은 빈 값일 수 없습니다.");
    }

}
