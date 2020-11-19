package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.UserService;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.CartMenu;
import com.example.uorders.domain.Favorite;
import com.example.uorders.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final CafeService cafeService;

    /**
     *  즐겨찾는 매장 조회
     */
    @GetMapping("/users/favorite")
    public ResponseEntity<Message> getFavoriteCafe (@RequestHeader("userIndex") Long id) {

        User user = userService.findOne(id).orElse(null);
        if(user == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        List<Favorite> favorites = user.getFavorites();
        List<Cafe> favoriteCafeList = new ArrayList<>();

        for (Favorite favorite: favorites) {
            if(favorite.getFavorite_value() == 1) {
                favoriteCafeList.add(favorite.getCafe());
            }
        }

        // 엔티티 -> DTO 변환
        List<CafeDto> collect = favoriteCafeList.stream()
                .map(c -> new CafeDto(c.getId(), c.getName(), c.getLocation(), c.getImage()))
                .collect(Collectors.toList());

        MessageWithData message = new MessageWithData();
        message.setStatus(StatusCode.OK);
        message.setMessage(ResponseMessage.READ_FAVORITE);
        message.setData(new Result_cafe(collect));

        return new ResponseEntity<>(message, null, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    class Result_cafe<T> {
        private T cafeInfo;
    }

    @Data
    @AllArgsConstructor
    class CafeDto {
        private Long cafeIndex;
        private String cafeName;
        private String cafeLocation;
        private String cafeImage;
    }

    /**
     *  즐겨찾는 매장 등록
     */
    @PutMapping("/users/favorite")
    public ResponseEntity<Message> setFavoriteCafe (@RequestHeader("userIndex") Long userId, @PathVariable("cafeIndex") Long cafeId){

        User user = userService.findOne(userId).orElse(null);

        if(user == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        /// 복합키 구조로 리팩토링 필요
        List<Favorite> favoriteList = user.getFavorites();
        for (Favorite favorite: favoriteList ) {

            Cafe cafe = favorite.getCafe();

            if (cafe.getId() == cafeId) {
                favorite.setFavorite_value(1);

                Message message = new Message();
                message.setStatus(StatusCode.OK);
                message.setMessage(ResponseMessage.SET_FAVORITE);

                return new ResponseEntity<>(message,null,HttpStatus.OK);
            }
        }

        Message message = new Message();
        message.setStatus(StatusCode.BAD_REQUEST);
        message.setMessage(ResponseMessage.NOT_FOUND_CAFE);

        return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
    }
}
