package starter.letapp.net.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import starter.letapp.net.dao.AppRoleRepository;
import starter.letapp.net.dao.AppUserRepository;
import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.PasswordRequest;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.technicals.UserRequest;
@Service
public class UsersServiceImp implements UsersService {
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<AppUser> getUsers(String keyword,String contry,String city, List<Profile> profiles, String state) {

		keyword = "%" + keyword + "%";
		if (state.equals("*"))
			state = "%%";
		if (city.equals("*"))
			city="%%";
		return this.appUserRepository.getUsersByDetails(keyword,contry, city, profiles, state, "USER");

	}

	@Override
	public AppUser addUser(UserRequest userRequest) {
		if (this.appUserRepository.existsById(userRequest.getUsername()))
			throw new RuntimeException("Utilisateur deja existant");

		AppUser user = new AppUser(userRequest.getUsername(),
				this.bCryptPasswordEncoder.encode(userRequest.getPassword()), userRequest.getName(), null,
				userRequest.getMail(), userRequest.getDescription(), userRequest.getPhone(),
				userRequest.getContry(),userRequest.getCity());
		user.setAppRole(this.appRoleRepository.findByRoleName("USER"));
		return this.appUserRepository.save(user);
	}

	@Override
	public AppUser editUser(String username, UserRequest userRequest) {
		AppUser user = this.getUser(username);
		user.setName(userRequest.getName());
		user.setDescription(userRequest.getDescription());
		user.setImage(userRequest.getImage());
		user.setCity(userRequest.getCity());
		user.setPhone(userRequest.getPassword());
		user.setState(userRequest.getState());
		user.setMail(userRequest.getMail());
		user.setProfiles(userRequest.getProfiles());
		return this.appUserRepository.save(user);
	}

	@Override
	public AppUser getUser(String username) {
		Optional<AppUser> userOpt = this.appUserRepository.findById(username);
		if (!userOpt.isPresent())
			throw new RuntimeException("Utilisateur introuvable");
		return userOpt.get();
	}

	@Override
	public void deleteUser(String username) {
		AppUser user = this.getUser(username);
		this.appUserRepository.delete(user);

	}

	@Override
	public void editPassword(String username, PasswordRequest passwordRequest) {
		AppUser user = this.getUser(username);

		if (!this.bCryptPasswordEncoder.matches(passwordRequest.getOldpassword(), user.getPassword())) {
			throw new RuntimeException("Mot de passe incorrecte");
		}
		user.setPassword(this.bCryptPasswordEncoder.encode(passwordRequest.getPassword()));
		this.appUserRepository.save(user);

	}

	@Override
	public void resetPassword(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public String confirmeResetPassword(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
