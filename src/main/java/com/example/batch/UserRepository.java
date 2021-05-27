package com.example.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u " +
            "from User u " +
            "where u.createDate < :checkDate")
    List<User> findByCreatedDate(@Param("checkDate")LocalDateTime checkDate);

}
