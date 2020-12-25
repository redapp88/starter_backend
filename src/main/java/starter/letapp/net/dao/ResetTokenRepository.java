package starter.letapp.net.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import starter.letapp.net.entities.ResetToken;
public interface ResetTokenRepository extends JpaRepository<ResetToken,Long > {

}
