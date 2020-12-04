package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Owner;
import com.example.uorders.dto.cafe.CafeDto;
import com.example.uorders.dto.cafe.OwnerCafeDto;
import com.example.uorders.exception.OwnerNotFoundException;
import com.example.uorders.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Transactional
    public void saveOwner(Owner owner) { ownerRepository.save(owner); }

    public Owner findById(Long ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }

    

}
