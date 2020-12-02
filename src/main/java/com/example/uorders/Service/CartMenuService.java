package com.example.uorders.Service;

import com.example.uorders.domain.CartMenu;
import com.example.uorders.exception.CartMenuNotFoundException;
import com.example.uorders.repository.CartMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartMenuService {

    private final CartMenuRepository cartMenuRepository;

    public CartMenu findById(Long cartMenuId) {
        return cartMenuRepository.findById(cartMenuId).orElseThrow(()-> new CartMenuNotFoundException(cartMenuId));
    }

    @Transactional
    public void deleteOne(CartMenu cartMenu) { cartMenuRepository.delete(cartMenu); }
}
