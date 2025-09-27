package com.example.quiz_app.model;

import lombok.Data;

@Data
public class QuizDTO {
    private String category;
    private Integer numQs;
    private String title;
}
