package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // DATA 바로 JSON 형식으로 반환
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { //@Valid 효과로 값 유효성 체크
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 엔티티를 파라미터로 전달하여 외부에 노출시키면 절대안된다
    // 엔티티를 타인이 변경하고 그대로 파라미터로 전달할 시 변경으로 인한 에러로 장애날수있어서다.
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
