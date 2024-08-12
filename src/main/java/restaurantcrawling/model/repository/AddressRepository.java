package restaurantcrawling.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurantcrawling.model.restaurantEntity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
