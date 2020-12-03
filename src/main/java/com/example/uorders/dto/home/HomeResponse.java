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

        String userName = user.getName();
        String languageCode = user.getLanguageCode();

        String greeting = Text.greeting(userName ,languageCode);
        String main = Text.main(languageCode);
        String nearestCafe = Text.nearestCafe(languageCode);
        String favoriteCafe = Text.favoriteCafe(languageCode);

        return new HomeResponse(greeting, main, nearestCafe, favoriteCafe, cafeDtoList);
    }
}
