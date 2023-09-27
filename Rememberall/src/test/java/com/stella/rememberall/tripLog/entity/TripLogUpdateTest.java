package com.stella.rememberall.tripLog.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripLogUpdateTest {
    private TripLog tripLog;

    @BeforeEach
    public void before() {
        tripLog = TripLog.builder()
                .title("제목")
                .tripStartDate(LocalDate.of(2023, 9, 15))
                .tripEndDate(LocalDate.of(2023, 9, 18))
                .userId(1L)
                .build();
    }

    @Test
    public void 제목_수정() {
        tripLog.updateTitle("새로운 제목");

        assertThat(tripLog.getTitle()).isEqualTo("새로운 제목");
    }

    @Test
    public void 제목_수정시_빈값이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            tripLog.updateTitle("");
        });

        assertTrue(e.getMessage().contains("title은 빈 값일 수 없습니다."));
    }

    @Test
    public void 시작날짜_수정() {
        tripLog.updateStartDate(LocalDate.of(2023, 8, 1));
        assertThat(tripLog.getTripStartDate()).isEqualTo(LocalDate.of(2023, 8, 1));
    }

    @Test
    public void 시작날짜_수정시_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            tripLog.updateStartDate(null);
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 시작날짜_수정시_시작날짜는_종료날짜보다_늦으면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            tripLog.updateStartDate(LocalDate.of(2023, 10, 1));
        });

        assertTrue(e.getMessage().contains("startDate는 endDate보다 늦을 수 없습니다."));
    }

    @Test
    public void 종료날짜_수정() {
        tripLog.updateEndDate(LocalDate.of(2023, 10, 1));
        assertThat(tripLog.getTripEndDate()).isEqualTo(LocalDate.of(2023, 10, 1));
    }

    @Test
    public void 종료날짜_수정시_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            tripLog.updateEndDate(null);
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 종료날짜_수정시_시작날짜는_종료날짜보다_늦으면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            tripLog.updateEndDate(LocalDate.of(2023, 8, 1));
        });

        assertTrue(e.getMessage().contains("endDate는 startDate보다 빠를 수 없습니다."));
    }
}
