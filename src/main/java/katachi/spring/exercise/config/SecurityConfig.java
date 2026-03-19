package katachi.spring.exercise.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // BCryptPasswordEncoderをBeanとして登録
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers(mvc.pattern("/login")).permitAll()
				.requestMatchers(mvc.pattern("/")).permitAll()
				.requestMatchers(mvc.pattern("/topPage/**")).permitAll()
				.requestMatchers(mvc.pattern("/teamNews/**")).permitAll()
				.requestMatchers(mvc.pattern("/teamNewsDetail")).permitAll()
				.requestMatchers(mvc.pattern("/items/**")).permitAll()
				.requestMatchers(mvc.pattern("/itemsDetail/**")).permitAll()
				.requestMatchers(mvc.pattern("/register")).permitAll()
				.requestMatchers(mvc.pattern("/basket")).permitAll()
				.requestMatchers(mvc.pattern("/addToCarts")).permitAll()
				.anyRequest().authenticated());

		//ログイン
		http.formLogin(login -> login
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureUrl("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.permitAll()

		//ログアウト
		).logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout"));
		
		return http.build();
	}
}


	

