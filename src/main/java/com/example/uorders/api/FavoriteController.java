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
import com.example.uorders.dto.favorite.FavoriteDto;
import com.example.uorders.dto.favorite.createFavoriteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/favorite")
public class FavoriteController {

    private final UserService userService;
    private final CafeService cafeService;
    private final FavoriteService favoriteService;

    /** 즐겨찾는 매장 조회 */
    @GetMapping
    public ResponseEntity<Message> getFavoriteCafe (@RequestHeader("userIndex") Long id) {
        User user = userService.findById(id);
        List<FavoriteDto.FavoriteCafeDto> response = favoriteService.readFavoriteCafeList(user);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_FAVORITE, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /** 즐겨찾는 매장 등록 */
    @PostMapping
    public ResponseEntity<Message> createFavoriteCafe (@RequestHeader("userIndex") Long userId, @RequestBody createFavoriteRequest request){
        User user = userService.findById(userId);
        Cafe cafe = cafeService.findById(request.getCafeIndex());
        favoriteService.createFavorite(user, cafe, request);

        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_FAVORITE);
        return new ResponseEntity<>(message,null,HttpStatus.OK);
    }

    /**  즐겨찾는 매장 등록 해제 (삭제) */
    @DeleteMapping
    public ResponseEntity<Message> deleteFavorite(@RequestHeader("userIndex") Long userId, @RequestHeader("cafeIndex") Long cafeId) {
        Favorite favorite = favoriteService.findById(userId,cafeId);
        favoriteService.deleteOne(favorite);

        Message message = new Message(StatusCode.OK, ResponseMessage.DELETE_FAVORITE);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
