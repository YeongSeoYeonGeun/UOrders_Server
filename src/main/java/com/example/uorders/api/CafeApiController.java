package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
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

    /**
     *
     * 홈 화면
     */
    // 요청헤더 user_index?
    @GetMapping("/home")
    public ResponseEntity<Message> cafe() {

        List<Cafe> findCafes = cafeService.findCafes();

        //엔티티 -> DTO 변환
        List<CafeDto> collect = findCafes.stream()
                .map(c -> new CafeDto(c.getId(), c.getName(), c.getLocation(), c.getImage()))
                .collect(Collectors.toList());

        MessageWithData message = new MessageWithData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(StatusCode.OK);
        message.setMessage(ResponseMessage.READ_CAFE_LIST);
        message.setData(new Result_home(collect));
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    @Data
    @AllArgsConstructor
    static class Result_home<T> {
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

        MessageWithData messageWithData = new MessageWithData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Cafe cafe = cafeService.findOne(cafeId).orElse(null);
        if(cafe==null){
            Message message = new Message();
            message.setStatus(StatusCode.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_CAFE);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        Set<Menu> findMenus = cafeService.findMenus(cafeId);

        List<MenuDto> collect = findMenus.stream()
                .map(m -> new MenuDto(m.getId(), m.getName(), m.getPrice(), m.getImage()))
                .collect(Collectors.toList());

        Result_cafeIndex result = new Result_cafeIndex(cafe.getName(), cafe.getLocation(), true, collect);

        messageWithData.setStatus(StatusCode.OK);
        messageWithData.setMessage(ResponseMessage.READ_CAFE);
        messageWithData.setData(result);

        return new ResponseEntity<>(messageWithData, headers, HttpStatus.OK);

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
