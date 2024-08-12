package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_restaurant_category")
@Entity
@Getter
@Setter
public class RestaurantCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 카테고리 식별자

    // TODO: KAKAO API 에서 카테고리 받아오면 저장
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;    // 카테고리명
}
