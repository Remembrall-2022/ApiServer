package com.stella.rememberall.diary.question.entity;

import com.stella.rememberall.diary.common.Validator;

public class QuestionValidation {
    public static void checkQuestionCategoryValid(QuestionCategory questionCategory) {
        if (Validator.isNull(questionCategory))
            throw new IllegalArgumentException("questionCategory는 null일 수 없습니다");
    }

    public static void checkTopicValid(String topic) {
        if (Validator.isBlank(topic))
            throw new IllegalArgumentException("topic은 빈값일 수 없습니다");
    }
}
