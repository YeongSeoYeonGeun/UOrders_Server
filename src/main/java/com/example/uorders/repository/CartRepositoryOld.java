package com.example.uorders.repository;

import com.example.uorders.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CartRepositoryOld {

    private final EntityManager em;

    public void save(Cart cart) {em.persist(cart); }

    public Cart findOne(Long id) { return em.find(Cart.class,id); }
}
