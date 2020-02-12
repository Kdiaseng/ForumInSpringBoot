package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date( hoje.getTime() + Long.parseLong(expiration) );

        return Jwts.builder()
                .setIssuer("API do Forum da Alura")             // qual a aplicação que gerou o token
                .setSubject(logado.getId().toString())          // quem o dono do token
                .setIssuedAt(hoje)                              // data de gereação do token
                .setExpiration(dataExpiracao)                   // tempo de expiração
                .signWith(SignatureAlgorithm.HS256, secret)     // o token deve ser criptografado
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Long getIdUsuario(String token) {
      Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
      return Long.parseLong(claims.getSubject());
    }
}
