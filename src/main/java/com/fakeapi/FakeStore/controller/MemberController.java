package com.fakeapi.FakeStore.controller;

import com.fakeapi.FakeStore.domain.Member;
import com.fakeapi.FakeStore.domain.RefreshToken;
import com.fakeapi.FakeStore.domain.Role;
import com.fakeapi.FakeStore.dto.*;
import com.fakeapi.FakeStore.security.jwt.util.JwtTokenizer;
import com.fakeapi.FakeStore.service.MemberService;
import com.fakeapi.FakeStore.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated @Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignUpDTO memberSignUpDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        Member saveMember = memberService.addMember(memberSignUpDTO);

        MemberSignUpResponseDTO memberSignupResponse = new MemberSignUpResponseDTO();
        memberSignupResponse.setMemberId(saveMember.getId());
        memberSignupResponse.setName(saveMember.getName());
        memberSignupResponse.setRegdate(saveMember.getDate());
        memberSignupResponse.setEmail(saveMember.getEmail());

        // 회원가입
        return new ResponseEntity(memberSignupResponse, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDTO memberLoginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // email이 없을 경우 Exception이 발생한다. Global Exception에 대한 처리가 필요하다.
        Member member = memberService.findByEmail(memberLoginDTO.getEmail()).get();
        if(!passwordEncoder.matches(memberLoginDTO.getPassword(), member.getPassword())){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        // List<Role> ===> List<String>
        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getId(), member.getEmail(), member.getName(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getId(), member.getEmail(), member.getName(), roles);

        // RefreshToken을 DB에 저장한다. 성능 때문에 DB가 아니라 Redis에 저장하는 것이 좋다.
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberId(member.getId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        MemberLoginResponseDTO loginResponse = MemberLoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getId())
                .nickname(member.getName())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }
    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        log.debug("Attempting to logout");
        refreshTokenService.deleteRefreshToken(refreshTokenDTO.getRefreshToken());
        log.debug("Logout successful");


        return new ResponseEntity(HttpStatus.OK);
    }

    /*
1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다.
2. RefreshToken이 유효한지 체크한다.
3. AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
 */
    @PostMapping("/refreshToken")
    public ResponseEntity requestRefresh(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDTO.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());

        Long memberId = Long.valueOf((Integer)claims.get("memberId"));

        Member member = memberService.getMember(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        String accessToken = jwtTokenizer.createAccessToken(memberId, email, member.getName(), roles);

        MemberLoginResponseDTO memberLoginResponseDTO = MemberLoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenDTO.getRefreshToken())
                .memberId(member.getId())
                .nickname(member.getName())
                .build();
        return new ResponseEntity(memberLoginResponseDTO, HttpStatus.OK);
    }


}
