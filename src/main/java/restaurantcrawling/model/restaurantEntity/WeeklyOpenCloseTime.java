package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_weekly_open_close_time")
@Entity
@Getter
@Setter
public class WeeklyOpenCloseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "monday_id")
    private OpenCloseTime monday;

    @OneToOne
    @JoinColumn(name = "tuesday_id")
    private OpenCloseTime tuesday;

    @OneToOne
    @JoinColumn(name = "wednesday_id")
    private OpenCloseTime wednesday;

    @OneToOne
    @JoinColumn(name = "thursday_id")
    private OpenCloseTime thursday;

    @OneToOne
    @JoinColumn(name = "friday_id")
    private OpenCloseTime friday;

    @OneToOne
    @JoinColumn(name = "saturday_id")
    private OpenCloseTime saturday;

    @OneToOne
    @JoinColumn(name = "sunday_id")
    private OpenCloseTime sunday;
}