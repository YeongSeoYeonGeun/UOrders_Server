package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

    @Transactional
    public void saveCafe(Cafe cafe) { cafeRepository.save(cafe);};

    private final CafeRepository cafeRepository;
    /**
     * 전체 카페 조회
     */
    public List<Cafe> findCafes() { return cafeRepository.findAll(); }

    /**
     * 단일 카페 조회
     */
    public Cafe findOne(Long cafeId) { return cafeRepository.findOne(cafeId); }

    /**
     * 카페 메뉴 조회
     * 맞나..?
     */
    public List<Menu> findMenus(Long cafeId) { return cafeRepository.findOne(cafeId).getMenus(); }
}
