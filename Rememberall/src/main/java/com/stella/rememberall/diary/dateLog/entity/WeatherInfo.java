package com.stella.rememberall.diary.dateLog.entity;

import lombok.*;

import jakarta.persistence.Embeddable;

@AllArgsConstructor @Getter
@EqualsAndHashCode
@Embeddable
public final class WeatherInfo {
    private final String weather;
    private final Integer degree;
}