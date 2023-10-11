package com.stella.rememberall.diary.tripLog.entity;

import com.stella.rememberall.diary.tripLog.entity.TripLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TripLogCreateTest {

    @Test
    public void 제목은_한글자_이상이다() {
        TripLog tripLog = TripLog.builder()
                .title("제목은 한 글자 이상이다")
                .tripStartDate(LocalDate.of(2023, 9, 15))
                .tripEndDate(LocalDate.of(2023, 9, 18))
                .userId(1L)
                .build();

        assertTrue(!tripLog.getTitle().isEmpty());
    }

    @Test
    public void 제목은_빈값일수_없다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLog emptyTitleTripLog = TripLog.builder()
                    .title("")
                    .tripStartDate(LocalDate.of(2023, 9, 15))
                    .tripEndDate(LocalDate.of(2023, 9, 18))
                    .userId(1L)
                    .build();
        });

        assertTrue(e.getMessage().contains("title은 빈 값일 수 없습니다."));
    }

    @Test
    public void 시작날짜는_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLog tripLog = TripLog.builder()
                    .title("제목")
                    .tripStartDate(null)
                    .tripEndDate(LocalDate.of(2023, 9, 18))
                    .userId(1L)
                    .build();
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 종료날짜는_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLog tripLog = TripLog.builder()
                    .title("제목")
                    .tripStartDate(LocalDate.of(2023, 9, 15))
                    .tripEndDate(null)
                    .userId(1L)
                    .build();
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 시작날짜는_종료날짜보다_이르다() {
        TripLog tripLog = TripLog.builder()
                .title("제목")
                .tripStartDate(LocalDate.of(2023, 9, 15))
                .tripEndDate(LocalDate.of(2023, 9, 15))
                .userId(1L)
                .build();

        assertThat(tripLog.getTripStartDate())
                .isBeforeOrEqualTo(tripLog.getTripEndDate());
    }

    @Test
    public void 시작날짜는_종료날짜보다_늦으면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLog tripLog = TripLog.builder()
                    .title("제목")
                    .tripStartDate(LocalDate.of(2023, 9, 15))
                    .tripEndDate(LocalDate.of(2023, 8, 1))
                    .userId(1L)
                    .build();
        });

        assertTrue(e.getMessage().contains("startDate는 endDate보다 늦을 수 없습니다."));
    }

    @Test
    public void 유저아이디는_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLog tripLog = TripLog.builder()
                    .title("제목")
                    .tripStartDate(LocalDate.of(2023, 9, 15))
                    .tripEndDate(LocalDate.of(2023, 9, 18))
                    .userId(null)
                    .build();
        });

        assertTrue(e.getMessage().contains("userId은 null일 수 없습니다."));
    }

}