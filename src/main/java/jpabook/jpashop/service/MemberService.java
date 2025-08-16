package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) //조회만 하는경우 별도 읽기설정모드로 성능최적화
@RequiredArgsConstructor //final 필드 대상 DI 지원으로 많이 사용
public class MemberService {

  private final MemberRepository memberRepository;

  //회원가입
  @Transactional //JPA 변경은 트랜젝션 필수설정, 클래스전체 readOnly라서 여기서는 별도설정
  public Long join(Member member) {
    validateDuplicateMember(member);
    memberRepository.save(member);
    return member.getId();
  }

  //중복회원검증
  private void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());

    if(!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  //회원전체조회
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  //회원단건조회
  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }
}
