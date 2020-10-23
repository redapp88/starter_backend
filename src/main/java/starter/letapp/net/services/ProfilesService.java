package starter.letapp.net.services;

import starter.letapp.net.entities.Profile;
import starter.letapp.net.technicals.ProfileRequest;

public interface ProfilesService {
public Profile addProfile(ProfileRequest profileRequest);
public void deleteProfile(Long id);
public Profile editProfile(Long id,ProfileRequest profileRequest);
public Profile getProfile(Long id);
}
