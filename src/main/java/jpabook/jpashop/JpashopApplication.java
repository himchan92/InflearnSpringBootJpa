package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    spring-boot-starter-web
    - 톰캣 내장되어 별도 설치 필요없음

    spring-boot-starter-logging
    - log 지원, 최신 실무는 롬복 Slf4j 사용

    spring-starter-test
    - junit 테스트 라이브러리
    - Mock, AssertJ 지원

    starter-jpa
    - jpa, db 등 설정 지원

    devtools
    - 서버 재기동없이 view 수정 후 refresh 하면 바로 반영 지원

    엔티티 설계 시 주의사항
    - setter 가급 적 사용하지말자 : 변경 가능성이 있어 유지보수 힘듬
    - 모든 연간관계 매핑부분에는 fetch = FetchType.LAZY(지연로딩) 설정
 */
@SpringBootApplication
public class JpashopApplication {

    public static void main(String[] args) {

        Hello hello = new Hello();
        hello.setData("hello");
        String data = hello.getData();
        System.out.println("data = " + data);

        SpringApplication.run(JpashopApplication.class, args);
    }

}