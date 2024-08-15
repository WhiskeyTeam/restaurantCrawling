package restaurantcrawling.model.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurantcrawling.model.repository.*;
import restaurantcrawling.model.restaurantEntity.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantCategoryRepository restaurantCategoryRepository, MemberRepository memberRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public RestaurantCategory getCategory(String categoryName) {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getCategory().getName().equals(categoryName)) {
                return restaurant.getCategory();
            }
        }

        RestaurantCategory category = new RestaurantCategory(null, categoryName);
        return restaurantCategoryRepository.save(category);
    }

    @Transactional
    public void registNewRestaurant(Restaurant restaurant) {
        if (!restaurantRepository.existsByName(restaurant.getNumber())) {
            log.info("등록 완료 : " + restaurantRepository.save(restaurant));
        }
    }
}
