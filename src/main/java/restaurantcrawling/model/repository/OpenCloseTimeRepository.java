package restaurantcrawling.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurantcrawling.model.restaurantEntity.OpenCloseTime;

public interface OpenCloseTimeRepository extends JpaRepository<OpenCloseTime, Long> {
}
