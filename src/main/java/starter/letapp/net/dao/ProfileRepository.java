package starter.letapp.net.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import starter.letapp.net.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	public List<Profile> findByLabel(String label);

}
