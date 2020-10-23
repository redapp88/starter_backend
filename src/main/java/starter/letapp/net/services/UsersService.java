package starter.letapp.net.services;

import java.util.List;

import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.PasswordRequest;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.technicals.UserRequest;


public interface UsersService {
	public AppUser addUser(UserRequest userRequest);
	public AppUser editUser(String username,UserRequest userRequest);
	public AppUser getUser(String username);
	public void deleteUser(String username);
	public void editPassword(String username, PasswordRequest passwordRequest);
	public void resetPassword(String username,String password);
	public String confirmeResetPassword(Long id);
	public List<AppUser> getUsers(String keyword,String contry,String city,
			List<Profile> profiles, String state);

}

