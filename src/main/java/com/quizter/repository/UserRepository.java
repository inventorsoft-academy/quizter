package com.quizter.repository;

import com.quizter.dictionary.Role;
import com.quizter.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    void deleteById(Long id);

    List<User> findUserByRole(Role role);

}
