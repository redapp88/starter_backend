package starter.letapp.net.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class ExtendedUser extends User {
	private String name;
	private byte[] image;
	private String contry;
	private String city;

	public ExtendedUser(String username, String password, String name, byte[] image, String contry, String city,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.name = name;
		this.image = image;
		this.contry = contry;
		this.city = city;

	}

}
