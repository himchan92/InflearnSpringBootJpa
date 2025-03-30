package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // DATA 바로 JSON 형식으로 반환
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();

        //별도 DTO로 반환함으로써 엔티티가 변경되어도 API스펙에 영향 안미침
        //엔티티 변경 시 오류 표시하여 제어
        //결론 : 별도 JSON 응답 구조 반환하는 DTO(Result)안에서 관리하면 유지보수 용이
        List<MemberDto> collect = findMembers.stream()
                                             .map(m -> new MemberDto(m.getName()))
            .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { //@Valid 효과로 값 유효성 체크
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 엔티티를 파라미터로 전달하여 외부에 노출시키면 절대안된다
    // 엔티티 변경 시 API 스펙이 변경되어 장애우려되니 엔티티를 파라미터에 셋팅하여 외부노출 금지!!
    // 해결1. 엔티티는 변경가능성이 높기에 필드명 변경되면 API 에러날수있으니 별도 DTO 만들어 전달하자
    // 해결2. API 호출/응답 각각 스펙에 맞는 별도 DTO(CreateMemberResponse, CreateMemberRequest)를 만들어 사용하자
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberReqeust request) {
        //수정 = 변경감지 = JPA UPDATE 수행지원
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberReqeust {
        private String name;
    }

    @Data
    @AllArgsConstructor //필드 전체 생성자
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
