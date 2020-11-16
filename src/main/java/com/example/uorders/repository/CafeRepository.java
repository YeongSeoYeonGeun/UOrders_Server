package com.example.uorders.repository;

import com.example.uorders.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    List<Cafe> findByName(String name);
}
