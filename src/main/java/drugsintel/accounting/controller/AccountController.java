package drugsintel.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import drugsintel.accounting.dto.ChangeRoleDto;
import drugsintel.accounting.dto.UserAccountDto;
import drugsintel.accounting.dto.UserRegisterDto;
import drugsintel.accounting.dto.UserUpdateDto;
import drugsintel.accounting.security.jwt.JwtTokenUtil;
import drugsintel.accounting.service.AccountService;

@RestController
@RequestMapping("/accounting")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	
	@PostMapping("/registation")
	public void register(@RequestBody UserRegisterDto userRegisterDto) {
		accountService.addUser(userRegisterDto);
	}
	
//	@PostMapping("/login")
//	public UserTokenDto login(Principal principal) {
//		return accountService.loginUser(principal.getName());
//	}
	
	@GetMapping("/getuser")
	public UserAccountDto getUser(@RequestHeader("Authorization") String token) {
		return accountService.getUser(jwtTokenUtil.getUserId(token.substring(7)));
	}
	
	@DeleteMapping("/delete")
	public UserAccountDto deleteUser(@RequestHeader("Authorization") String token) {
		return accountService.removeUser(jwtTokenUtil.getUserId(token.substring(7)));
	}
	
	@PutMapping("/update")
	public UserAccountDto updateUser(@RequestHeader("Authorization") String token,
			@RequestBody UserUpdateDto userUpdateDto) {
		return accountService.updateUser(jwtTokenUtil.getUserId(token.substring(7)), userUpdateDto);
	}
	
	@PutMapping("/password")
	public void changePassword(@RequestHeader("Authorization") String token, 
			@RequestHeader("X-Password") String newPassword) {
		accountService.changePassword(jwtTokenUtil.getUserId(token.substring(7)), newPassword);
	}
	
	@PutMapping("/admin/role")
	public UserAccountDto changeRole(@RequestHeader("Authorization") String token,
			@RequestBody ChangeRoleDto changeRoleDto) {
		return accountService.changeRole(changeRoleDto);
	}

}
