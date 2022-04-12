package drugsintel.accounting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ChangeRoleDto {
	@JsonProperty("username")
	String userName;
	@JsonProperty("role")
	String roleName;
	@JsonProperty("validity")
	Integer validityInDays;
}
