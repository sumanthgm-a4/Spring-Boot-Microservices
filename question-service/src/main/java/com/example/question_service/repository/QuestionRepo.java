package com.example.question_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.question_service.model.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer>{

    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE q.category=:cat ORDER BY RAND() LIMIT :num", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(@Param("cat") String category, @Param("num") Integer numQ);
}
