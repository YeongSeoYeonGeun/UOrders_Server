package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.FavoriteService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.*;
import com.example.uorders.dto.favorite.FavoriteDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        User user = userService.findById(id);

        List<Cafe> favoriteCafeList = userService.findFavoriteCafeList(user);

        List<FavoriteDto.FavoriteCafeDto> favoriteCafeDtoList = new ArrayList<>();
        for(Cafe cafe: favoriteCafeList) {
            FavoriteDto.FavoriteCafeDto favoriteCafeDto = FavoriteDto.FavoriteCafeDto.of(cafe);
            favoriteCafeDtoList.add(favoriteCafeDto);
        }

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_FAVORITE, favoriteCafeDtoList);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     *  즐겨찾는 매장 등록
     */
    @PostMapping("/users/favorite")
    public ResponseEntity<Message> createFavoriteCafe (@RequestHeader("userIndex") Long userId, @RequestBody createFavoriteRequest request){

        User user = userService.findById(userId);
        Cafe cafe = cafeService.findById(request.cafeIndex);

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
