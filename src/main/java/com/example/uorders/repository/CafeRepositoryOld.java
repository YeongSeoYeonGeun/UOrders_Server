package com.example.uorders.repository;

import com.example.uorders.domain.Cafe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CafeRepositoryOld {

    @PersistenceContext
    private final EntityManager em;

    public void save(Cafe cafe) { em.persist(cafe); }

    public Cafe findOne(Long id) { return em.find(Cafe.class, id); }

    public List<Cafe> findAll(){
        return em.createQuery("select c from Cafe c", Cafe.class)
                .getResultList();
    }

    // 없어도 될 듯 ?
    // public List<Cafe> findByName(String name)
}
