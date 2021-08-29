package uz.pdp.chat.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;


import java.util.Date;
@Component
public class JwtProvider {
    private static final long expireTime = 1000 * 60 * 60 * 24;
    private static final String secretKey="SenBuniBilolmiysan";

    public String generateToken(String username) {
        Date expireDate=new Date(System.currentTimeMillis()+expireTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    public String getEmailFromToken(String token){
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (Exception e){
            return null;
        }
    }
}
