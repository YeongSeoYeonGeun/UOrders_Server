package com.example.uorders.Service;

import com.example.uorders.domain.Menu;
import com.example.uorders.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public void saveMenu(Menu menu) { menuRepository.save(menu);}

    public List<Menu> findMenus() { return menuRepository.findAll(); }

    public Menu findOne(Long menuId) { return menuRepository.findOne(menuId); }
}
