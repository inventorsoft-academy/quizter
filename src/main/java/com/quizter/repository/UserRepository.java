package com.quizter.repository;

import com.quizter.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    void deleteById(Long id);

    Optional<List<User>> findUserByProfileSubject(String name);

    Optional<User> findUserByEmail(String email);

}
