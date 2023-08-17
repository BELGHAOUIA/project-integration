package springboot.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springboot.Entity.Enum.Role;
import springboot.auth.MyUserDetailsService;
import springboot.filter.CorsFilter;
import springboot.filter.JwtFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	//@Autowired
	//private CorsFilter corsFilter;

	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	UserDetailsService myUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
				cors().and().csrf().disable()
				.authorizeRequests()
				.antMatchers("/",
						"/user/register/etudiant",
						"/user/authenticate",
						"/user/emailVerification/**").permitAll()
				.antMatchers("/salle/start").authenticated()
				.antMatchers("/technicien/**").hasAuthority(Role.TECHNICIEN.toString())
				.antMatchers("/prof/**").hasAuthority(Role.PROF.toString())
				.antMatchers("/etudiant/**").hasAuthority(Role.ETUDIANT.toString())
				.antMatchers("/techProf/**").hasAnyAuthority(Role.TECHNICIEN.toString(),Role.PROF.toString())

				// Any other URLs which are not configured in above antMatchers
				// generally declared aunthenticated() in real time
				.anyRequest().authenticated()

				//Filter chain
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				//Login Form Details
				.and()
				.formLogin()
				.defaultSuccessUrl("/salle/start", true)

				//Logout Form Details
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				//Exception Details
				.and()
				.exceptionHandling()
				.accessDeniedPage("/accessDenied");
				//Authentication
				//.and()
				//.authenticationProvider(configure());
		//http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/*
	@Autowired
	private DataSource dataSource;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList(authenticationProvider()));
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(myUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//declares which Page(URL) will have What access type
		http.csrf().disable().
				authorizeRequests()
				.antMatchers("/authenticate").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/home").authenticated()
				.antMatchers("/technicien/**").hasAuthority(Role.TECHNICIEN.toString())
				.antMatchers("/prof/**").hasAuthority(Role.PROF.toString())
				.antMatchers("/etudiant/**").hasAuthority(Role.ETUDIANT.toString())
				.antMatchers("/techProf/**").hasAnyAuthority(Role.TECHNICIEN.toString(),Role.PROF.toString())

				// Any other URLs which are not configured in above antMatchers
				// generally declared aunthenticated() in real time
				.anyRequest().authenticated()

				//Login Form Details
				.and()
				.formLogin()
				.defaultSuccessUrl("/home", true)

				//Logout Form Details
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				//Exception Details
				.and()
				.exceptionHandling()
				.accessDeniedPage("/accessDenied")

				//Authentication
				.and()
				.authenticationProvider(authenticationProvider());

		return http.build();

	}*/
}