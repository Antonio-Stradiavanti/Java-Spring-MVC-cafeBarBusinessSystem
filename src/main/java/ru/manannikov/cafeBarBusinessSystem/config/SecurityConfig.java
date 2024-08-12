package ru.manannikov.cafeBarBusinessSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.manannikov.cafeBarBusinessSystem.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    /**
     * @return бин для шифрования паролей перед их сохранением в БД.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Бин, создает и настраивает цепочку фильтров безопасности.
     * @param http порождающий бин, соотв. паттерну builder, предназначенный для пошаговой настройки цепочки фильтров SecurityFilterChain;
     * @return настроенную цепочку фильтров -- экземпляр SecurityFilterChain;
     * @throws Exception -- общее исключение.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Защиту от csrf -> cross-site resource forgery = межсайтовой подделки запросов на этапе разработки и тестирования можно отключить;
            .csrf(AbstractHttpConfigurer::disable)
            // Я не делаю запросы к другим доменам
            .cors(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests((request) -> request
                // Разрешаем любым пользователям доступ к endpoint /login
                .requestMatchers("/styles/**", "/scripts/**", "/images/**").permitAll()
                .requestMatchers("/login").permitAll()
                // Выбросит IllegalArgumentException если у пользователя нет ни одной из перечисленных ролей.
                .anyRequest().authenticated()
            )

            .formLogin((form) -> form
                .loginPage("/login")
                // На этот URL будут отправляться данные с формы логина
                .loginProcessingUrl("/perform-login")
                // Куда перенаправить пользователя в случае успеха
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                // Идентификатор соответствующего текстового поля в форме, он же ключ http параметра.
                .usernameParameter("username")
                .passwordParameter("password")
                // Гарантируем, что endpoint /login доступен любому пользователю
                .permitAll()
            )

            .logout((logout) -> logout
                // Вообще не авторизированному пользователю доступен только один endpoint -> login
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
            )
        ;

        return http.build();
    }
    // Конфигурация аутентификации
    // Передаем реализацию UserDetailsService
    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);

        return daoAuthenticationProvider;
    }
}
