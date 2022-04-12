package drugsintel.accounting.security.jwt.dto;

import java.io.Serializable;

public class JwtRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4429282787536220035L;
	private String userName;
	private String password;
	
	public JwtRequest()
	{
		
	}

	public JwtRequest(String username, String password) {
		this.setUsername(userName);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
