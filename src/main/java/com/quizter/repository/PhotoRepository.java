package com.quizter.repository;

import com.quizter.entity.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {

    Optional<Photo> findByFileName(String fileName);
}
