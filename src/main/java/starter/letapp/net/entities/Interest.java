package starter.letapp.net.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NonNull;
@Entity
@Data
public class Interest {
	@Id
	private String id;
	@NonNull
	@ManyToOne
	private AppUser user;
	@NonNull
	@ManyToOne
	private Project project;
	private String message;
	@NonNull
	private Date creationDate;
	private String state;
	private String direction;
	public Interest(@NonNull AppUser user, @NonNull Project project, String message,String direction) {
		super();
		this.id=user.getUsername()+"_"+ project.getId()+"_"+direction;
		this.user = user;
		this.project = project;
		this.message = message;
		this.direction=direction;
		this.creationDate=new Date();
		this.state="new";
	}
	

}
