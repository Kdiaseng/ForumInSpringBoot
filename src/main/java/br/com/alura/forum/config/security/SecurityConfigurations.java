package br.com.alura.forum.config.security;

import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // habilida modulo de seguranca
@Configuration    // classe que tem configuração deve ter essa notacao
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private  TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Configuracoes de autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

     @Override
     @Bean
     protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
     }


    //Configuracao de Autorizacao(quais as url's podem ser acessadas)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/h2-console/**").permitAll()
              .antMatchers(HttpMethod.GET,"/topicos").permitAll()
              .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
              .antMatchers(HttpMethod.POST, "/auth").permitAll()
              .anyRequest().authenticated()
              .and().csrf().disable()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // NÃO É PARA CRIAR SESSAO
              .and().addFilterBefore(new AutenticationToTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class); //aplicando filter

    }

    //Configuracao de recursos staticos(js, css, imagens, etc)
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

//        public static void main(String[] args) {
//            System.out.println(new BCryptPasswordEncoder().encode("123456"));
//    }
}
