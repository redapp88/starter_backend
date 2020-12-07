package starter.letapp.net.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.Interest;
import starter.letapp.net.entities.Project;
import starter.letapp.net.technicals.InterestRequest;
import starter.letapp.net.dao.InterestRepository;
@Service
public class InterestsServiceImp implements InterestsService {
@Autowired
private InterestRepository InterestRepository;
@Autowired
private UsersService usersService;
@Autowired
private ProjectsService projectsService;
	@Override
	public Interest addInterest(InterestRequest interestRequest) {
		AppUser user=this.usersService.getUser(interestRequest.getUsername());
		Project project=this.projectsService.getProject(interestRequest.getId());
		/*
		 * if(!CollectionUtils.intersection(project.getProfiles(),
		 * user.getProfiles()).isEmpty()) throw new
		 * RuntimeException("Ce projet ne cherches pas les competances mentionés dans votre compte"
		 * );
		 */
		
		if(this.InterestRepository.existsById(interestRequest.getUsername()+"_"+interestRequest.getId()+"_"+interestRequest.getDirection()))
		throw new RuntimeException("vous avez deja ajouté ce projet à a vos interet");
		Interest interest=new Interest(user, project, interestRequest.getMessage(), interestRequest.getDirection());
		return this.InterestRepository.save(interest);
		/*
		 * if(interestRequest.getDirection().equals("UserToProject"))
		 * user.getInterest().add(interest); else
		 * if((interestRequest.getDirection().equals("ProjectToUser")))
		 * project.getInterests().add(interest); return interest;
		 */
	}

	@Override
	public void deleteInterest(String id) {
		Interest interest=this.getInterest(id);
		this.InterestRepository.delete(interest);
		
	}

	@Override
	public Interest editInterest(String id, InterestRequest interestRequest) {
		Interest interest=this.getInterest(id);
		interest.setMessage(interestRequest.getMessage());
		interest.setState(interestRequest.getState());
		return this.InterestRepository.save(interest);
	}

	@Override
	public Interest getInterest(String id) {
Optional<Interest> interetOpt = this.InterestRepository.findById(id);
if(!interetOpt.isPresent())
	throw new RuntimeException("Introuvable");
else return interetOpt.get();
	}

	@Override
	public List<Interest> getInterests(String username, String id) {
		if(username.equals("*"))
			username="%%";
		if(id.equals("*"))
			id="%%";
		return this.InterestRepository.getByUserAndProject(username,id);
	}


}
