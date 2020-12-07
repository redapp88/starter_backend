package starter.letapp.net.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import starter.letapp.net.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	@Query("select p from Profile p where p.label like :label")
	public List<Profile> findByLabel(
			@Param(value="label")String label);

}
