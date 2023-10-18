package com.stella.rememberall.diary.question.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "question")
@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="question_category")
    private QuestionCategory questionCategory;

    @NotNull
    @Column(name = "topic")
    private String topic;

    @Builder
    public Question(QuestionCategory questionCategory, String topic) {
        QuestionValidation.checkQuestionCategoryValid(questionCategory);
        QuestionValidation.checkTopicValid(topic);
        this.questionCategory = questionCategory;
        this.topic = topic;
    }

    public void updateQuestionCategory(@NotNull QuestionCategory questionCategory) {
        QuestionValidation.checkQuestionCategoryValid(questionCategory);
        if (!this.questionCategory.equals(questionCategory)) {
            this.questionCategory = questionCategory;
        }
    }

    public void updateQuestionTopic(@NotBlank String topic) {
        QuestionValidation.checkTopicValid(topic);
        if (!this.topic.equals(topic)) {
            this.topic = topic;
        }
    }

}
