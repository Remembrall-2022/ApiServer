package com.stella.rememberall.tripLog.service;

import com.stella.rememberall.diary.tripLog.data.TripLogResponseDto;
import com.stella.rememberall.diary.tripLog.data.TripLogServiceRequestDto;
import com.stella.rememberall.diary.tripLog.entity.TripLog;
import com.stella.rememberall.diary.tripLog.repository.TripLogRepository;
import com.stella.rememberall.diary.tripLog.service.TripLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripLogServiceTest {

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


    @Test
    public void 일기_조회_성공() {

        TripLog tripLog = new TripLogProxy(1L, getValidTripLogBuilder().build());
        when(tripLogRepository.findById(VALID_TRIPLOG_ID)).thenReturn(Optional.of(tripLog));

        TripLogResponseDto foundTripLogDto = tripLogService.readTripLog(VALID_TRIPLOG_ID);

        assertEquals(foundTripLogDto.getId(), VALID_TRIPLOG_ID);
    }

    @Test
    public void 존재하지_않는_일기_조회() {
        when(tripLogRepository.findById(INVALID_TRIPLOG_ID)).thenThrow(new NoSuchElementException("해당 tripLog를 찾을 수 없습니다."));

        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> {
            tripLogService.readTripLog(INVALID_TRIPLOG_ID);
        });

        assertTrue(e.getMessage().contains("해당 tripLog를 찾을 수 없습니다."));
    }

    @Test
    public void 유저의_일기장들_조회() {

        List<TripLog> userTripLogList = new ArrayList<>();
        userTripLogList.add(new TripLogProxy(1L, getValidTripLogBuilder().title("제목1").build()));
        userTripLogList.add(new TripLogProxy(2L, getValidTripLogBuilder().title("제목2").build()));
        when(tripLogRepository.findAllByUserId(VALID_USER_ID)).thenReturn(userTripLogList);

        List<TripLogResponseDto> foundTripLogResponseDtos = tripLogService.readTripLogByUser(VALID_USER_ID, userId -> {});

        List<Long> foundTripLogResponseDtosIdList = foundTripLogResponseDtos.stream().map(TripLogResponseDto::getId).collect(Collectors.toList());
        assertEquals(foundTripLogResponseDtosIdList, new ArrayList<>(Arrays.asList(1L, 2L)));
    }

    @Test
    public void 존재하지_않는_유저의_일기장_조회() {

        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> {
            tripLogService.readTripLogByUser(INVALID_USER_ID, userId -> {throw new NoSuchElementException("존재하지 않는 user입니다.");});
        });

        assertTrue(e.getMessage().contains("존재하지 않는 user입니다."));
    }

    @Test
    public void 일기장_수정() {
        TripLog tripLog = new TripLogProxy(1L, getValidTripLogBuilder().build());
        when(tripLogRepository.findById(VALID_TRIPLOG_ID)).thenReturn(Optional.of(tripLog));

        TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                "수정한 제목",
                tripLog.getTripStartDate(),
                tripLog.getTripEndDate(),
                tripLog.getUserId());

        TripLogResponseDto foundTripLogResponseDto = tripLogService.updateTripLog(VALID_TRIPLOG_ID, requestDto);

        assertThat(foundTripLogResponseDto.getTitle()).isEqualTo(requestDto.getTitle());
    }

    @Test
    public void 일기장_수정시_제목은_빈값일수_없다() {
        TripLog tripLog = new TripLogProxy(1L, getValidTripLogBuilder().build());
        when(tripLogRepository.findById(VALID_TRIPLOG_ID)).thenReturn(Optional.of(tripLog));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    "",
                    VALID_START_DATE,
                    VALID_END_DATE,
                    VALID_USER_ID);

            tripLogService.updateTripLog(tripLog.getId(), requestDto);
        });

        assertTrue(e.getMessage().contains("title은 빈 값일 수 없습니다."));
    }

    @Test
    public void 일기장_수정시_시작날짜는_null이면_안된다() {
        TripLog tripLog = new TripLogProxy(1L, getValidTripLogBuilder().build());
        when(tripLogRepository.findById(VALID_TRIPLOG_ID)).thenReturn(Optional.of(tripLog));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    VALID_TITLE,
                    null,
                    VALID_END_DATE,
                    VALID_USER_ID);

            tripLogService.updateTripLog(VALID_TRIPLOG_ID, requestDto);
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 일기장_수정시_종료날짜는_null이면_안된다() {
        TripLog tripLog = new TripLogProxy(1L, getValidTripLogBuilder().build());
        when(tripLogRepository.findById(VALID_TRIPLOG_ID)).thenReturn(Optional.of(tripLog));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    VALID_TITLE,
                    VALID_START_DATE,
                    null,
                    VALID_USER_ID);

            tripLogService.updateTripLog(VALID_TRIPLOG_ID, requestDto);
        });

        assertTrue(e.getMessage().contains("startDate 혹은 endDate는 null일 수 없습니다."));
    }

    @Test
    public void 일기장_수정시_시작날짜는_종료날짜보다_늦으면_안된다() {
        TripLog tripLog = new TripLogProxy(1L, getValidTripLogBuilder().build());
        when(tripLogRepository.findById(VALID_TRIPLOG_ID)).thenReturn(Optional.of(tripLog));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            TripLogServiceRequestDto requestDto = new TripLogServiceRequestDto(
                    VALID_TITLE,
                    VALID_END_DATE,
                    VALID_START_DATE,
                    VALID_USER_ID);

           tripLogService.updateTripLog(VALID_TRIPLOG_ID, requestDto);
        });

        System.out.println(e.getMessage());

        assertTrue(e.getMessage().contains("endDate는 startDate보다 빠를 수 없습니다."));
    }

    @Test
    public void 일기장_삭제() {
        when(tripLogRepository.findById(VALID_TRIPLOG_ID)).thenReturn(Optional.of(new TripLogProxy(VALID_TRIPLOG_ID, getValidTripLogBuilder().build())));
        doNothing().when(tripLogRepository).deleteById(VALID_TRIPLOG_ID);
        tripLogService.deleteTripLog(VALID_TRIPLOG_ID);
    }

    @Test
    public void 존재하지_않는_일기장_삭제() {
        when(tripLogRepository.findById(INVALID_TRIPLOG_ID)).thenThrow(new NoSuchElementException("해당 tripLog를 찾을 수 없습니다."));

        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> {
            tripLogService.deleteTripLog(INVALID_TRIPLOG_ID);
        });

        assertTrue(e.getMessage().contains("해당 tripLog를 찾을 수 없습니다."));
    }

}