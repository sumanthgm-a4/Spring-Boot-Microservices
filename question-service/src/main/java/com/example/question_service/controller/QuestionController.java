package com.example.question_service.controller;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.question_service.model.Question;
import com.example.question_service.model.QuestionDTO;
import com.example.question_service.model.Response;
import com.example.question_service.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService service;
    private final Environment environment;

    // @Autowired
    public QuestionController(QuestionService service, Environment env) {
        this.service = service;
        this.environment = env;
    }

    @GetMapping("/")
    public String welcomeHome() {
        return "Welcome to Quiz-App (Question Service)";
    }

    @GetMapping("/questions")
    public List<Question> getAllQues() {
        return service.getAllQuestions();
    }

    @GetMapping("/questions/{id}")
    public Question getQuesById(@PathVariable Integer id) {
        return service.getQuestionById(id);
    }

    @GetMapping("/questions/category/{category}")
    public List<Question> getQuesByCategory(@PathVariable String category) {
        return service.getQuestionsByCategory(category);
    }

    @PostMapping("questions")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return service.addQuestion(question);
    }

    // Get questions for a quiz
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQs) {
        return service.getQuestionsForQuiz(category, numQs);
    }

    // Get questions by question IDs
    @PostMapping("/fetch")
    public ResponseEntity<List<QuestionDTO>> fetchQuestionsById(@RequestBody List<Integer> ids) {
        System.out.println("Used instance's PORT: " + environment.getProperty("local.server.port"));
        return service.fetchQuestionsById(ids);
    }

    // Calculate score
    @PostMapping("/score")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return service.getScore(responses);
    }
}
