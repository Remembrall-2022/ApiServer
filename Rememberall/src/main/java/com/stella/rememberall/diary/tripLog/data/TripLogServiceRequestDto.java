package com.stella.rememberall.diary.tripLog.data;

import com.stella.rememberall.diary.tripLog.entity.TripLog;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public final class TripLogServiceRequestDto {

    @NotNull private String title;
    @NotNull private LocalDate startDate;
    @NotNull private LocalDate endDate;
    @NotNull private final Long userId;

    public TripLog toEntity() {
        return TripLog.builder()
                .title(title)
                .tripStartDate(startDate)
                .tripEndDate(endDate)
                .userId(userId)
                .build();
    }

}
