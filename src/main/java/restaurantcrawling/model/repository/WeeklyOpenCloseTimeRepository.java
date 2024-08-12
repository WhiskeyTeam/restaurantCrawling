package restaurantcrawling.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurantcrawling.model.restaurantEntity.WeeklyOpenCloseTime;

public interface WeeklyOpenCloseTimeRepository extends JpaRepository<WeeklyOpenCloseTime, Long> {
}
