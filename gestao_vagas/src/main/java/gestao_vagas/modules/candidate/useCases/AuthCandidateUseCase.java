package gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import gestao_vagas.exceptions.UserFoundException;
import gestao_vagas.modules.candidate.Repository.CandidateRepository;
import gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException{
        var candidate = this.candidateRepository
            .findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Username/password incorrect");
            });

        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                    .withIssuer("javagas")
                    .withSubject(candidate.getId().toString())
                    .withClaim("roles", Arrays.asList("candidate"))
                    .withExpiresAt(expiresIn)
                    .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                                        .access_token(token)
                                        .expires_in(expiresIn.toEpochMilli())
                                        .build();
        return  authCandidateResponse;
    }
}
