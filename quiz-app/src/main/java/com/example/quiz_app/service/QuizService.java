package com.example.quiz_app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quiz_app.exceptions.ResourceNotFoundException;
import com.example.quiz_app.feign.QuizInterface;
import com.example.quiz_app.model.QuestionDTO;
import com.example.quiz_app.model.Quiz;
import com.example.quiz_app.model.Response;
import com.example.quiz_app.repository.QuizRepo;

@Service
public class QuizService {

    private final QuizRepo quizRepo;
    private final QuizInterface quizInterface;

    // @Autowired
    public QuizService(QuizRepo quizRepo, QuizInterface quizInterface) {
        this.quizRepo = quizRepo;
        this.quizInterface = quizInterface;
    }

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        // Target URL is http://localhost:8080/question/generate

        List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questionIds);

        quizRepo.save(quiz);

        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDTO>> fetchQuizQuestions(Integer id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        // Get the question IDs from the quiz
        List<Integer> questionIDs = quiz.getQuestionsIds();

        // Now get the questions from QUESTION-SERVICE for these IDs
        return quizInterface.fetchQuestionsById(questionIDs);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        return quizInterface.getScore(responses);
    }

     

}
