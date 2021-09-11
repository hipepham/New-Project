/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.security.AccessControlException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The type Jwt token utils.
 */
@Component
@Slf4j
public class JwtTokenUtils implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -2550185165626007488L;
    /**
     * JWT validity time.
     */
    @Value("${app.jwt.validity}")
    private Integer validity;
    /**
     * JWT secret key.
     */
    @Value("${app.jwt.secret}")
    private String secret;

    /**
     * The Secret hash.
     */
    private static String secretHash;

    /**
     * The constant SECRET_HASH_KEY.
     */
    public static final String SECRET_HASH_KEY = "hash";

    /**
     * Gets username from token.
     *
     * @param token the token
     * @return the username from token
     */
//retrieve username from jwt token
    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Gets expiration date from token.
     *
     * @param token the token
     * @return the expiration date from token
     */
//retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Gets claim from token.
     *
     * @param <T>            the type parameter
     * @param token          the token
     * @param claimsResolver the claims resolver
     * @return the claim from token
     */
    public <T> T getClaimFromToken(final String token,
                                   final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        String hashValue = claims.get(SECRET_HASH_KEY).toString();
        if (!hashValue.equals(hashJwtSecretKey())) {
            throw new AccessControlException("THE JWT SECRET KEY HAS CHANGED");
        }
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser()
                .setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token string.
     *
     * @param username the username
     * @return the string
     */
//generate token for user
    public String generateToken(final String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SECRET_HASH_KEY, hashJwtSecretKey());
        return doGenerateToken(claims, username);
    }

    /**
     * Do generate token.
     * @param claims claims
     * @param subject subject
     * @return token
     */
    private String doGenerateToken(final Map<String, Object> claims,
                                   final String subject) {
        final int ms = 1000;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + validity * ms))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(final String token,
                                 final String inputUsername) {
        final String username = getUsernameFromToken(token);
        return (username.equals(inputUsername)
                && !isTokenExpired(token));
    }

    /**
     * Hash jwt secret key string.
     *
     * @return the string
     */
    public String hashJwtSecretKey() {
        if (!StringUtils.isBlank(secretHash)) {
            return secretHash;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(secret.getBytes());
            byte[] digest = md.digest();
            secretHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            return secretHash;
        } catch (NoSuchAlgorithmException e) {
            log.error("HASH SECRET KEY ERROR", e);
        }
        return null;
    }
}
