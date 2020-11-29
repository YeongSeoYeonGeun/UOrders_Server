package com.example.uorders.Service;

import com.example.uorders.api.UserApiController;
import com.example.uorders.domain.*;
import com.example.uorders.exception.UserNotFoundException;
import com.example.uorders.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Long> findFavoriteCafeListEager(User user) {

        Set<Favorite> favorites = user.getFavorites();
        List<Long> favoriteCafeList = new ArrayList<>();

        for (Favorite favorite: favorites) {
            favoriteCafeList.add(favorite.getCafe().getId());
        }

        return favoriteCafeList;
    }

    public Cart findCart(Long userId) {
        User user = findById(userId);

        return user.getCart();
    }

    public Set<Order> findOrders(Long userId) {
        User user = findById(userId);
        Set<Order> orders = user.getOrders();
        return orders;
    }
}
