package restaurantcrawling.model.restaurantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_member")
@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 식별자

    @Column(nullable = false)
    private String name;    // 이름

    @Column(nullable = false)
    private String nickname;    // 닉네임

    @Column(nullable = false, unique = true)    // 중복 방지
    private String loginId; // 로그인 아이디 -> 기본 로그인, 소셜로그인

    @Column(nullable = false)
    private String email;   // 이메일

    private String password;    // 비밀번호

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;   // 생성 시점에 현재 시간 초기화

    private LocalDateTime deletedAt;   // 삭제 시점에 현재 시간 초기화

    @Column(nullable = false)
    private boolean isActive;   // 활성화/비활성화
}