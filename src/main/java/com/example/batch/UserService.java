package com.example.batch;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Builder
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers() {

        List<User> userList = userRepository.findAll();

        return userList;
    }

    public List<User> findUsersByCreateDate(LocalDateTime checkDate) {
        List<User> userList = userRepository.findByCreatedDate(checkDate);

        return userList;
    }

    public void saveAll(List<User> users) {
        log.info("====== writer ing call save all");
        userRepository.saveAll(users);
    }
}
