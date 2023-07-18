package com.fakeapi.FakeStore.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
//@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MEMBER_NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DATETIME")
    @CreationTimestamp // 현재시간이 저장될 때 자동으로 생성.
    private LocalDateTime date;

    @Column(nullable = false, name = "BIRTHYEAR")
    private Integer birthYear;

    @Column(nullable = false, name = "BIRTHMONTH")
    private Integer birthMonth;

    @Column(nullable = false, name = "BIRTHDAY")
    private Integer birthDay;

    @Column(length = 10, nullable = false, name = "GENDER")
    private String gender;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MEMBER_ROLE",
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                ", birthYear=" + birthYear +
                ", birthMonth=" + birthMonth +
                ", birthDay=" + birthDay +
                ", gender='" + gender + '\'' +
                ", roles=" + roles +
                '}';
    }

    public void addRole(Role role){
        roles.add(role);
    }
}
