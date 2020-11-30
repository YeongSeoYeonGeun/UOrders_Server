package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.dto.cafe.CafeDto;
import com.example.uorders.dto.home.HomeResponse;
import com.example.uorders.exception.CafeNotFoundException;
import com.example.uorders.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;

    @Transactional
    public void saveCafe(Cafe cafe) { cafeRepository.save(cafe);};

    public Cafe findById(Long cafeId) {
        return cafeRepository.findById(cafeId).orElseThrow(() -> new CafeNotFoundException(cafeId));
    }
    /**
     * 전체 카페 조회
     */
    public List<Cafe> findCafes() { return cafeRepository.findAll(); }

    /**
     * 카페 메뉴 조회
     * 맞나..?
     */
    public Set<Menu> findMenus(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElse(null);
        Set<Menu> menuList = cafe.getMenuSet();
        return menuList;
    }

    public List<CafeDto> readCafeList() {

        List<Cafe> findCafes = findCafes();

        List<CafeDto> cafeDtoList = new ArrayList<>();
        for(Cafe cafe: findCafes) {
            cafeDtoList.add(CafeDto.of(cafe));
        }
        return cafeDtoList;
    }
}
