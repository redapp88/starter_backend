package starter.letapp.net.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Serializable {
@Id
private String username;
@NonNull
private String password;
@NonNull
private String name;
@NonNull
@Lob
private byte[] image;
private String mail;
@NonNull
private String description;
private String phone;
@NonNull
private String city;
@NonNull
private Date subsDate;
@NonNull
private String state;
@OneToMany(mappedBy="owner")
@JsonIgnore
private List<Project> projects;
@ManyToMany(fetch=FetchType.EAGER)
private List<Profile> profiles;
@OneToMany
private List<Interest> interest;
@JsonIgnore
@ManyToOne
private AppRole appRole;
public AppUser(String username, @NonNull String password, @NonNull String name, byte[] image, String mail,
		@NonNull String description, String phone,@NonNull String city) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.image = image;
	this.mail = mail;
	this.description = description;
	this.phone = phone;
	this.city=city;
	this.subsDate=new Date();
	this.state="active";
	this.profiles= new ArrayList<Profile>();
	this.projects=new ArrayList<Project>();
	this.interest=new ArrayList<Interest>();
	
	
}






}
