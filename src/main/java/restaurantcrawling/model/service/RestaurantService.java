package restaurantcrawling.model.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restaurantcrawling.model.repository.*;
import restaurantcrawling.model.restaurantEntity.*;

import java.util.Objects;

@Service
@Slf4j
public class RestaurantService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private final OpenCloseTimeRepository openCloseTimeRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final WeeklyOpenCloseTimeRepository weeklyOpenCloseTimeRepository;
    private final ModelMapper modelMapper;

    public RestaurantService(AddressRepository addressRepository, MemberRepository memberRepository, OpenCloseTimeRepository openCloseTimeRepository, RestaurantRepository restaurantRepository, RestaurantCategoryRepository restaurantCategoryRepository, WeeklyOpenCloseTimeRepository weeklyOpenCloseTimeRepository) {
        this.addressRepository = addressRepository;
        this.memberRepository = memberRepository;
        this.openCloseTimeRepository = openCloseTimeRepository;
        this.restaurantRepository = restaurantRepository;
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.weeklyOpenCloseTimeRepository = weeklyOpenCloseTimeRepository;
        this.modelMapper = new ModelMapper();
    }

    @Transactional
    public void registNewRestaurant(Address address, Member member, WeeklyOpenCloseTime weeklyOpenCloseTime, RestaurantCategory restaurantCategory, Restaurant restaurant) {
        Address saved_address = registAddress(address);
        Member saved_member = registeringMember(member);
        RestaurantCategory saved_restaurantCategory = registRestaurantCategory(restaurantCategory);
        WeeklyOpenCloseTime saved_weeklyOpenCloseTime = registWeeklyOpenCloseTime(weeklyOpenCloseTime);
        restaurant.setAddress(saved_address);
        restaurant.setOwner(saved_member);
        restaurant.setCategory(saved_restaurantCategory);
        restaurant.setWeeklyOpenCloseTime(saved_weeklyOpenCloseTime);
        registRestaurant(restaurant);
    }

    private Address registAddress(Address address) {
        return addressRepository.save(address);
    }

    private Member registeringMember(Member member) {
        return memberRepository.findById(member.getId()).orElse(null);
    }

    private WeeklyOpenCloseTime registWeeklyOpenCloseTime(WeeklyOpenCloseTime weeklyOpenCloseTime) {
        return weeklyOpenCloseTimeRepository.save(weeklyOpenCloseTime);
    }

    private RestaurantCategory registRestaurantCategory(RestaurantCategory restaurantCategory) {
        if (!restaurantCategoryRepository.existsByCode(restaurantCategory.getCode())) {
            return restaurantCategoryRepository.save(restaurantCategory);
        } else {
            return restaurantCategoryRepository.findByCode(restaurantCategory.getCode());
        }
    }

    private Restaurant registRestaurant(Restaurant restaurant) {
        if (!(restaurantRepository.existsByName(restaurant.getName()) && restaurantRepository.existsByAddress(restaurant.getAddress()))) {
            return restaurantRepository.save(restaurant);
        } else {
            log.info("중복된 음식점입니다.");
            return null;
        }
    }
}
