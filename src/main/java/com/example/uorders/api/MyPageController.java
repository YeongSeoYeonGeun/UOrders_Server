package com.example.uorders.api;

import com.example.uorders.Service.CafeService;
import com.example.uorders.Service.MyPageService;
import com.example.uorders.Service.OwnerService;
import com.example.uorders.api.constants.Message;
import com.example.uorders.api.constants.ResponseMessage;
import com.example.uorders.api.constants.StatusCode;
import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Owner;
import com.example.uorders.dto.cafe.UpdateCafeRequest;
import com.example.uorders.dto.myPage.ReadCafeRevenueDto;
import com.example.uorders.dto.myPage.myPageDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mypage")
public class MyPageController {

    private final OwnerService ownerService;
    private final CafeService cafeService;
    private final MyPageService myPageService;


    /** 마이페이지 화면 조회 */
    @GetMapping
    public ResponseEntity<Message> myPage(@RequestHeader("ownerIndex") Long ownerId, @RequestHeader("cafeIndex") Long cafeId) {
        Owner owner = ownerService.findById(ownerId);
        Cafe cafe = cafeService.findById(cafeId);

        myPageDto response = myPageService.myPage(cafeId);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_MYPAGE, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    /** 마이페이지 - 매장 정보 변경 */
    @PutMapping("/cafe")
    public ResponseEntity<Message> updateCafe(@RequestHeader("ownerIndex") Long ownerId, @RequestHeader("cafeIndex") Long cafeId, @RequestBody UpdateCafeRequest request) {
        Owner owner = ownerService.findById(ownerId);
        Cafe cafe = cafeService.findById(cafeId);

        cafeService.updateCafe(cafe, request);
        Message message = new Message(StatusCode.OK, ResponseMessage.UPDATE_CAFE);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    /** 마이페이지 - 매장 수익 내역 조회 */
    @GetMapping("/order")
    public ResponseEntity<Message> readCafeRevenue(@RequestHeader("ownerIndex") Long ownerId, @RequestHeader("cafeIndex") Long cafeId) {
        Owner owner = ownerService.findById(ownerId);
        Cafe cafe = cafeService.findById(cafeId);

        ReadCafeRevenueDto response = myPageService.readCafeRevenue(cafe);

        Message message = new Message(StatusCode.OK, ResponseMessage.READ_CAFE_REVENUE, response);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
