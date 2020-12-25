package starter.letapp.net.services;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
	public void sendEmail(String body,String title,String to,String from) throws Exception;
}
