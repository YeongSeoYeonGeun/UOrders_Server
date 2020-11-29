package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.MenuService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.repository.CafeRepository;
import com.example.uorders.repository.MenuRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuApiController {

    private final CafeService cafeService;
    private final MenuService menuService;


    @GetMapping("/menu")
    public ResponseEntity<Message> readMenu(@RequestParam("cafeIndex") Long cafeId, @RequestParam("menuIndex") Long menuId) {
        Cafe cafe = cafeService.findOne(cafeId).orElse(null);
        if(cafe == null){
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_CAFE);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        Menu menu = menuService.findOne(menuId).orElse(null);
        if(menu == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_MENU);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        MenuDto menuDto = new MenuDto(menu.getName(), menu.getPrice(), menu.getImage(), menu.getTemperatureSelect(), menu.getSizeSelect());

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_MENU, new Result(menuDto));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static
    class MenuDto {
        private String menuName;
        private int menuPrice;
        private String menuImage;
        private boolean selectTemperature;
        private boolean selectSize;
    }

    @Data
    @AllArgsConstructor
    static
    class Result<T> {
        private T menuInfo;
    }
}
