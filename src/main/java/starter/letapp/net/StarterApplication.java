package starter.letapp.net;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import starter.letapp.net.dao.AppRoleRepository;
import starter.letapp.net.entities.AppRole;
import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.Interest;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.entities.Project;
import starter.letapp.net.services.InterestsService;
import starter.letapp.net.services.ProfilesService;
import starter.letapp.net.services.ProjectsService;
import starter.letapp.net.services.UsersService;
import starter.letapp.net.technicals.InterestRequest;
import starter.letapp.net.technicals.ProfileRequest;
import starter.letapp.net.technicals.ProjectRequest;
import starter.letapp.net.technicals.UserRequest;

@SpringBootApplication
public class StarterApplication implements CommandLineRunner {
	@Autowired
	ProjectsService projectsService;
	@Autowired
	UsersService usersService;
	@Autowired
	ProfilesService profilesService;
	@Autowired
	AppRoleRepository appRoleRepository;
	@Autowired
	InterestsService interestsService;
	

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder generateBcrypte() {
		return new BCryptPasswordEncoder();
	}

	public void run(String... args) throws Exception {
		this.appRoleRepository.save(new AppRole("USER"));
		
		Profile profile1=this.profilesService.addProfile(new ProfileRequest("developpeur"));
		Profile profile2=this.profilesService.addProfile(new ProfileRequest("investisseur"));
		Profile profile3=this.profilesService.addProfile(new ProfileRequest("commercial"));
		Profile profile4=this.profilesService.addProfile(new ProfileRequest("chauffeur"));
		
		
		AppUser user = this.usersService.addUser(new UserRequest("red5", "123456", "reda EL IDRISSI", "reda@gmail.com",
				"happy happy", "0600000", "Fes", new ArrayList<Project>(), new ArrayList<Profile>()));
		user.getProfiles().add(profile1);
		this.usersService.save(user);
		
		Project project = this.projectsService.addProject(new ProjectRequest("red5", "project 1",
				"orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry'sfg fgdf sdfdsf sdf dsf.",
				"Fes", "informatique", new ArrayList<Profile>(), "active"));

		project.getProfiles().add(profile1);
		//project.getProfiles().add(profile2);
		project.getProfiles().add(profile3);
		//project.getProfiles().add(profile4);
		this.projectsService.save(project);
		
		
		
		Project project2 = this.projectsService.addProject(new ProjectRequest("red5", "project 2",
				"cxvcxvdfv svfdvfdvdfvfdvfdvfvf vfdg fdgfeg ergreger gregreter ertre reg regre greg f.",
				"Casablanca", "commerce", new ArrayList<Profile>(), "active"));

		project2.getProfiles().add(profile2);
		project2.getProfiles().add(profile3);
		project2.getProfiles().add(profile4);
		this.projectsService.save(project2);
		
		this.interestsService.addInterest(new InterestRequest(user.getUsername(),project.getId(),"","message 1","ProjectToUser"));
		this.interestsService.addInterest(new InterestRequest(user.getUsername(),project.getId(),"","reponse 1","UserToProject"));
		this.interestsService.addInterest(new InterestRequest(user.getUsername(),project2.getId(),"","message 2","ProjectToUser"));
		this.interestsService.addInterest(new InterestRequest(user.getUsername(),project2.getId(),"","response 2","UserToProject"));
		// storageService.deleteAll();
		// storageService.init();
		/*
		 * this.appRoleRepository.save(new AppRole("MANAGER"));
		 * 
		 * this.appRoleRepository.save(new AppRole("USER")); AppUser
		 * user=this.usersService.addUser(new
		 * UserRequest("ir.trade88@gmail.com","123456","red company","07098900",
		 * "reda@gmail.com","N24","ACTIVE"));
		 */

	}

}
