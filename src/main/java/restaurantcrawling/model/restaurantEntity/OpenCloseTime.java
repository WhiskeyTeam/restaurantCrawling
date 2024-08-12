package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_open_close_time")
@Entity
@Getter
@Setter
public class OpenCloseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String openTime;

    @Column(nullable = false)
    private String closeTime;

    //    영업중 여부
    @Column(nullable = false)
    private boolean isOpen;
}
