package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_restaurant_category")
@Entity
@Getter
public class RestaurantCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 카테고리 식별자

    @Column(nullable = false)
    private String name;    // 카테고리명
}