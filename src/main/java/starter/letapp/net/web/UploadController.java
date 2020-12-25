package starter.letapp.net.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;	
import starter.letapp.net.entities.AppUser;
import starter.letapp.net.services.UsersService;

@RestController
public class UploadController {
@Autowired
private UsersService usersService;

  @PostMapping("/upload")
  public void uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("username") String username ) {
    try {
    AppUser user=this.usersService.getUser(username);
    user.setImage(file.getBytes());
    user.setImageLoaded(true);
    this.usersService.save(user);
    } catch (Exception e) {
    	String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
    	//hrow new RunTimeException( message);
    
      //throw new RunTimeException("error");
      System.out.println(e);
    }
  }
  
	@GetMapping(value = "/files")
	public ResponseEntity downloadFile (@RequestParam String username) throws FileNotFoundException, IOException {
		//System.out.print("get file$$$$$$$");
		 AppUser user=this.usersService.getUser(username);
		 	Resource resource = new ByteArrayResource(user.getImage());;
		 	return ResponseEntity.ok()
		 			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + username+ ".jpg\"")
		 			.body(resource);
	}
  
  
  
}