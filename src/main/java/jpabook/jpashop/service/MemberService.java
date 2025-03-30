package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) //JPA 변경작업시 필수셋팅, 조회만 하는경우 별도 readOnly 속성 설정 권장
@RequiredArgsConstructor //final 필드 대상 롬복 DI 지원되며 실무에서 권장함
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired //생성자 주입방식 : 생성자 생성시점 주입되어 이후 변경될일이 없어 권장
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional //변경작업이므로 클래스전체에는 readOnly줬으나 여기서는 별도 설정해줘야 적용됨
    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원체크
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    //클래스전체에 readOnly 걸어줘서 적용됨
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //단건 조회
    //클래스전체에 readOnly 걸어줘서 적용됨
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        //변경대상 id 기준 조회 후 setter 하여 name 값 변경
        //Transactional 안에서 setter로 인한 변경 시 Transactional가 변경감지 체크
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
