package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") //GET 방식 URL 경로 매핑
    public String hello(Model model) {
        //model 객체로 화면단에 값 전달 셋팅
        model.addAttribute("data", "update hello!!!");

        // html 파일명과 반드시 일치
        // 스프링부트 타임리프가 자동으로 view 연동지원하여 별도 설정 필요없음
        // 첫화면 정적 경로는 static
        return "hello";
    }
}
