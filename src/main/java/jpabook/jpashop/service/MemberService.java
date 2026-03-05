package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //가입
    @Transactional //변경작업에는 필수 설정
    public Long join(Member member) {
        //중복회원체크
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체조회
    @Transactional(readOnly = true) //변경작업에는 필수 설정, 조회전용설정
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원단건조회
    @Transactional(readOnly = true) //변경작업에는 필수 설정, 조회전용설정
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
