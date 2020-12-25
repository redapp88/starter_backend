package starter.letapp.net.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import starter.letapp.net.services.UsersService;


@Controller
public class ActivationsController {
	@Autowired
	private UsersService usersService;
	
	@GetMapping("/activateAccount")
	public String confirmeAccount(Model model,@RequestParam Long tokenId) {
		
		String resp=this.usersService.confirmeAccount(tokenId);
		model.addAttribute("response",resp) ;
		return "accountActivationTemplate";
		
	}

	@GetMapping("/confirmeResetPassword")
	public String confirmeResetPassword(Model model,@RequestParam Long tokenId) {
		
		String resp=this.usersService.confirmeResetPassword(tokenId);
		model.addAttribute("response",resp) ;
		return "passwordResetTemplate.html";
		
	}

}
