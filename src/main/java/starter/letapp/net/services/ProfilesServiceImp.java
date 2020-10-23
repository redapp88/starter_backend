package starter.letapp.net.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import starter.letapp.net.dao.ProfileRepository;
import starter.letapp.net.entities.Profile;
import starter.letapp.net.technicals.ProfileRequest;
@Service
public class ProfilesServiceImp implements ProfilesService {
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile addProfile(ProfileRequest profileRequest) {
		if (!this.profileRepository.findByLabel(profileRequest.getLabel()).isEmpty())
			throw new RuntimeException("Un profile avec le meme nom existe Deja");

		return this.profileRepository.save(new Profile(profileRequest.getLabel()));

	}

	@Override
	public void deleteProfile(Long id) {
		Profile profile=this.getProfile(id);
		this.profileRepository.delete(profile);

	}

	@Override
	public Profile editProfile(Long id, ProfileRequest profileRequest) {
	Profile profile=this.getProfile(id);
	profile.setLabel(profileRequest.getLabel());
	return this.profileRepository.save(profile);
	}

	@Override
	public Profile getProfile(Long id) {
	Optional<Profile> profileOpt = this.profileRepository.findById(id);
	if(!profileOpt.isPresent())
		throw new RuntimeException("Profile Introuvable");
	return profileOpt.get();
	}

}
