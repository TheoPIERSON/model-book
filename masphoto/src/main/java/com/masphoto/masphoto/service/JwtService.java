package com.masphoto.masphoto.service;

import com.masphoto.masphoto.entities.User;
import com.masphoto.masphoto.entities.Jwt;
import com.masphoto.masphoto.repositories.JwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional

@AllArgsConstructor
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    private final String ENCRYPTION_KEY = "3LtjalAUrv5fOfeTGrAVyM/d8fX35gAXRakA9z3dz2Oell791r/eYYzkJr7/OGW1";
    private UserService userService;
    private JwtRepository jwtRepository;

    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValueAndDesactiveAndExpire(value, false, false)
                .orElseThrow(()-> new RuntimeException("Le token n'est pas connu"));
    }
    public Map<String, String> generate(String username){
        User user = (User) this.userService.loadUserByUsername(username);
        this.disableTokens(user);
        final Map<String, String> jwtMap = this.generateJwt(user);

        final Jwt jwt = Jwt
                .builder()
                .value(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .user(user)
                .build();
    this.jwtRepository.save(jwt);
        return jwtMap;
    }
    private void disableTokens(User user) {
        final List<Jwt> jwtList = this.jwtRepository.findUser(user.getEmail()).peek(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
    }

    public String readUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate =  this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function){
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 60 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "role", user.getRole() // Inclure le rôle de l'utilisateur
        );
        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .addClaims(claims) // Utiliser addClaims() pour inclure les revendications
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }
    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public void disconnectUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findUserValidToken(user.getEmail(), false, false)
                .orElseThrow(()-> new RuntimeException("Le token est invalide"));
        jwt.setExpire(true);
        jwt.setDesactive(true);
        this.jwtRepository.save(jwt);
    }

    public Map<String, String> generatePasswordResetToken(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 15 * 60 * 1000; // Expire dans 15 minutes

        final Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "type", "PASSWORD_RESET" // Identifie l'usage du jeton
        );

        final String resetToken = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .addClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of(BEARER, resetToken);
    }
    public boolean isResetTokenValid(String token) {
        try {
            String type = this.getClaim(token, claims -> claims.get("type", String.class));
            return "PASSWORD_RESET".equals(type) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }


    @Scheduled(cron = "@daily")
    //@Scheduled(cron = "0 */1 * * * *")
    public void removeUselessJwt(){
    this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
    }
}
