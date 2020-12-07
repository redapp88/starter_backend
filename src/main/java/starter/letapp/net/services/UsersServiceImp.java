package starter.letapp.net.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import starter.letapp.net.dao.AppRoleRepository;
import starter.letapp.net.dao.AppUserRepository;
import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.technicals.PasswordRequest;
import starter.letapp.net.technicals.UserRequest;
@Service
public class UsersServiceImp implements UsersService {
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private ProfilesService profilesService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<AppUser> getUsers(String keyword,String city, List<Profile> profiles, String state) {
		System.out.println("profiles selectioné"+profiles);
		keyword = "%" + keyword + "%";
		if (state.equals("*"))
			state = "%%";
		if (city.equals("*"))
			city="%%";
		List<AppUser> result = this.appUserRepository.getUsersByDetails(keyword, city, state, "USER");
		List<AppUser> response=new ArrayList<AppUser>();
		result.forEach(
			user->{
				System.out.println("profiles de l 'utlisateur"+user.getProfiles());
			if(ListUtils.intersection(profiles, user.getProfiles()).size()>0) {
				System.out.println("Intersection"+ListUtils.intersection(profiles, user.getProfiles()));
				response.add(user);
			}
		});
		
		return response;

	}

	@Override
	public AppUser addUser(UserRequest userRequest) {
		if (this.appUserRepository.existsById(userRequest.getUsername()))
			throw new RuntimeException("Utilisateur deja existant");

		AppUser user = new AppUser(userRequest.getUsername(),
				this.bCryptPasswordEncoder.encode(userRequest.getPassword()), userRequest.getName(), null,
				userRequest.getMail(), userRequest.getDescription(), userRequest.getPhone(),userRequest.getCity());
		user.setAppRole(this.appRoleRepository.findByRoleName("USER"));
		return this.appUserRepository.save(user);
	}

	@Override
	public AppUser editUser(String username, UserRequest userRequest) {
		AppUser user = this.getUser(username);
		user.setName(userRequest.getName());
		user.setDescription(userRequest.getDescription());
		user.setImage(null);
		user.setCity(userRequest.getCity());
		user.setPhone(userRequest.getPassword());
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

	@Override
	public AppUser save(AppUser user) {
		return this.appUserRepository.save(user);
	}

}
