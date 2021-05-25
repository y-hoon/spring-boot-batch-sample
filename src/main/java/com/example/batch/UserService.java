package com.example.batch;

import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }
}
