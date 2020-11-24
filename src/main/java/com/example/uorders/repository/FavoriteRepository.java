package com.example.uorders.repository;


import com.example.uorders.domain.Favorite;
import com.example.uorders.domain.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
}
