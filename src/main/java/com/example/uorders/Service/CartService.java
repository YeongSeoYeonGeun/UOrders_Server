package com.example.uorders.Service;

import com.example.uorders.domain.*;
import com.example.uorders.dto.cart.CartDto;
import com.example.uorders.exception.CartNotFoundException;
import com.example.uorders.repository.CartMenuRepository;
import com.example.uorders.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;


    @Transactional
    public void saveCart(Cart cart) {cartRepository.save(cart); }

    public Cart findById(Long cartId) { return cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException(cartId)); }

    @Transactional
    public void initializeCart(Cart cart) {

        Set<CartMenu> cartMenuSet = cart.getCartMenuSet();

        for(CartMenu cartMenu: cartMenuSet){
            cartMenuRepository.delete(cartMenu);
        }

        cart.setCartMenuSet(new HashSet<>());
        cart.setCafe(null);
    }

    public Set<CartMenu> findCartMenus(Cart cart, Menu menu) {
        Set<CartMenu> findCartMenus = new HashSet<>();
        Set<CartMenu> cartMenus = cart.getCartMenuSet();

        for(CartMenu cartMenu : cartMenus) {
            if(cartMenu.getMenu().getId().equals(menu.getId())){
                findCartMenus.add(cartMenu);
            }
        }
        return findCartMenus;
    }

    public CartDto readCart(User user, Cart cart) {
        Set<CartMenu> findCartMenus = cart.getCartMenuSet();

        // 장바구니 비어있음
        String cafeName = "";
        Long cafeIndex = 0L;
        if(findCartMenus.size() == 0) { cafeName = ""; cafeIndex = 0L;}
        else {
            Cafe cafe = cart.getCafe();
            cafeIndex = cafe.getId();
            cafeName = cafe.getName(user.getLanguageCode());
        }

        return CartDto.of(cart, cafeIndex, cafeName, user.getLanguageCode());
    }
}
