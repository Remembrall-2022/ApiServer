package com.stella.rememberall.diary.tripLog.data;

import com.stella.rememberall.diary.tripLog.data.TripLogResponseDto;
import com.stella.rememberall.diary.tripLog.entity.TripLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripLogResponseDtoTest {

    @Test
    public void TripLog를_TripLogResponseDto로_변환() {
        TripLog tripLog = new TripLogProxy(1L, TripLog.builder()
                .title("제목")
                .tripStartDate(LocalDate.of(2023, 9, 15))
                .tripEndDate(LocalDate.of(2023, 9, 18))
                .userId(1L).build());

        TripLogResponseDto tripLogResponseDto = TripLogResponseDto.of(tripLog);

        assertEquals(tripLogResponseDto.getId(), 1L);
        assertEquals(tripLogResponseDto.getTitle(), "제목");
        assertEquals(tripLogResponseDto.getTripStartDate(), LocalDate.of(2023, 9, 15));
        assertEquals(tripLogResponseDto.getTripEndDate(), LocalDate.of(2023, 9, 18));
    }

    static class TripLogProxy extends TripLog {
        private Long id;

        public TripLogProxy(Long id, TripLog tripLog) {
            super(tripLog.getTitle(), tripLog.getTripStartDate(), tripLog.getTripEndDate(), tripLog.getUserId());
            this.id = id;
        }

        public TripLogProxy(Long id) {
            super();
            this.id = id;
        }

        public TripLogProxy() {
            super();
        }

        @Override
        public Long getId() {
            return this.id;
        }
    }

}
