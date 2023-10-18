package com.stella.rememberall.diary.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    public String getCreatedDateTime(DateTimeFormatter dateTimeFormatter) {
        return createdDateTime.format(dateTimeFormatter);
    }

    public String getModifiedDateTime(DateTimeFormatter dateTimeFormatter) {
        return modifiedDateTime.format(dateTimeFormatter);
    }

}