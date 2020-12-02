package com.example.uorders.Service;

import com.example.uorders.domain.Menu;
import com.example.uorders.exception.MenuNotFoundException;
import com.example.uorders.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public void saveMenu(Menu menu) { menuRepository.save(menu);}

    public List<Menu> findMenus() { return menuRepository.findAll(); }

    public Menu findById(Long menuId) { return menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException(menuId)); }



}
