package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.MenuService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Favorite;
import com.example.uorders.domain.Menu;
import com.example.uorders.domain.User;
import com.example.uorders.dto.cafe.CafeDetailDto;
import com.example.uorders.dto.cafe.OwnerCafeDetail;
import com.example.uorders.dto.menu.MenuDto;
import com.example.uorders.dto.menu.MenuResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final CafeService cafeService;
    private final MenuService menuService;


    @GetMapping("/menu")
    public ResponseEntity<Message> readMenu(@RequestParam("cafeIndex") Long cafeId, @RequestParam("menuIndex") Long menuId) {
        Cafe cafe = cafeService.findById(cafeId);
        Menu menu = menuService.findById(menuId);

        MenuDto menuDto = MenuDto.of(menu);
        MenuResponse response = new MenuResponse(menuDto);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_MENU, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /*점주용 메장 조회*/
    @GetMapping("/owner/cafe/{cafeIndex}")
    public ResponseEntity<Message> readMenu(@PathVariable("cafeIndex") Long cafeId ){
        Cafe cafe = cafeService.findById(cafeId);

        OwnerCafeDetail result = OwnerCafeDetail.of(cafe);
        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE, result);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Data
    static class createMenuRequest {

    }

    //점주용 메뉴 변경//
    @PostMapping("owner/menu")
    public ResponseEntity<Message> createMenu (@RequestBody MenuController.createMenuRequest request ){
        Menu menu = new Menu();
        menu.setName(request.getmenuName());
    }


//    @PostMapping
//    public ResponseEntity<Message> createFavoriteCafe (@RequestHeader("userIndex") Long userId, @RequestBody FavoriteController.createFavoriteRequest request){
//
//        User user = userService.findById(userId);
//        Cafe cafe = cafeService.findById(request.cafeIndex);
//
//        Favorite favorite = new Favorite();
//        favorite.setUser(user);
//        favorite.setCafe(cafe);
//        favoriteService.saveFavorite(favorite);
//
//        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_FAVORITE);
//        return new ResponseEntity<>(message,null,HttpStatus.OK);
//    }

}
