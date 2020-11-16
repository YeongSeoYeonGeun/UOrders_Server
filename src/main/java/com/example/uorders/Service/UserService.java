package com.example.uorders.Service;

import com.example.uorders.domain.User;
import com.example.uorders.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 조회
     */
    public Optional<User> findOne(Long userId) { return userRepository.findById(userId); }

    /**
     *  회원 전체 조회
     */
    public List<User> findUsers() { return userRepository.findAll(); }

}
