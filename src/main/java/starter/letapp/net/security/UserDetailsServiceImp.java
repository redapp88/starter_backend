package starter.letapp.net.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import starter.letapp.net.entities.AppUser;
import starter.letapp.net.services.UsersService;




@Service
@Transactional

public class UserDetailsServiceImp implements UserDetailsService {
@Autowired
private UsersService usersService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser= this.usersService.getUser(username);
		if (appUser== null) throw new UsernameNotFoundException(username);
		if(appUser.getState().equals("DELETED"))  throw new RuntimeException("Compte supprimé");
		if(appUser.getState().equals("DISABLED")) throw new RuntimeException("compte desactivé");
		Collection<GrantedAuthority> authorities=new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority(appUser.getAppRole().getRoleName()));
	    ExtendedUser user=new ExtendedUser(appUser.getUsername(),
	    		appUser.getPassword(),
	    		appUser.getName(),
	    		appUser.getImage(),
	    		appUser.getContry(),
	    		appUser.getCity(),
	    		authorities);
		return user;
	}



}
