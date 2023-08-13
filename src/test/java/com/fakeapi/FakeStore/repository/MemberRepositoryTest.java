package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.Member;
import com.fakeapi.FakeStore.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = new Member();
        member.setEmail("gkswlcjs2@naver.com");
        member.setName("HanJiChan");
        member.setPassword("HanJiChan");
        member.setBirthYear(1995);
        member.setBirthMonth(7);
        member.setBirthDay(17);
        member.setGender("Male");

        Role role = new Role();
        role.setName("ADMIN");

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        member.setRoles(roles);
    }

    @Test
    public void testSaveMember() {
        Member savedMember = memberRepository.save(member);
        assertNotNull(savedMember.getId()); // 저장된 멤버의 ID를 확인
        assertEquals(savedMember.getEmail(), member.getEmail()); // 저장된 멤버의 이메일을 확인
    }
}
