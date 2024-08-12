package restaurantcrawling.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import restaurantcrawling.model.restaurantEntity.Address;
import restaurantcrawling.model.restaurantEntity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByAddress(Address address);

    boolean existsByName(String name);

    Restaurant findByName(String name);

    Restaurant findByAddress(Address address);
}
