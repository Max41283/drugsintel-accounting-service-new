package drugsintel.accounting.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import drugsintel.accounting.security.jwt.JwtTokenUtil;
import drugsintel.accounting.security.jwt.JwtUserDetailsService;
import drugsintel.accounting.security.jwt.UserProfile;
import drugsintel.accounting.security.jwt.dto.JwtRequest;
import drugsintel.accounting.security.jwt.dto.JwtResponse;

@RestController
@CrossOrigin
@RequestMapping("/accounting")
public class AuthenticationController {
	
	AuthenticationManager authenticationManager;
	JwtTokenUtil jwtTokenUtil;
	JwtUserDetailsService userDetailsService;
	
	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
			JwtUserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserProfile userDetails = (UserProfile) userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		final Set<String> routeNames = userDetails.getRouteNames();
		
		return ResponseEntity.ok(new JwtResponse(token, routeNames));
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
