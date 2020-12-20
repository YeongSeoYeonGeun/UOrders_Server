package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.dto.menu.CreateMenuRequest;
import com.example.uorders.exception.MenuNotFoundException;
import com.example.uorders.repository.CafeRepository;
import com.example.uorders.repository.MenuRepository;
import com.example.uorders.dto.menu.UpdateMenuRequest;
import com.example.uorders.util.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeService cafeService;

    @Transactional
    public void saveMenu(Menu menu) { menuRepository.save(menu);}

    public List<Menu> findMenus() { return menuRepository.findAll(); }

    public Menu findById(Long menuId) { return menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException(menuId)); }

    @Transactional
    public void createMenu(CreateMenuRequest request){

        Cafe cafe = cafeService.findById(request.getCafeIndex());

        Menu menu = Menu.builder()
                .name(request.getMenuName())
                .price(request.getMenuPrice())
                .cafe(cafe)
                .image(request.getMenuImage())
                .cartMenuSet(new HashSet<>())
                .orderMenuSet(new HashSet<>())
                .sizeSelect(request.isMenuSize())
                .temperatureSelect(request.isMenuTemperature())
                .status(request.getSoldOut())
                .build();
    }

    @Transactional
    public void deleteMenu(Menu menu, Cafe cafe) {
        cafe.getMenuSet().remove(menu);
        menu.setCafe(null);
        menuRepository.delete(menu);
    }

    @Transactional
    public void UpdateMenu(Menu menu, UpdateMenuRequest request) {
        menu.setName(request.getMenuName());
        menu.setName_chinese(Translator.translate(request.getMenuName(),"zh"));
        menu.setName_english(Translator.translate(request.getMenuName(),"en"));
        menu.setSizeSelect(request.isMenuSize());
        menu.setTemperatureSelect(request.isMenuTemperature());
        menu.setPrice(request.getMenuPrice());
        menu.setStatus(request.getSoldOut());
        menu.setImage(request.getMenuImage());

        saveMenu(menu);
    }

}
