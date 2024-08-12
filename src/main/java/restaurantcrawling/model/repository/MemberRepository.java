package restaurantcrawling.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurantcrawling.model.restaurantEntity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
