package com.stella.rememberall.tripLog.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TripLogWebRequestDto {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

}
