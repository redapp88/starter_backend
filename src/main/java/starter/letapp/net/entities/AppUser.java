package starter.letapp.net.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
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
private String contry;
@NonNull
private String city;
@NonNull
private Date subsDate;
@NonNull
private String state;
@OneToMany(mappedBy="owner")
private List<Project> projects=new ArrayList<Project>();
@ManyToMany
private List<Profile> profiles=new ArrayList<Profile>();
@OneToMany
private List<Interest> interest=new ArrayList<Interest>();
@JsonIgnore
@ManyToOne
private AppRole appRole;
public AppUser(String username, @NonNull String password, @NonNull String name, @NonNull byte[] image, String mail,
		@NonNull String description, String phone, @NonNull String contry,@NonNull String city) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.image = image;
	this.mail = mail;
	this.description = description;
	this.phone = phone;
	this.contry=contry;
	this.city=city;
	this.subsDate=new Date();
	this.state="active";
}






}
