package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_address")
@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 주소 식별자

    @Column(nullable = false)
    private String name;    // 주소

    @Column(name = "latitude", nullable = false)
    private Double latitude;    // 위도

    @Column(name = "longitude", nullable = false)
    private double longitude;    // 경도
}