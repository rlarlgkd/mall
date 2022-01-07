package com.study.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;
	
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				/*
				 * .antMatchers("/notice").permitAll() .antMatchers("/csChatting").permitAll()
				 * .antMatchers("/notice").permitAll() .antMatchers("/mallInfo").permitAll()
				 */
				.antMatchers("/css/**", "/js/**","/img/**").permitAll()
				.antMatchers("/guest/**").permitAll()
				.antMatchers("/buyer/**").hasAnyRole("BUYER","ADMIN")
				.antMatchers("/seller/**").hasAnyRole("SELLER","ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/security/**").permitAll()
				.anyRequest().permitAll();
			
		
		http.formLogin()
				.loginPage("/guest/login")
				.loginProcessingUrl("/loginProcess")
//				.failureUrl("/guest/main")
//				.defaultSuccessUrl("/admin/main")
				.successForwardUrl("/login_success_handler")
				.failureHandler(authenticationFailureHandler)
				.usernameParameter("mid")
				.passwordParameter("mpw")
				.permitAll();
				
		http.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll();
		
		
		//다른 도매인으로 넘어가지 않게 해주는걸 풀어야 테스트 가능
		http.csrf().disable();
	}
	
//	@Autowired
//	public void configureGlocal(AuthenticationManagerBuilder auth)throws Exception{
//		auth.inMemoryAuthentication()
//			.withUser("user").password(passwordEncoder().encode("1234")).roles("USER")
//			.and()
//			.withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
//		//ROLE_ADMIN 에서 ROLE_는 자동으로 붙는다
//	}
//	//빠른 테스트를 위해 등록이 간단한  inMemory 방식의 인증 사용자를 등록했습니다.

	@Autowired
	private DataSource dataSource;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select mid as userName, mpw as password, enabled"
								+" from member where mid = ?")
			.authoritiesByUsernameQuery("select mid as userName, usertype as authority"
								+" from member where mid = ? ")
			.passwordEncoder(new BCryptPasswordEncoder());
		
		
		/*
		 * String encoded=new BCryptPasswordEncoder().encode("1234");
		 * System.out.println(encoded);
		 */
		 

	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
