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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final CafeService cafeService;
    private final MenuService menuService;


    @GetMapping
    public ResponseEntity<Message> readMenu(@RequestParam("cafeIndex") Long cafeId, @RequestParam("menuIndex") Long menuId) {
        Cafe cafe = cafeService.findById(cafeId);
        Menu menu = menuService.findById(menuId);

        MenuDto menuDto = MenuDto.of(menu);
        MenuResponse response = new MenuResponse(menuDto);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_MENU, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /*점주용 메뉴 조회*/
    @GetMapping("/owner/cafe/{cafeIndex}")
    public ResponseEntity<Message> readMenu(@PathVariable("cafeIndex") Long cafeId ){
        System.out.println("아무거나 ");
        Cafe cafe = cafeService.findById(cafeId);

        System.out.println("아무거나 ");
        OwnerCafeDetail result = OwnerCafeDetail.of(cafe);
        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE, result);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}
