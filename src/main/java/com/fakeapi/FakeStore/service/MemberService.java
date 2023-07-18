package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Member;
import com.fakeapi.FakeStore.dto.MemberSignUpDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public interface MemberService {
    Member addMember(MemberSignUpDTO memberSignUpDTO);
    Optional<Member> findByEmail(String email);

    Optional<Member> getMember(Long memberId);
}
