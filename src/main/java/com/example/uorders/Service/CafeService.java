package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;

    @Transactional
    public void saveCafe(Cafe cafe) { cafeRepository.save(cafe);};

    /**
     * 전체 카페 조회
     */
    public List<Cafe> findCafes() { return cafeRepository.findAll(); }

    /**
     * 단일 카페 조회
     */
    public Optional<Cafe> findOne(Long cafeId) { return cafeRepository.findById(cafeId); }

    /**
     * 카페 메뉴 조회
     * 맞나..?
     */
    public Set<Menu> findMenus(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElse(null);
        Set<Menu> menuList = cafe.getMenus();
        return menuList;
    }
}
