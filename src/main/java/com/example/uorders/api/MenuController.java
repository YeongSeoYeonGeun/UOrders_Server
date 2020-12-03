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
import com.example.uorders.dto.menu.CreateMenuRequest;
import com.example.uorders.dto.menu.UpdateMenuRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
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
        if(menu == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_MENU);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

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

    //점주용 메뉴 생성//
    @PostMapping("owner/menu")
    public ResponseEntity<Message> createMenu (@RequestBody CreateMenuRequest request ){
        menuService.createMenu(request);

        Message message = new Message(StatusCode.OK, ResponseMessage.CREATE_MENU);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //점주용 메뉴 삭제//
    @DeleteMapping("owner/menu")
    public ResponseEntity<Message> deleteMenu (@RequestHeader("menuIndex") Long menuId, @RequestHeader("cafeIndex") Long cafeId){

        Menu menu = menuService.findById(menuId);
        Cafe cafe = cafeService.findById(cafeId);
        menuService.deleteMenu(menu, cafe);

        Message message = new Message(StatusCode.OK, ResponseMessage.DELETE_MENU);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //점주용 메뉴 수정///
    @PutMapping("/owner/menu")
    public ResponseEntity<Message> updateMenu (@RequestHeader("menuIndex") Long menuId, @RequestHeader("cafeIndex") Long cafeId, @RequestBody UpdateMenuRequest request) {
        Menu menu = menuService.findById(menuId);
        Cafe cafe = cafeService.findById(cafeId);
        menuService.UpdateMenu(menu, cafe, request);

        Message message = new Message(StatusCode.OK, ResponseMessage.UPDATE_MENU);
        return new ResponseEntity<>(message, HttpStatus.OK);
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
