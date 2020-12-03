package com.example.uorders.dto.home;

import com.example.uorders.api.constants.Text;
import com.example.uorders.domain.User;
import com.example.uorders.dto.cafe.CafeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class HomeResponse {
    private String greetingText;
    private String mainText;
    private String nearestCafeText;
    private String favoriteCafeText;
    private List<CafeDto> cafeInfo;

    public static HomeResponse of(User user, List<CafeDto> cafeDtoList) {

        String greeting;
        String main;
        String nearestCafe;
        String favoriteCafe;

        switch (user.getLanguageCode()) {
            case "zh":
                greeting = Text.greeting_chinese(user.getName());
                main = Text.mainText_chinese;
                nearestCafe = Text.nearestCafe_chinese;
                favoriteCafe = Text.favoriteStore_chiense;
                break;
            default:
                greeting = Text.greeting_korean(user.getName());
                main = Text.mainText_korean;
                nearestCafe = Text.nearestCafe_korean;
                favoriteCafe = Text.favoriteStore_korean;
                break;
        }

        return new HomeResponse(greeting, main, nearestCafe, favoriteCafe, cafeDtoList);
    }
}
