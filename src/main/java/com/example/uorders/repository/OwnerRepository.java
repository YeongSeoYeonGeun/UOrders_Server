package com.example.uorders.repository;

import com.example.uorders.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query(value = "select * from owner where id_owner = :ownerId and password = :ownerPw LIMIT 1", nativeQuery = true)
    Optional<Owner> findByIdAndPw(String ownerId, String ownerPw);
}
