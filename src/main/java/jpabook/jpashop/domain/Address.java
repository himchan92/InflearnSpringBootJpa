package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

// 값 타입은 변경불가로 설계해야되니  getter만 허용하고 생성자에서만 값 초기화하여 이후 변경 막기
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
