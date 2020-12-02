package com.example.uorders.Service;

import com.example.uorders.domain.*;
import com.example.uorders.dto.user.LoginRequest;
import com.example.uorders.exception.UserNotFoundException;
import com.example.uorders.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 조회
     */
    public User findById(Long userId) { return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId)); }

    /**
     *  회원 전체 조회
     */
    public List<User> findUsers() { return userRepository.findAll(); }

    public List<Cafe> findFavoriteCafeList(User user) {

        Set<Favorite> favorites = user.getFavorites();
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

    /*
    public Long login(LoginRequest request) {

        Long userId = request.getUserIndex();
        String code = request.getJs_code();

        User user = new User();
        Cart cart = new Cart();
        user.setName(name);
        cart.setUser(user);
        set
    }

     */
}
