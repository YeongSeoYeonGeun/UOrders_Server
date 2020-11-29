package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.FavoriteService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final CafeService cafeService;
    private final FavoriteService favoriteService;

    /**
     *  회원 등록
     */
    //@PostMapping

    /**
     *  즐겨찾는 매장 조회
     */
    @GetMapping("/users/favorite")
    public ResponseEntity<Message> getFavoriteCafe (@RequestHeader("userIndex") Long id) {

        User user = userService.findOne(id).orElse(null);
        if(user == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        List<Long> favoriteCafeList = userService.findFavoriteCafeListEager(user);

        // 엔티티 -> DTO 변환
        List<CafeDto> collect = favoriteCafeList.stream()
                .map(c -> new CafeDto(cafeService.findOne(c).orElse(null).getId(), cafeService.findOne(c).orElse(null).getName(), cafeService.findOne(c).orElse(null).getLocation(), cafeService.findOne(c).orElse(null).getImage()))
                .collect(Collectors.toList());

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_FAVORITE, new Result_cafe(collect));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    class Result_cafe<T> {
        private T cafeInfo;
    }

    @Data
    @AllArgsConstructor
    public static class CafeDto {
        private Long cafeIndex;
        private String cafeName;
        private String cafeLocation;
        private String cafeImage;
    }

    /**
     *  즐겨찾는 매장 등록
     */
    @PostMapping("/users/favorite")
    public ResponseEntity<Message> createFavoriteCafe (@RequestHeader("userIndex") Long userId, @RequestBody createFavoriteRequest request){

        User user = userService.findOne(userId).orElse(null);
        Cafe cafe = cafeService.findOne(request.cafeIndex).orElse(null);

        if(user == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        if(cafe == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_CAFE);
            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setCafe(cafe);
        favoriteService.saveFavorite(favorite);

        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_FAVORITE);
        return new ResponseEntity<>(message,null,HttpStatus.OK);
        }

    @Data
    static class createFavoriteRequest {
        private Long cafeIndex;
    }

    /**
     *  즐겨찾는 매장 등록 해제 (삭제)
     */
    @DeleteMapping("users/favorite")
    public ResponseEntity<Message> deleteFavorite(@RequestHeader("userIndex") Long userId, @RequestHeader("cafeIndex") Long cafeId) {

    Favorite favorite = favoriteService.findOne(userId, cafeId).orElse(null);
    if(favorite == null) {
        Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER_OR_CAFE);
        return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
    }

    favoriteService.deleteOne(favorite);
    Message message = new Message(StatusCode.OK, ResponseMessage.DELETE_FAVORITE);
    return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
