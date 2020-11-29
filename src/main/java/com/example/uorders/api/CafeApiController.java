package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.FavoriteService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Favorite;
import com.example.uorders.domain.User;
import com.example.uorders.dto.cafe.CafeDetailDto;
import com.example.uorders.dto.cafe.CafeDto;
import com.example.uorders.dto.home.HomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CafeApiController {

    private final CafeService cafeService;
    private final UserService userService;
    private final FavoriteService favoriteService;

    /**
     *
     * 홈 화면
     */
    @GetMapping("/home")
    public ResponseEntity<Message> cafe(@RequestHeader("userIndex") Long userId) {

        User user = userService.findById(userId);

        String userName = user.getName();

        List<Cafe> findCafes = cafeService.findCafes();

        //엔티티 -> DTO 변환
        List<CafeDto> collect = findCafes.stream()
                .map(c -> new CafeDto(c.getId(), c.getName(), c.getLocation(), c.getImage()))
                .collect(Collectors.toList());

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE_LIST, new HomeResponse(userName, collect));
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    /**
     *
     * 매장 상세 조회
     */
    @GetMapping("/cafe/{cafeIndex}")
    public ResponseEntity<Message> menu(@RequestHeader("userIndex") Long userId, @PathVariable("cafeIndex") Long cafeId) {

        User user = userService.findById(userId);
        Cafe cafe = cafeService.findById(cafeId);

        boolean isFavorite = false;
        Favorite findFavorite = favoriteService.findOne(user.getId(), cafe.getId()).orElse(null);
        if(findFavorite != null) { isFavorite = true; }

        CafeDetailDto result = CafeDetailDto.of(cafe, isFavorite);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE, result);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
