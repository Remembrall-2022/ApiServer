package com.stella.rememberall.diary.question.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    QuestionCategory questionCategory;

    @BeforeEach
    public void before() {
        this.questionCategory = QuestionCategory.builder()
                .questionCategoryName("기타")
                .build();
    }

    @Test
    public void 질문_생성() {
        Question question = Question.builder()
                .questionCategory(questionCategory)
                .topic("나만의 여행 버킷리스트가 있다면 소개해주세요.")
                .build();

        assertEquals(question.getTopic(), "나만의 여행 버킷리스트가 있다면 소개해주세요.");
    }

    @Test
    public void 질문_생성시_빈값일수없다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            Question question = Question.builder()
                    .questionCategory(questionCategory)
                    .topic("")
                    .build();
        });

        assertTrue(e.getMessage().contains("topic은 빈값일 수 없습니다"));
    }

    @Test
    public void 질문_생성시_카테고리_null일수없다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            Question question = Question.builder()
                    .questionCategory(null)
                    .topic("나만의 여행 버킷리스트가 있다면 소개해주세요.")
                    .build();
        });

        assertTrue(e.getMessage().contains("questionCategory는 null일 수 없습니다"));
    }

    // @todo : Question 배치 로직에서 수정 필요


}