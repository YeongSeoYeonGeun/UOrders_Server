package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Favorite;
import com.example.uorders.domain.Menu;
import com.example.uorders.domain.User;
import com.example.uorders.dto.cafe.CafeDetailDto;
import com.example.uorders.dto.cafe.CafeDto;
import com.example.uorders.dto.cafe.UpdateCafeRequest;
import com.example.uorders.dto.home.HomeResponse;
import com.example.uorders.exception.CafeNotFoundException;
import com.example.uorders.repository.CafeRepository;
import com.example.uorders.util.Translator;
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
    private final FavoriteService favoriteService;

    @Transactional
    public void saveCafe(Cafe cafe) { cafeRepository.save(cafe);};

    public Cafe findById(Long cafeId) {
        return cafeRepository.findById(cafeId).orElseThrow(() -> new CafeNotFoundException(cafeId));
    }

    public List<Cafe> findCafes() { return cafeRepository.findAll(); }

    public List<CafeDto> readCafeList(String languageCode) {

        List<Cafe> findCafes = findCafes();

        List<CafeDto> cafeDtoList = new ArrayList<>();
        for(Cafe cafe: findCafes) {
            cafeDtoList.add(CafeDto.of(cafe, languageCode));
        }
        return cafeDtoList;
    }

    @Transactional
    public void updateCafe(Cafe cafe, UpdateCafeRequest request) {
        cafe.setName(request.getCafeName());
        cafe.setName_chinese(Translator.translate(request.getCafeName(),"zh"));
        cafe.setName_english(Translator.translate(request.getCafeName(),"en"));

        cafe.setLocation(request.getCafeLocation());
        cafe.setLocation_chinese(Translator.translate(request.getCafeLocation(),"zh"));
        cafe.setLocation_english(Translator.translate(request.getCafeLocation(),"en"));

        saveCafe(cafe);
    }

    public CafeDetailDto readCafeDetail(User user, Cafe cafe) {

        boolean isFavorite = false;
        Favorite findFavorite = favoriteService.findOne(user.getId(), cafe.getId()).orElse(null);
        if(findFavorite != null) { isFavorite = true; }

        return CafeDetailDto.of(cafe, isFavorite, user.getLanguageCode());
    }
}