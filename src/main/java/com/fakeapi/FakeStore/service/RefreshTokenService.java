package com.fakeapi.FakeStore.service;

import com.fakeapi.FakeStore.domain.RefreshToken;
import com.fakeapi.FakeStore.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public interface RefreshTokenService {

    RefreshToken addRefreshToken(RefreshToken refreshToken);

    void deleteRefreshToken(String refreshToken);

    Optional<RefreshToken> findRefreshToken(String refreshToken);

}
