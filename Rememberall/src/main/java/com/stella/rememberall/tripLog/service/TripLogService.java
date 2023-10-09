package com.stella.rememberall.tripLog.service;

import com.stella.rememberall.tripLog.data.TripLogResponseDto;
import com.stella.rememberall.tripLog.data.TripLogServiceRequestDto;
import com.stella.rememberall.tripLog.entity.TripLog;
import com.stella.rememberall.tripLog.repository.TripLogRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TripLogService {

    private final TripLogRepository tripLogRepository;

    @Transactional
    public Long createTripLog(@NotNull final TripLogServiceRequestDto tripLogRequestDto) {
        TripLog tripLog = tripLogRequestDto.toEntity();
        return tripLogRepository.save(tripLog).getId();
    }

    public TripLogResponseDto readTripLog(@NotNull final Long tripLogId) {
        TripLog tripLog = getTripLogFromRepository(tripLogId);
        return TripLogResponseDto.of(tripLog);
    }

    private TripLog getTripLogFromRepository(@NotNull final Long tripLogId) {
        return tripLogRepository.findById(tripLogId)
                .orElseThrow(() -> new NoSuchElementException("해당 tripLog를 찾을 수 없습니다."));
    }

    public List<TripLogResponseDto> readTripLogByUser(@NotNull final Long userId, UserService userService) {
        // @Todo : userId를 db에서 조회해서 존재하는 userId인지 검증하는게 맞지 않을까
        userService.checkExists(userId);
        List<TripLog> foundTripLogList = tripLogRepository.findAllByUserId(userId);
        return foundTripLogList.stream().map(TripLogResponseDto::of).toList();
    }

    @Transactional
    public TripLogResponseDto updateTripLog(@NotNull final Long tripLogId, @NotNull final TripLogServiceRequestDto tripLogRequestDto) {
        TripLog tripLog = getTripLogFromRepository(tripLogId);
        tripLog.updateTitle(tripLogRequestDto.getTitle());
        tripLog.updateStartDate(tripLogRequestDto.getStartDate());
        tripLog.updateEndDate(tripLogRequestDto.getEndDate());
        return TripLogResponseDto.of(tripLog);
    }

    @Transactional
    public void deleteTripLog(@NotNull final Long tripLogId) {
        TripLog tripLog = getTripLogFromRepository(tripLogId);
        tripLogRepository.deleteById(tripLog.getId());
    }

}
