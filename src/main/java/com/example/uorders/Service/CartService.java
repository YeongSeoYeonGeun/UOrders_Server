package com.example.uorders.Service;

import com.example.uorders.domain.Cart;
import com.example.uorders.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public void saveCart(Cart cart) {cartRepository.save(cart); }

    public Optional<Cart> findOne(Long cartId) { return cartRepository.findById(cartId); }
}
