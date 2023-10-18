package com.stella.rememberall.diary.question.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "question_category")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class QuestionCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_category_id")
    private Long id;

    @NotBlank
    @Column(name = "question_category_name", unique = true)
    private String questionCategoryName;

    @OneToMany(mappedBy = "questionCategory")
    private List<Question> questionList = new ArrayList<>();

    @Builder
    public QuestionCategory(@NotBlank String questionCategoryName) {
        QuestionCategoryValidation.checkQuestionCategoryNameValid(questionCategoryName);
        this.questionCategoryName = questionCategoryName;
    }

    public void updateQuestionCategoryName(@NotBlank String questionCategoryName) {
        QuestionCategoryValidation.checkQuestionCategoryNameValid(questionCategoryName);
        if (!this.questionCategoryName.equals(questionCategoryName)) {
            this.questionCategoryName = questionCategoryName;
        }
    }

}
