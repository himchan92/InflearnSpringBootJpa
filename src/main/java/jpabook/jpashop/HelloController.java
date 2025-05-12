package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello222"); //view 단으로 데이터 전달해주는 객체
        return "hello"; //타임리프가 html 매핑지원해줘서 해당 파일명으로 template html 파일찾아 UI 보여주는것
    }
}
