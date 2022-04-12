package drugsintel.accounting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class UserRegisterDto {
	@JsonProperty("username")
	String userName;
	String email;
	String password;
}
