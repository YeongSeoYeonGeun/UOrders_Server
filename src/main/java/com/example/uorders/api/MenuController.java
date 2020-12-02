package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.MenuService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.dto.menu.MenuDto;
import com.example.uorders.dto.menu.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/menu")
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
}
