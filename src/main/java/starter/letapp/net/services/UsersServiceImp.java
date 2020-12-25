package starter.letapp.net.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import starter.letapp.net.dao.AppRoleRepository;
import starter.letapp.net.dao.AppUserRepository;
import starter.letapp.net.dao.ResetTokenRepository;
import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.entities.ResetToken;
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
	@Autowired
	private MailService mailService;
	@Autowired
	private Environment environment;
	@Autowired
	private ResetTokenRepository resetTokenRepository;

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
			throw new RuntimeException("Nom d'utilisateur deja existant");

		AppUser user = new AppUser(userRequest.getUsername(),
				this.bCryptPasswordEncoder.encode(userRequest.getPassword()), userRequest.getName(), null,
				userRequest.getMail(), userRequest.getDescription(), userRequest.getPhone(),userRequest.getCity(),userRequest.getProfiles());
		user.setAppRole(this.appRoleRepository.findByRoleName("USER"));
		user=this.appUserRepository.save(user);
		ResetToken rt = new ResetToken(user, "","VALIDATION");
		this.resetTokenRepository.save(rt);
		this.sendActivationEmail(rt);
		return user;
	}

	@Override
	public AppUser editUser(String username, UserRequest userRequest) {
		AppUser user = this.getUser(username);
		user.setName(userRequest.getName());
		user.setDescription(userRequest.getDescription());
		//user.setImage(userRequest.getImage());
		user.setCity(userRequest.getCity());
		user.setPhone(userRequest.getPhone());
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
		AppUser user = this.getUser(username);
		ResetToken rt = new ResetToken(user, password,"PASSWORD_RESET");
		this.resetTokenRepository.save(rt);
		this.sendPasswordResetEmail(rt);

	}

	@Override
	public String confirmeResetPassword(Long id) {
		Optional<ResetToken> optTk = this.resetTokenRepository.findById(id);
		if (!optTk.isPresent())
			return "Demande introuvale";
		ResetToken tk = optTk.get();
		long diffInMillies = Math.abs(tk.getTokenDate().getTime() - new Date().getTime());
		long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		;
		if (!tk.getType().equals("PASSWORD_RESET")) {
			return "Demmande inccorecte";
		} else {
		if (diff > 48) {
			return "Delais de demande depassé, renouveller votre demande";
		} else {

			AppUser user = tk.getUser();
			user.setPassword(this.bCryptPasswordEncoder.encode(tk.getPassword()));
			this.save(user);
			this.resetTokenRepository.deleteById(tk.getId());
			return "Operation reussie vous pouvez vous authentifier avec votre nouveau mot de passe";
		}
		}
	}
	
	
	@Override
	public String confirmeAccount(Long id) {
		Optional<ResetToken> optTk = this.resetTokenRepository.findById(id);
		if (!optTk.isPresent())
			return "Demande introuvale";
		ResetToken tk = optTk.get();
		
		if (!tk.getType().equals("VALIDATION")) {
			return "Demmande inccorecte";
		} else {

			AppUser user = tk.getUser();
			user.setState("ACTIVE");
			this.save(user);
			this.resetTokenRepository.deleteById(tk.getId());
			return "Operation reussie vous pouvez vous acceder a votre compte Starter";
		}
	}

	@Override
	public AppUser save(AppUser user) {
		return this.appUserRepository.save(user);
	}
	
	private void sendActivationEmail(ResetToken rt) {
try {
	String link=environment.getProperty("my.backend")+"/activateAccount?tokenId="+rt.getId();
	String body="<p><strong>Bonjour "+rt.getUser().getName()+", pour activer votre compte Starter&nbsp;<a href='"+link+"'>cliquez ici</a></strong></p>"+
	"<p>ou copier ce lien dans votre naviguateur</p>"+
	"<p>"+link+"</p>"+
	"<p>&nbsp;</p>"+
	"<p><span style='color: #ff0000;'>Nb: si vous vous etes jamais enregistrer à Starter veuillez ignorer ce message</span></p>";
	
	this.mailService.sendEmail(body, "Activation de votre compte Starter", rt.getUser().getMail(), "LetApp");
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
	
	
	
	
	private void sendPasswordResetEmail(ResetToken rt) {
		try {
			String link=environment.getProperty("my.backend")+"/confirmeResetPassword?tokenId="+rt.getId();
			String body="<p><strong>Bonjour, pour confirmer la reinitialisation du mot de passe de votre compte Starter&nbsp;<a href='"+link+"'>cliquez ici</a></strong></p>"+
			"<p>ou copier ce lien dans votre naviguateur</p>"+
			"<p>"+link+"</p>"+
			"<p>&nbsp;</p>"+
			"<p><span style='color: #ff0000;'>Nb: si vous n'avez pas demander de&nbsp;reinitialisation de votre mot de passe veuillez ignorer ce message</span></p>";
			
			this.mailService.sendEmail(body, "Reinitialisation de votre mot de passe", rt.getUser().getMail(), "LetApp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}

}
