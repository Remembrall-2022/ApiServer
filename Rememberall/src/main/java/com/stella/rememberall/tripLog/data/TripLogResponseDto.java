package com.stella.rememberall.tripLog.data;

import com.stella.rememberall.tripLog.entity.TripLog;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder @AllArgsConstructor
public class TripLogResponseDto {

    private Long id;
    private String title;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;

    public static TripLogResponseDto of(TripLog tripLog) {

        return TripLogResponseDto.builder()
                .id(tripLog.getId())
                .title(tripLog.getTitle())
                .tripStartDate(tripLog.getTripStartDate())
                .tripEndDate(tripLog.getTripEndDate())
                .build();
    }
}
