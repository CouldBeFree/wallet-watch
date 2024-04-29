package com.expenses.walletwatch.repository;

import com.expenses.walletwatch.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Person, Long> {
    @Query("select c from Person c where c.email = ?1 or c.username = ?2")
    Person findUserByEmailOrUsername(String email, String String);
}
