package starter.letapp.net.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import starter.letapp.net.entities.AppUser;
import starter.letapp.net.entities.Profile;

/**
 * @author Reda EL IDRISSI
 *
 */
public interface AppUserRepository extends JpaRepository<AppUser, String> {
	@Query("select u from AppUser u where (u.name like :keyword or u.username like :keyword or u.mail like :keyword or u.description like :keyword)  and u.appRole.roleName=:role and u.state like :state and u.contry like :contry and u.city like :city and u.profiles in :profiles")
	public List<AppUser> getUsersByDetails(
			@Param(value="keyword")String keyword,
			@Param(value="contry")String contry,
			@Param(value="city")String city,
			@Param(value="profiles")List<Profile> profiles,
			@Param(value="state")String state,
			@Param(value="role")String role);

}
