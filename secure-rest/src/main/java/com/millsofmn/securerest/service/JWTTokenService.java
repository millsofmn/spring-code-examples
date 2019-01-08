package com.millsofmn.securerest.service;

import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JWTTokenService implements Clock, TokenService {

    private static final String DOT = ".";
    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();

    private String issuer;
    private int expirationSec;
    private int clockSkewSec;
    private String secret;

    public JWTTokenService(@Value("${jwt.issuer:octoperf}") final String issuer,
                           @Value("${jwt.expiration-sec:86400}") final int expirationSec,
                           @Value("${jwt.clock-skew-sec:300}") final int clockSkewSec,
                           @Value("${jwt.secret:secret}") final String secret) {
        this.issuer = issuer;
        this.expirationSec = expirationSec;
        this.clockSkewSec = clockSkewSec;
        this.secret = secret;
    }

    @Override
    public String pernament(Map<String, String> attributes) {
        return newToken(attributes, 0);
    }

    @Override
    public String expiring(Map<String, String> attributes) {
        return newToken(attributes, expirationSec);
    }

    private String newToken(Map<String, String> attributes, int expiresInSec){
        Instant now = Instant.now();
        Claims claims = Jwts
                .claims()
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now));

        if(expiresInSec > 0){
            Instant expiresAt = now.plusSeconds(expiresInSec);
            claims.setExpiration(Date.from(expiresAt));
        }
        claims.putAll(attributes);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compressWith(COMPRESSION_CODEC)
                .compact();
    }

    @Override
    public Map<String, String> untrusted(String token) {
        JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(clockSkewSec);

        String withoutSignature = substringBeforeLast(token, DOT) + DOT;

        return parseClaims(()->parser.parseClaimsJws(withoutSignature).getBody());
    }

    private Map<String, String> parseClaims(Supplier<Claims> toClaims) {
        try {
            Claims claims = toClaims.get();
            ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

            for(Map.Entry<String, Object> e: claims.entrySet()){
                builder.put(e.getKey(), String.valueOf(e.getValue()));
            }
            return builder.build();
        } catch (IllegalArgumentException | JwtException e){
            return ImmutableMap.of();
        }
    }

    @Override
    public Map<String, String> verify(String token) {
        JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(clockSkewSec)
                .setSigningKey(secret);

        return parseClaims(()->parser.parseClaimsJws(token).getBody());
    }

    @Override
    public Date now() {
        return Date.from(Instant.now());
    }
}
