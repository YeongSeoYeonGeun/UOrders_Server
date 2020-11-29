package com.example.uorders.Service;

import com.example.uorders.domain.Cart;
import com.example.uorders.domain.CartMenu;
import com.example.uorders.domain.Menu;
import com.example.uorders.exception.CartNotFoundException;
import com.example.uorders.repository.CartMenuRepository;
import com.example.uorders.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
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

        Set<CartMenu> cartMenus = cart.getCartMenus();

        for(CartMenu cartMenu: cartMenus){
            cartMenuRepository.delete(cartMenu);
        }
    }

    public Set<CartMenu> findCartMenus(Cart cart, Menu menu) {
        Set<CartMenu> findCartMenus = new HashSet<>();
        Set<CartMenu> cartMenus = cart.getCartMenus();

        for(CartMenu cartMenu : cartMenus) {
            if(cartMenu.getMenu().getId().equals(menu.getId())){
                findCartMenus.add(cartMenu);
            }
        }
        return findCartMenus;
    }
}
