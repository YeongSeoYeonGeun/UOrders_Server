package com.example.uorders.Service;

import com.example.uorders.domain.CartMenu;
import com.example.uorders.domain.CartMenuId;
import com.example.uorders.repository.CartMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartMenuService {

    private final CartMenuRepository cartMenuRepository;

    public Optional<CartMenu> findOne(Long cartId, Long menuId) {
        CartMenuId cartMenuId = new CartMenuId(cartId, menuId);
        return cartMenuRepository.findById(cartMenuId);
    }

    @Transactional
    public void deleteOne(CartMenu cartMenu) { cartMenuRepository.delete(cartMenu); }
}
