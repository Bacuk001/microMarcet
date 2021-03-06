package by.intexsoft.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import by.intexsoft.entity.Role;
import by.intexsoft.entity.User;

/**
 * Class object will be registered in {@link SecurityContextHolder}. The user on
 * the basis of this class will be granted access to the components of the
 * application.
 */
public class UserAuthentication implements Authentication {
	private static final long serialVersionUID = -6220452839777795632L;
	private User user;
	private boolean authenticated = true;

	public UserAuthentication(User user) {
		this.user = user;
	}

	@Override
	public String getName() {
		return user.name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (int index = 0; index < user.roles.size(); index++) {
			authorities = getRolesArray(user.roles);
		}
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return user.password;
	}

	@Override
	public Object getDetails() {
		return this.user;
	}

	@Override
	public Object getPrincipal() {
		return user.name;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;

	}

	/**
	 * The method {@link List} the sheet with array values.
	 */
	public List<GrantedAuthority> getRolesArray(List<Role> roles) {
		String[] rolesArray = new String[roles.size()];
		for (int index = 0; index < roles.size(); index++) {
			rolesArray[index] = roles.get(index).name;
		}
		return AuthorityUtils.createAuthorityList(rolesArray);
	}
}
