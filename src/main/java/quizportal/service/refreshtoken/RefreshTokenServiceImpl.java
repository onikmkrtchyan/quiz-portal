package quizportal.service.refreshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.data.model.RefreshToken;
import quizportal.data.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void save(String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(token.substring(token.lastIndexOf(".")));
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void delete(String token) {
        refreshTokenRepository.deleteByRefreshToken(token.substring(token.lastIndexOf(".")));
    }

    @Transactional(readOnly = true)
    public boolean exists(String token) {
        return refreshTokenRepository.existsByRefreshToken(token.substring(token.lastIndexOf(".")));
    }
}
