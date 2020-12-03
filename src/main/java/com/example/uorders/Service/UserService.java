package com.example.uorders.Service;

import com.example.uorders.domain.*;
import com.example.uorders.dto.user.CreateUserResponse;
import com.example.uorders.dto.user.LoginRequest;
import com.example.uorders.dto.user.UpdateUserLanguageCodeRequest;
import com.example.uorders.exception.UserNotFoundException;
import com.example.uorders.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public void saveUser(User user) { userRepository.save(user); }

    /**
     * 회원 조회
     */
    public User findById(Long userId) { return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId)); }

    /**
     *  회원 전체 조회
     */
    public List<User> findUsers() { return userRepository.findAll(); }

    public List<Cafe> findFavoriteCafeList(User user) {

        Set<Favorite> favorites = user.getFavoriteSet();
        List<Cafe> favoriteCafeList = new ArrayList<>();

        for (Favorite favorite: favorites) {
            favoriteCafeList.add(favorite.getCafe());
        }

        return favoriteCafeList;
    }

    public Cart findCart(Long userId) {
        User user = findById(userId);

        return user.getCart();
    }

    public Set<Order> findOrderSet(Long userId) {
        User user = findById(userId);
        Set<Order> orderSet = user.getOrderSet();
        return orderSet;
    }

    @Transactional
    public CreateUserResponse login(LoginRequest request) {
        User user =  userRepository.findById(request.getUserIndex()).orElse(null);

        if(user == null) { // 신규 유저 등록
            User new_user = User.builder()
                    .name(request.getUserName())
                    .cart(null)
                    .code(request.getJs_code())
                    .favoriteSet(new HashSet<>())
                    .orderSet(new HashSet<>())
                    .languageCode("ko")
                    .build();

            Cart cart = Cart.builder()
                    .user(new_user)
                    .cafe(null)
                    .cartMenuSet(new HashSet<>())
                    .build();

            saveUser(new_user);
            return new CreateUserResponse(new_user.getId());
        }
        else { // 유저 JS_CODE 재등록
            user.setCode(request.getJs_code());
            saveUser(user);

            return new CreateUserResponse(user.getId());
        }
    }

    @Transactional
    public void updateLanguageCode(User user, UpdateUserLanguageCodeRequest request) {
        user.setLanguageCode(request.getLanguageCode());
        saveUser(user);
    }
}
