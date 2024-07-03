package com.group.libraryapp.domain.user;

import javax.persistence.*;

@Entity //mySQL의 user테이블과 user객체를 매핑시켜주기 위한 어노테이션
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null; //유저테이블에 매핑하기 위해 설정해줌.

    @Column(nullable = false, length = 20) //name varchar(20)
    private String name;
    private Integer age;

    protected User(){}  //빈 생성자 설정 = jpa를 사용하기 위해 기본생성자 꼭 필요!
    
    public User(String name, Integer age) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
