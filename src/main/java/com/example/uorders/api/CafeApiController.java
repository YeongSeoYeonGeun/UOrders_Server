package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.FavoriteService;
import com.example.uorders.Service.UserService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Favorite;
import com.example.uorders.domain.Menu;
import com.example.uorders.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Chars;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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

        User user = userService.findOne(userId).orElse(null);
        if(user == null) {
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);

            return new ResponseEntity<>(message, null, HttpStatus.BAD_REQUEST);
        }
        String userName = user.getName();

        List<Cafe> findCafes = cafeService.findCafes();

        //엔티티 -> DTO 변환
        List<CafeDto> collect = findCafes.stream()
                .map(c -> new CafeDto(c.getId(), c.getName(), c.getLocation(), c.getImage()))
                .collect(Collectors.toList());

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE_LIST, new Result_home(userName, collect));
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @Data
    @AllArgsConstructor
    static class Result_home<T> {
        private String userName;
        private T cafeInfo;
    }

    @Data
    @AllArgsConstructor
    static class CafeDto {
        private Long cafeIndex;
        private String cafeName;
        private String cafeLocation;
        private String cafeImage;
    }

    /**
     *
     * 매장 상세 조회
     */
    @GetMapping("/cafe/{cafeIndex}")
    public ResponseEntity<Message> menu(@RequestHeader("userIndex") Long userId, @PathVariable("cafeIndex") Long cafeId) {

        // 유저 isFavorite
        User user = userService.findOne(userId).orElse(null);
        if(user == null) {
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        Cafe cafe = cafeService.findOne(cafeId).orElse(null);
        if(cafe==null){
            Message message = new Message(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_CAFE);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        boolean isFavorite = false;
        Favorite findFavorite = favoriteService.findOne(user.getId(), cafe.getId()).orElse(null);
        if(findFavorite != null) { isFavorite = true; }

        Set<Menu> findMenus = cafeService.findMenus(cafeId);

        List<MenuDto> collect = findMenus.stream()
                .map(m -> new MenuDto(m.getId(), m.getName(), m.getPrice(), m.getImage()))
                .collect(Collectors.toList());

        Result_cafeIndex result = new Result_cafeIndex(cafe.getName(), cafe.getLocation(), isFavorite, collect);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE, result);

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @Data
    @AllArgsConstructor
    static class Result_cafeIndex<T> {
        private String cafeName;
        private String cafeLocation;
        private Boolean isFavorite;

        private T menuInfo;
    }

    @Data
    @AllArgsConstructor
    static class MenuDto {
        private Long menuIndex;
        private String menuName;
        private int menuPrice;
        private String menuImage;
    }


}
