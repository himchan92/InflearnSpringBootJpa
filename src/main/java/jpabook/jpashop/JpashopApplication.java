package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * JPA 설계 원칙
 * - 모든 연관관계는 지연로딩(LAZY) 설정
 * - setter 금지 : 값 변경을 사전에 막고 생성자초기화때만 설정
 */
@SpringBootApplication
public class JpashopApplication {

    public static void main(String[] args) {
//        Hello hello = new Hello();
//        hello.setData("hello");
//        String data = hello.getData();
//        System.out.println("data = " + data);

        //스프링부트 : 내장톰캣으로 별도설치/셋팅없이 바로 실행가능, Gradle 통해 라이브러리 자동 설정
        SpringApplication.run(JpashopApplication.class, args);
    }

}