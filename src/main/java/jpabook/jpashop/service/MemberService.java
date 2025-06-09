package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) //JPA 변경 시 필수 트랜젝션 설정, readOnly true 시 조회전용으로 성능최적화
@RequiredArgsConstructor //final 붙은 필드대상 생성자 DI 지원되고 간단하여 많이 사용
public class MemberService {

    private final MemberRepository memberRepository;

    // 생성자 주입 방식 : 많이 권장하는 방식으로 생성자 호출 시 DI 되어 값설정 완성, 객체 생성 시 호출되어 잘못코딩 시 오류체크되고 권장함
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원가입
    @Transactional //최상위 readOnly = true라서 조회만 되는데 변경 필요시 별도 설정해주면 됨
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    //중복회원 체크
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
