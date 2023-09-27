package com.stella.rememberall.tripLog.repository;

import com.stella.rememberall.tripLog.entity.TripLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TripLogRepositoryTest {

    private TripLog tripLog;

    @BeforeEach
    public void before() {
        tripLog = createTripLog();
    }

    private static TripLog createTripLog() {
        return TripLog.builder()
                .title("제목")
                .tripStartDate(LocalDate.of(2023, 9, 15))
                .tripEndDate(LocalDate.of(2023, 9, 18))
                .userId(1L)
                .build();
    }

    private static void assertTripLog(TripLog savedTripLog) {
        assertTripLogTitle(savedTripLog, "제목");
        assertTripLogStartDate(savedTripLog, LocalDate.of(2023, 9, 15));
        assertTripLogEndDate(savedTripLog, LocalDate.of(2023, 9, 18));
    }

    private static void assertTripLogUpdateTitle(TripLog savedTripLog, String expectedTitle) {
        assertTripLogTitle(savedTripLog, expectedTitle);
        assertTripLogStartDate(savedTripLog, LocalDate.of(2023, 9, 15));
        assertTripLogEndDate(savedTripLog, LocalDate.of(2023, 9, 18));
    }

    private static void assertTripLogUpdatedStartDate(TripLog savedTripLog, LocalDate expectedStartDate) {
        assertTripLogTitle(savedTripLog, "제목");
        assertTripLogStartDate(savedTripLog, expectedStartDate);
        assertTripLogEndDate(savedTripLog, LocalDate.of(2023, 9, 18));
    }

    private static void assertTripLogUpdatedEndDate(TripLog savedTripLog, LocalDate expectedEndDate) {
        assertTripLogTitle(savedTripLog, "제목");
        assertTripLogStartDate(savedTripLog, LocalDate.of(2023, 9, 15));
        assertTripLogEndDate(savedTripLog, expectedEndDate);
    }

    private static void assertTripLogTitle(TripLog savedTripLog, String expectedTitle) {
        assertEquals(expectedTitle, savedTripLog.getTitle());
    }

    private static void assertTripLogStartDate(TripLog savedTripLog, LocalDate expectedStartDate) {
        assertEquals(expectedStartDate, savedTripLog.getTripStartDate());
    }

    private static void assertTripLogEndDate(TripLog savedTripLog, LocalDate expectedEndDate) {
        assertEquals(expectedEndDate, savedTripLog.getTripEndDate());
    }

    @Autowired
    private TripLogRepository tripLogRepository;

    @Test
    public void 저장(){
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        assertTripLog(savedTripLog);

        LocalDateTime afterSave = LocalDateTime.now();
        assertTrue(savedTripLog.getCreatedDateTime().isBefore(afterSave));
    }

    @Test
    public void 일기장_아이디로_조회(){
        TripLog savedTripLog = tripLogRepository.save(tripLog);
        Long savedTripLogId = savedTripLog.getId();

        TripLog foundTripLog = tripLogRepository.findById(savedTripLogId).orElseThrow();

        assertTripLog(foundTripLog);
    }

    @Test
    public void 유저_아이디로_일기장들_조회(){
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        List<TripLog> tripLogs = tripLogRepository.findAllByUserId(1L);

        assertTripLog(tripLogs.get(0));
        assertEquals(tripLogs.size(), 1);
    }

    @Test
    public void 처음_저장하면_생성날짜와_수정날짜가_동일하다() {
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        assertTripLog(savedTripLog);
        assertTrue(savedTripLog.getCreatedDateTime().isEqual(savedTripLog.getModifiedDateTime()));
    }

    @Test
    public void 제목_수정() {
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        savedTripLog.updateTitle("새로운 제목");
        tripLogRepository.saveAndFlush(savedTripLog);

        TripLog updatedTripLog = tripLogRepository.findById(savedTripLog.getId()).orElseThrow();
        assertTripLogUpdateTitle(updatedTripLog, "새로운 제목");
    }

    @Test
    public void 수정후_수정날짜가_변경된다() {
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        savedTripLog.updateTitle("새로운 제목");
        tripLogRepository.saveAndFlush(savedTripLog);

        TripLog updatedTripLog = tripLogRepository.findById(savedTripLog.getId()).orElseThrow();
        assertThat(updatedTripLog.getModifiedDateTime().isAfter(updatedTripLog.getCreatedDateTime()));
    }

    @Test
    public void 시작날짜_수정() {
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        savedTripLog.updateStartDate(LocalDate.of(2023, 9, 16));
        tripLogRepository.saveAndFlush(savedTripLog);

        TripLog updatedTripLog = tripLogRepository.findById(savedTripLog.getId()).orElseThrow();
        assertTripLogUpdatedStartDate(updatedTripLog, LocalDate.of(2023, 9, 16));
    }

    @Test
    public void 종료날짜_수정() {
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        savedTripLog.updateEndDate(LocalDate.of(2023, 9, 20));
        tripLogRepository.saveAndFlush(savedTripLog);

        TripLog updatedTripLog = tripLogRepository.findById(savedTripLog.getId()).orElseThrow();
        assertTripLogUpdatedEndDate(updatedTripLog, LocalDate.of(2023, 9, 20));
    }

    @Test
    public void 삭제() {
        TripLog savedTripLog = tripLogRepository.save(tripLog);

        tripLogRepository.deleteById(savedTripLog.getId());

        Optional<TripLog> deletedTripLog = tripLogRepository.findById(savedTripLog.getId());
        assertTrue(deletedTripLog.isEmpty());
    }

    @Test
    public void 유저_아이디로_전부_삭제() {
        TripLog tripLog1 = createTripLog();
        TripLog tripLog2 = createTripLog();

        tripLogRepository.deleteAllByUserId(tripLog1.getUserId());

        List<TripLog> foundTripLogList = tripLogRepository.findAllByUserId(tripLog1.getUserId());
        assertEquals(foundTripLogList.size(), 0);
    }

    @Test
    public void 전부_삭제() {
        TripLog tripLog1 = createTripLog();
        TripLog tripLog2 = createTripLog();

        tripLogRepository.deleteAll();

        List<TripLog> foundTripLogList = tripLogRepository.findAll();
        assertEquals(foundTripLogList.size(), 0);
    }
}