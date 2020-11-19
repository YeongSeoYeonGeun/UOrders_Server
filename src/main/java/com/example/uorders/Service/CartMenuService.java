package com.example.uorders.Service;

import com.example.uorders.domain.CartMenu;
import com.example.uorders.repository.CartMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartMenuService {
    CartMenuRepository cartMenuRepository;

    public Optional<CartMenu> findOne(Long id) { return cartMenuRepository.findById(id); }

    @Transactional
    public void deleteOne(Long menuId) { cartMenuRepository.deleteById(menuId); }
}
