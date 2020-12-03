package com.example.uorders.Service;

import com.example.uorders.domain.User;
import com.example.uorders.dto.cafe.CafeDto;
import com.example.uorders.dto.home.HomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final CafeService cafeService;

    public HomeResponse readHome(User user) {
        List<CafeDto> cafeDtoList = cafeService.readCafeList(user.getLanguageCode());
        return HomeResponse.of(user, cafeDtoList);
    }

}
