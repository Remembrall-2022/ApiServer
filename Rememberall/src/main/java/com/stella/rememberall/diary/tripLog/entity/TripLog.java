package com.stella.rememberall.diary.tripLog.entity;

import com.stella.rememberall.diary.common.BaseTimeEntity;
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
        TripLogValidation.checkParamsValid(title, tripStartDate, tripEndDate, userId);
        this.title = title;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.userId = userId;
    }

    public void updateTitle(final String title) {
        TripLogValidation.checkTitleNotEmpty(title);
        if (!this.title.equals(title))
            this.title = title;
    }

    public void updateStartDate(final LocalDate tripStartDate) {
        TripLogValidation.checkStartDateIsEarlierThanEndDate(tripStartDate, this.tripEndDate);
        if (!this.tripStartDate.isEqual(tripStartDate))
            this.tripStartDate = tripStartDate;
    }

    public void updateEndDate(final LocalDate tripEndDate) {
        TripLogValidation.checkEndDateIsLaterThanStartDate(this.tripStartDate, tripEndDate);
        if (!this.tripEndDate.isEqual(tripEndDate))
            this.tripEndDate = tripEndDate;
    }

}
