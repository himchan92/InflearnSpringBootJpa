package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("hello")
  public String hello(Model model) { //VIEW단 전달위한 MODEL 객체
    model.addAttribute("data", "hello"); //DATA에 HELLO 값 전달
    return "hello"; //hello.html 파일경로로 전달
  }
}