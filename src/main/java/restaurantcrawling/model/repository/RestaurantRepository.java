package restaurantcrawling.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restaurantcrawling.model.restaurantEntity.Address;
import restaurantcrawling.model.restaurantEntity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant save(Restaurant restaurant);

    boolean existsByName(@Param("name") String number);
}
