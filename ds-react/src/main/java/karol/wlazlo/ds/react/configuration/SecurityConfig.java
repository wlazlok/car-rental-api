package karol.wlazlo.ds.react.configuration;

import karol.wlazlo.ds.react.configuration.security.jwt.AppUserService;
import karol.wlazlo.ds.react.configuration.security.jwt.JwtConfig;
import karol.wlazlo.ds.react.configuration.security.jwt.JwtTokenVerifier;
import karol.wlazlo.ds.react.configuration.security.jwt.JwtUsernameAndPasswordAuthFilter;
import karol.wlazlo.model.Security.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public SecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService, SecretKey secretKey, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.appUserService = appUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(getJwtUsernameAndPasswordAuthFilter())
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthFilter.class)
                .authorizeRequests()
//                .antMatchers("/*", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/react/products").hasAnyRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated();
//                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public JwtUsernameAndPasswordAuthFilter getJwtUsernameAndPasswordAuthFilter() throws Exception {
        final JwtUsernameAndPasswordAuthFilter filter = new JwtUsernameAndPasswordAuthFilter(authenticationManager(), jwtConfig, secretKey);
        filter.setFilterProcessesUrl("/api/react/user/login");

        return filter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);

        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
