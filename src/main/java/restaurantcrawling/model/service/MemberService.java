package restaurantcrawling.model.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import restaurantcrawling.model.repository.MemberRepository;
import restaurantcrawling.model.restaurantEntity.Member;

@Service
@Slf4j
public class MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    public MemberService(ModelMapper modelMapper, MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
    }

    public Member getTempMember() {
        return memberRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
    }
}
