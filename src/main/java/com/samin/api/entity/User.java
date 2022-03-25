package com.samin.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder            // builder를 사용할 수 있게 함
@Entity             // jpa entity임을 알림
@Getter             // user 필드값의 getter를 자동으로 생성합니다
@NoArgsConstructor  // 인자 없는 생서앚를 자동으로 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name = "user")   // 'user' 테이블과 매핑됨을 명시
public class User {

    @Id     // primarykey임을 알림
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // pk생성전략을 db에 위임한다는 의미
    private long msrl;

    @Column(nullable = false, unique = true, length = 30)   // uid 칼럼을 명시
    private String uid;

    @Column(nullable = false, length = 100)             // name 칼럼을 명시
    private String name;
}
