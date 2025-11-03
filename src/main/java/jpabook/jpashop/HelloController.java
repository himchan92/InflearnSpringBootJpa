package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello") // GET 방식 url 매핑
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // view 단 데이터 전달 객체
        return "hello"; // view - templates - html 파일명 일치
    }
}