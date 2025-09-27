package com.example.quiz_app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz_app.model.QuestionDTO;
import com.example.quiz_app.model.QuizDTO;
import com.example.quiz_app.model.Response;
import com.example.quiz_app.service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDto) {
        return service.createQuiz(quizDto.getCategory(), quizDto.getNumQs(), quizDto.getTitle());
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<List<QuestionDTO>> fetchQuiz(@PathVariable Integer id) {
        return service.fetchQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses ) {
        return service.calculateResult(id, responses);
    }
}
