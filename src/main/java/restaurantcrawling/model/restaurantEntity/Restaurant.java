package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_restaurant")
@Entity
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 음식점 식별자

    @Column(nullable = false)
    private String name;  // 음식점명

    @Column(nullable = false)
    private String number;  // 음식점 전화번호

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;   // 점주

    @OneToOne
    @JoinColumn(name = "restaurant_address_id", nullable = false)
    private Address address;    // 음식점 주소

    @OneToOne
    @JoinColumn(name = "weekly_open_close_time_id")
    private WeeklyOpenCloseTime weeklyOpenCloseTime;    // 요일별 영업시간

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private RestaurantCategory category; // 음식점의 음식 카테고리s
}