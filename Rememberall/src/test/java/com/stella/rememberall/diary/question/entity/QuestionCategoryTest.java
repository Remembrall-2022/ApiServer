package com.stella.rememberall.diary.question.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionCategoryTest {

    @Test
    public void 카테고리_생성() {
        QuestionCategory 질문_카테고리1 = QuestionCategory.builder()
                .questionCategoryName("질문 카테고리1")
                .build();

        assertEquals(질문_카테고리1.getQuestionCategoryName(), "질문 카테고리1");
    }

    @Test
    public void 카테고리_생성시_이름은_null일_수_없다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            QuestionCategory 질문_카테고리_null = QuestionCategory.builder()
                    .questionCategoryName(null)
                    .build();
        });

        assertTrue(e.getMessage().contains("questionCategoryName은 빈값일 수 없습니다"));
    }

    @Test
    public void 카테고리_생성시_이름은_빈_값일_수_없다() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            QuestionCategory 질문_카테고리_빈값 = QuestionCategory.builder()
                    .questionCategoryName("")
                    .build();
        });

        assertTrue(e.getMessage().contains("questionCategoryName은 빈값일 수 없습니다"));
    }

    @Test
    public void 카테고리_수정() {
        QuestionCategory 질문_카테고리1 = QuestionCategory.builder()
                .questionCategoryName("질문 카테고리1")
                .build();
        질문_카테고리1.updateQuestionCategoryName("수정_카테고리");

        assertEquals(질문_카테고리1.getQuestionCategoryName(), "수정_카테고리");
    }

    @Test
    public void 카테고리_수정시_이름은_빈_값일_수_없다() {
        QuestionCategory 질문_카테고리1 = QuestionCategory.builder()
                .questionCategoryName("질문 카테고리1")
                .build();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            질문_카테고리1.updateQuestionCategoryName("");
        });

        assertTrue(e.getMessage().contains("questionCategoryName은 빈값일 수 없습니다"));
    }

}