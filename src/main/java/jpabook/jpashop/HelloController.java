package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        //Model : 화면단에 데이터 전달위한 객체
        model.addAttribute("data", "hello!!");

        //HTML 파일명과 받드시 일치!!
        //기본경로 templates 폴더
        return "hello";
    }
}
