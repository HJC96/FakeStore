package com.fakeapi.FakeStore.repository;

import com.fakeapi.FakeStore.domain.Category;
import com.fakeapi.FakeStore.domain.Member;
import com.fakeapi.FakeStore.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void Member_Sign(){

//        Member member = new Member();
//        member.setEmail("gkswlcjs2@naver.com");
//        member.setMembername("HanJiChan");
//        member.setPassword("HanJiChan");
//
//        String dateStr = "2023-07-17";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(dateStr, formatter);
//        LocalDateTime dateTime = date.atStartOfDay(); // this will set the time to 00:00
//
//        member.setDate(dateTime);
//
//        Role role = new Role();
//        role.setName("ADMIN");
//
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        member.setRoles(roles);
//        //        memberRepository.setName("men's clothing");
//
//        memberRepository.save(member);
////        memberRepository.save();
    }


}