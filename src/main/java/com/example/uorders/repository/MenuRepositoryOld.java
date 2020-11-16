package com.example.uorders.repository;

import com.example.uorders.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryOld {

    private final EntityManager em;

    public void save(Menu menu){
        if(menu.getId()==null){
            em.persist(menu);
        } else {
            em.merge(menu);
        }
    }

    public Menu findOne(Long id) { return em.find(Menu.class,id); }

    public List<Menu> findAll() {
        return em.createQuery("select m from Menu m", Menu.class)
                .getResultList();
    }
}
