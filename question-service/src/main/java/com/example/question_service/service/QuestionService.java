package com.example.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.question_service.exceptions.ResourceNotFoundException;
import com.example.question_service.model.Question;
import com.example.question_service.model.QuestionDTO;
import com.example.question_service.model.Response;
import com.example.question_service.repository.QuestionRepo;

@Service
public class QuestionService {
    
    private final QuestionRepo repo;

    // @Autowired
    public QuestionService(QuestionRepo repo) {
        this.repo = repo;
    }

    public List<Question> getAllQuestions() {
        return repo.findAll();
    }

    public Question getQuestionById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
    } 

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = repo.findByCategory(category);

        if (questions.isEmpty())
            throw new ResourceNotFoundException("Questions in the given category don't exist");

        return questions;
    }

    public ResponseEntity<Question> addQuestion(Question question) {
        repo.save(question);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQs) {
        List<Integer> questions = repo.findRandomQuestionsByCategory(category, numQs);

        return new ResponseEntity<>(questions, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDTO>> fetchQuestionsById(List<Integer> ids) {
        List<QuestionDTO> questionsDTOs = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        // Fetching the questions first, then sending the DTOs
        ids.forEach(id -> questions.add(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found"))));

        // Now the QuestionDTOs
        for (Question question: questions) {
            QuestionDTO dto = new QuestionDTO(
                question.getId(),
                question.getQuestionTitle(),
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4()
            );

            questionsDTOs.add(dto);

        }

        return new ResponseEntity<>(questionsDTOs, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        // Checking the results
        Integer result = 0;
        for (Response response : responses) {
            Question question = repo.findById(response.getId()).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
            if (response.getResponse().equalsIgnoreCase(question.getRightAnswer()))
                result ++;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
