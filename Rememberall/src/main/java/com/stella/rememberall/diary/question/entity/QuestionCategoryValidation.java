package com.stella.rememberall.diary.question.entity;

import com.stella.rememberall.diary.common.Validator;

public class QuestionCategoryValidation {
    public static void checkQuestionCategoryNameValid(String questionCategoryName) {
        if (Validator.isBlank(questionCategoryName))
            throw new IllegalArgumentException("questionCategoryName은 빈값일 수 없습니다");
    }

}
