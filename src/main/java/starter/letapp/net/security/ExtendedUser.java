package starter.letapp.net.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import starter.letapp.net.entities.Profile;

@Data
public class ExtendedUser extends User {
	private String name;
	private boolean imageLoaded;
	private String city;
	private List<Profile> profiles;

	public ExtendedUser(String username, String password, String name,  boolean imageLoaded, String city,List<Profile> profiles,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.name = name;
		this.imageLoaded = imageLoaded;
		this.city = city;
		this.profiles=profiles;

	}

}
