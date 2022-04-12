package drugsintel.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.CONFLICT)
public class LoginExistsExeption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9123314843921253382L;

	public LoginExistsExeption(String login) {
		super("Account with login " + login + " already exists");
	}

}
