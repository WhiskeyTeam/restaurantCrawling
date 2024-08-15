package restaurantcrawling.controller;

import com.whiskey.libs.rest.request.RequestMethod;
import com.whiskey.libs.rest.request.RestInvoker;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import restaurantcrawling.model.dto.*;
import restaurantcrawling.model.restaurantEntity.*;
import restaurantcrawling.model.service.MemberService;
import restaurantcrawling.model.service.RestaurantService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class CrawlingController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;


    @Value("${kakao.restApiKey}")
    private String restApiKey;

    private final RestaurantService restaurantService;

    public CrawlingController(RestaurantService restaurantService, MemberService memberService, ModelMapper modelMapper) {
        this.restaurantService = restaurantService;
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/showRestaurantList")
    @ResponseBody
    @Transactional
    public void showRestaurantList(HttpSession session, @RequestParam double[] lat, @RequestParam double[] lng, @RequestParam int[] radius) throws Exception {

        session.setAttribute("member", getTempMember());

        for (int i = 0; i < lat.length; i++) {
            String apiUrl = "https://dapi.kakao.com/v2/local/search/category.json?" +
                    "category_group_code=" + "FD6" +
                    "&page=1" +
                    "&size=15" +
                    "&sort=distance" +
                    "&x=" + lng[i] +
                    "&y=" + lat[i] +
                    "&radius=" + radius[i];

            var invoker = new RestInvoker<>(apiUrl, JSONDataDTO.class);
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "KakaoAK " + this.restApiKey);
            JSONDataDTO response = invoker.request(headers, RequestMethod.GET);

            log.info("""
                    =====================================================================================================================
                    """);
            for (DocumentDTO document : response.getDocuments()) {
                System.out.println(document.toString());
                registNewRestaurant(document, (Member) session.getAttribute("member"));
            }
        }
    }

    @PostMapping("/showRestaurantListAuto")
    @ResponseBody
    @Transactional
    public void showRestaurantListAuto(HttpSession session, @RequestParam double lat, @RequestParam double lng, @RequestParam int radius) throws Exception {

        session.setAttribute("member", getTempMember());

        String apiUrl = "https://dapi.kakao.com/v2/local/search/category.json?" +
                "category_group_code=" + "FD6" +
                "&page=1" +
                "&size=15" +
                "&sort=distance" +
                "&x=" + lng +
                "&y=" + lat +
                "&radius=" + radius;

        var invoker = new RestInvoker<>(apiUrl, JSONDataDTO.class);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "KakaoAK " + this.restApiKey);
        JSONDataDTO response = invoker.request(headers, RequestMethod.GET);

        Map<Long, DocumentDTO> documents = new HashMap<>();

        for (DocumentDTO document : response.getDocuments()) {
//
            log.info(document.getId().toString());
            documents.put(document.getId(), document);
            if (document.getPhone() != null && !document.getPhone().equals("")) {
                log.info(document.toString());
                registNewRestaurant(document, (Member) session.getAttribute("member"));
            }
        }
    }

    private Member getTempMember() {
        return memberService.getTempMember();
    }

    private void registNewRestaurant(DocumentDTO document, Member member) {

        String categoryName;
        String[] category = document.getCategory_name().split(" > ");
        if (category.length > 1) {
            categoryName = category[1];
        } else {
            categoryName = "기타";
        }
        Restaurant restaurant = new Restaurant(
                document.getId(),
                document.getPlace_name(),
                restaurantService.getCategory(categoryName),
                document.getPhone(),
                true,
                member,
                new Address(null, document.getAddress_name(), document.getY(), document.getX()),
                new WeeklyOpenCloseTime(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ),
                null,
                null
        );

        log.info("restaurant : " + restaurant.toString());
        restaurantService.registNewRestaurant(restaurant);
    }
}
