package drugsintel.accounting.security.jwt;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import drugsintel.accounting.model.Route;

public class UserProfile extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7397351558952418836L;
	private Long userId;
	private Set<String> routeNames;

	public UserProfile(String username, String password,
			Collection<? extends GrantedAuthority> authorities, Long userId, Set<String> routeNames) {
		super(username, password, authorities);
		this.userId = userId;
		this.routeNames = routeNames;
	}

	public Long getUserId() {
		return userId;
	}
	
	public Set<String> getRouteNames() {
		return routeNames;
	}
	
}
