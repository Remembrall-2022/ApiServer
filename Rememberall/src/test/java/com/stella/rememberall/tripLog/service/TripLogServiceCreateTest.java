package com.stella.rememberall.tripLog.service;

import com.stella.rememberall.tripLog.data.TripLogServiceRequestDto;
import com.stella.rememberall.tripLog.entity.TripLog;
import com.stella.rememberall.tripLog.repository.TripLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripLogServiceCreateTest {

    @Mock
    private TripLogRepository tripLogRepository;

    @InjectMocks
    private TripLogService tripLogService;

    private final Long VALID_TRIPLOG_ID = 1L;
    private final Long INVALID_TRIPLOG_ID = -1L;

    private final Long VALID_USER_ID = 1L;
    private final Long INVALID_USER_ID = -1L;

    private final String VALID_TITLE = "제목";
    private final LocalDate VALID_START_DATE = LocalDate.of(2023, 9, 15);
    private final LocalDate VALID_END_DATE = LocalDate.of(2023, 9, 18);

    private TripLog.TripLogBuilder getValidTripLogBuilder() {
        return TripLog.builder()
                .title(VALID_TITLE)
                .tripStartDate(VALID_START_DATE)
                .tripEndDate(VALID_END_DATE)
                .userId(VALID_USER_ID);
    }

    @Test
    public void 일기_저장() {
        TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                VALID_TITLE,
                VALID_START_DATE,
                VALID_END_DATE,
                VALID_USER_ID);
        TripLog tripLog = new TripLogServiceTest.TripLogProxy(1L, requestDto.toEntity());
        when(tripLogRepository.save(any(TripLog.class))).thenReturn(tripLog);

        Long savedTripLogId = tripLogService.createTripLog(requestDto);

        assertEquals(savedTripLogId, 1L);
    }

    @Test
    public void 제목은_빈값일수_없다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    "",
                    VALID_START_DATE,
                    VALID_END_DATE,
                    VALID_USER_ID);

            Long savedTripLogId = tripLogService.createTripLog(requestDto);
        });

        assertTrue(e.getMessage().contains("title은 빈 값일 수 없습니다."));
    }

    @Test
    public void 시작날짜는_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    VALID_TITLE,
                    null,
                    VALID_END_DATE,
                    VALID_USER_ID);

            Long savedTripLogId = tripLogService.createTripLog(requestDto);
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 종료날짜는_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    VALID_TITLE,
                    VALID_START_DATE,
                    null,
                    VALID_USER_ID);

            Long savedTripLogId = tripLogService.createTripLog(requestDto);
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 시작날짜는_종료날짜보다_늦으면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    VALID_TITLE,
                    VALID_END_DATE,
                    VALID_START_DATE,
                    VALID_USER_ID);

            Long savedTripLogId = tripLogService.createTripLog(requestDto);
        });

        assertTrue(e.getMessage().contains("startDate는 endDate보다 늦을 수 없습니다."));
    }

    @Test
    public void 유저아이디는_null이면_안된다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    VALID_TITLE,
                    VALID_START_DATE,
                    VALID_END_DATE,
                    null);

            Long savedTripLogId = tripLogService.createTripLog(requestDto);
        });

        assertTrue(e.getMessage().contains("userId은 null일 수 없습니다."));
    }
}
