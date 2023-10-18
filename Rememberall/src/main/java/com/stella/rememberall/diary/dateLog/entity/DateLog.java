package com.stella.rememberall.diary.dateLog.entity;

import com.stella.rememberall.diary.common.BaseTimeEntity;
import com.stella.rememberall.diary.question.entity.Question;
import com.stella.rememberall.diary.tripLog.entity.TripLog;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Table(name = "date_log")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateLog extends BaseTimeEntity {
    @Id
    @Column(name = "date_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    @Embedded
    @Column(name = "weather_info")
    private WeatherInfo weatherInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @NotNull
    @Column(length = 1000)
    private String answer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_log_id")
    private TripLog tripLog;

//    @OneToMany(mappedBy = "dateLog", cascade = CascadeType.ALL)
//    private List<PlaceLog> placeLogList = new ArrayList<>();

    @Builder
    public DateLog(TripLog tripLog, LocalDate date, WeatherInfo weatherInfo, Question question, String answer) {
        this.tripLog = tripLog;
        this.date = date;
        this.weatherInfo = weatherInfo;
        this.question = question;
        this.answer = answer;
    }

    // @todo : 다르면 업데이트
    // @todo : not null 걸리는지 체크

    public void updateDate(LocalDate date){
        this.date = date;
    }

    public void updateQuestion(Question question){
        this.question = question;
    }

    public void updateAnswer(String answer){
        this.answer = answer;
    }

    public void updateWeatherInfo(WeatherInfo weatherInfo){
        this.weatherInfo = weatherInfo;
    }

}
