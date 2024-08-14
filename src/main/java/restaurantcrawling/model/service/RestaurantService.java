package restaurantcrawling.model.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurantcrawling.model.repository.*;
import restaurantcrawling.model.restaurantEntity.Restaurant;
import restaurantcrawling.model.restaurantEntity.RestaurantCategory;

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
        Restaurant sameRestaurant = restaurantRepository.findByName(restaurant.getName());
        if (sameRestaurant != null) {
            if (!sameRestaurant.getAddress().getName().equals(restaurant.getAddress().getName())) {
                restaurantRepository.save(restaurant);
                log.info("성공");
            }
        } else {
            restaurantRepository.save(restaurant);
            log.info("성공");
        }
    }

//    @Transactional
//    public void registNewRestaurant(Address address, Member member, WeeklyOpenCloseTime weeklyOpenCloseTime, RestaurantCategory restaurantCategory, Restaurant restaurant) {

//        if (!checkSameRestaurant(restaurant, address)) {
//            return;
//        }
//
//        restaurant.setAddress(address);
//        restaurant.setOwner(member);
//        restaurant.setCategory(restaurantCategory);
//        restaurant.setWeeklyOpenCloseTime(weeklyOpenCloseTime);
//
//        restaurantRepository.save(restaurant);

//        Address saved_address = registAddress(address);
//        Member saved_member = registeringMember(member);
//        RestaurantCategory saved_restaurantCategory = registRestaurantCategory(restaurantCategory);
//        WeeklyOpenCloseTime saved_weeklyOpenCloseTime = registWeeklyOpenCloseTime(weeklyOpenCloseTime);
//        restaurant.setAddress(saved_address);
//        restaurant.setOwner(saved_member);
//        restaurant.setCategory(saved_restaurantCategory);
//        restaurant.setWeeklyOpenCloseTime(saved_weeklyOpenCloseTime);
//        registRestaurant(restaurant, address);
//    }
//
//    private boolean checkSameRestaurant(Restaurant restaurant, Address address) {
//        List<Restaurant> restaurantList = restaurantRepository.findAll();
//        for (Restaurant r : restaurantList) {
//            if (r.getAddress().getName().equals(address.getName()) && r.getName().equals(restaurant.getName())) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private Address registAddress(Address address) {
//        return addressRepository.save(address);
//    }
//
//    private Member registeringMember(Member member) {
//        return memberRepository.findById(member.getId()).orElse(null);
//    }
//
//    private WeeklyOpenCloseTime registWeeklyOpenCloseTime(WeeklyOpenCloseTime weeklyOpenCloseTime) {
//        return weeklyOpenCloseTimeRepository.save(weeklyOpenCloseTime);
//    }
//
//    private RestaurantCategory registRestaurantCategory(RestaurantCategory restaurantCategory) {
//        if (!restaurantCategoryRepository.existsByCode(restaurantCategory.getCode())) {
//            return restaurantCategoryRepository.save(restaurantCategory);
//        } else {
//            return restaurantCategoryRepository.findByCode(restaurantCategory.getCode());
//        }
//    }
//
//    private void registRestaurant(Restaurant restaurant, Address address) {
//        if (!(restaurantRepository.existsByName(restaurant.getName()) && (restaurant.getAddress().getName().equals(address.getName())))) {
//            restaurantRepository.save(restaurant);
//        } else {
//            log.info("중복된 음식점입니다.");
//        }
//    }
}
