package restaurantcrawling.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurantcrawling.model.restaurantEntity.Address;
import restaurantcrawling.model.restaurantEntity.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByAddress(Address address);

    boolean existsByName(String name);

    Restaurant findByName(String name);

    Restaurant findByAddress(Address address);

//    @Query(value = "SELECT addressName FROM tbl_category ORDER BY category_code DESC",
//            nativeQuery = true)
//    boolean existsByAddressName(String addressName);
}
