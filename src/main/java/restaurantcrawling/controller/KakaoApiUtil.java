package restaurantcrawling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoApiUtil {
    
    @Value("${kakao.apiKey}")
    private String apiKey;

    @GetMapping("/kakao-api-key")
    public String getKakaoApiKey() {
        return apiKey;
    }
}
