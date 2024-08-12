package restaurantcrawling.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurantcrawling.model.restaurantEntity.RestaurantCategory;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Long> {
    boolean existsByCode(String code);

    RestaurantCategory findByCode(String code);
}
