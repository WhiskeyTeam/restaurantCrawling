package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_restaurant")
@Entity
@Getter
@Setter
@ToString
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 음식점 식별자

    @Column(nullable = false)
    private String name;  // 음식점명

    //    @Enumerated(EnumType.STRING)
    //    @OneToOne
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private RestaurantCategory category; // 음식점 카테고리

    @Column(nullable = false)
    private String number;  // 음식점 전화번호

    @Column(nullable = false)
    private boolean isVisible;  // 공개 여부

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;   // 점주

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_address_id", nullable = false)
    private Address address;    // 음식점 주소

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weekly_open_close_time_id")
    private WeeklyOpenCloseTime weeklyOpenCloseTime;    // 요일별 영업시간

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus; // 메뉴 리스트

    @OneToOne
    @JoinColumn(name = "cover_image_id")
    private ImageFile coverImage;    // 음식점 대표 이미지
}
