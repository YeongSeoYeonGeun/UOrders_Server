package com.example.uorders.Service;

import com.example.uorders.domain.*;
import com.example.uorders.dto.favorite.FavoriteDto;
import com.example.uorders.dto.favorite.createFavoriteRequest;
import com.example.uorders.exception.CartNotFoundException;
import com.example.uorders.exception.FavoriteNotFoundException;
import com.example.uorders.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;

    @Transactional
    public void saveFavorite(Favorite favorite) { favoriteRepository.save(favorite);}

    public Favorite findById(Long userId, Long cafeId) {
        FavoriteId favoriteId = new FavoriteId(userId, cafeId);
        return favoriteRepository.findById(favoriteId).orElseThrow(() -> new FavoriteNotFoundException(userId, cafeId));
    }

    public Optional<Favorite> findOne(Long userId, Long cafeId) {
        FavoriteId favoriteId = new FavoriteId(userId, cafeId);
        return favoriteRepository.findById(favoriteId);
    }
    @Transactional
    public void deleteOne(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public List<Cafe> findCafes(Set<Favorite> favoriteSet) {

        List<Cafe> favoriteCafeList = new ArrayList<>();

        for (Favorite favorite: favoriteSet) {
            favoriteCafeList.add(favorite.getCafe());
        }

        return favoriteCafeList;
    }

    public List<FavoriteDto.FavoriteCafeDto> readFavoriteCafeList(User user) {
        List<Cafe> favoriteCafeList = userService.findFavoriteCafeList(user);

        List<FavoriteDto.FavoriteCafeDto> favoriteCafeDtoList = new ArrayList<>();
        for(Cafe cafe: favoriteCafeList) {
            FavoriteDto.FavoriteCafeDto favoriteCafeDto = FavoriteDto.FavoriteCafeDto.of(cafe, user.getLanguageCode());
            favoriteCafeDtoList.add(favoriteCafeDto);
        }

        return favoriteCafeDtoList;
    }

    @Transactional
    public void createFavorite(User user, Cafe cafe, createFavoriteRequest request) {
        Favorite favorite = Favorite.builder()
                .user(user)
                .cafe(cafe)
                .build();

        saveFavorite(favorite);
    }

}
