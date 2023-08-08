package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.Member;
import com.fakeapi.FakeStore.domain.Role;
import com.fakeapi.FakeStore.dto.MemberSignUpDTO;
import com.fakeapi.FakeStore.repository.MemberRepository;
import com.fakeapi.FakeStore.repository.RoleRepository;
import com.fakeapi.FakeStore.security.jwt.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Member addMember(MemberSignUpDTO memberSignUpDTO) {
        String email = memberSignUpDTO.getEmail();
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email already in use: " + email);
        }
        Member member = new Member();
        log.info(memberSignUpDTO.getName());
        log.info(memberSignUpDTO.getEmail());
        member.setName(memberSignUpDTO.getName());
        member.setEmail(memberSignUpDTO.getEmail());
        member.setPassword(passwordEncoder.encode(memberSignUpDTO.getPassword()));
        member.setBirthYear(Integer.parseInt(memberSignUpDTO.getBirthYear()));
        member.setBirthMonth(Integer.parseInt(memberSignUpDTO.getBirthMonth()));
        member.setBirthDay(Integer.parseInt(memberSignUpDTO.getBirthDay()));
        member.setGender(memberSignUpDTO.getGender());

        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        member.addRole(userRole.get());
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Optional<Member> getMember(Long memberId) {  return memberRepository.findById(memberId); }


}
