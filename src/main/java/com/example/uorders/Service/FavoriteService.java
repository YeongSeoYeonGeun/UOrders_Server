package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Favorite;
import com.example.uorders.domain.FavoriteId;
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

    @Transactional
    public void saveFavorite(Favorite favorite) { favoriteRepository.save(favorite);}

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
}
