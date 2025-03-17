package site.common.utill;

import java.util.Properties;
import java.util.Random;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


public class Mailer {
	
	private String google_app_num; 
	private String sender; // 보내는 이(주소)
	private String receiver_name; // 받는 이(이름)
	private String receiver; // 받는 이(주소)
	private String auth_num;
	
	private String cont;
	private Properties props;
	
	public Mailer() {
		this.props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");	
		
	}

	public void setAppNum(String google_app_num) {
		this.google_app_num = google_app_num;
	}
	
	public String getAppNum() {
		return google_app_num;
	}
	
	public void setProperties(String host, String port, String isAuth, String isTtls) {
		this.props.put("mail.smtp.host", host);
		this.props.put("mail.smtp.port", port);
		this.props.put("mail.smtp.auth", isAuth);
		this.props.put("mail.smtp.starttls.enable", isTtls);
	}
	
	public Properties getProperties() {
		return this.props;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getSender() {
		return this.sender;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getReceiver() {
		return this.receiver;
	}
	
	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	// 인증번호 생성
	public void generateAuthNum() {
		this.auth_num = "";
		Random random = new Random();
		random.setSeed(0);
		
		for(int i = 0; i < 6; i++) {
			int num = random.nextInt(100);
			this.auth_num += Integer.toString(num);
		}
	}
	
	public String getAuthNum() {
		return this.auth_num;
	}
	
	// 주어진 정보를 토대로 발송할 메일의 컨텐츠를 생성한다.
	public void generateMailContent() {
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setPrefix("templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		
		Context context = new Context();
		context.setVariable("name", receiver_name);
		context.setVariable("auth", auth_num);
		
		TemplateEngine tpeg = new TemplateEngine();
		tpeg.setTemplateResolver(resolver);
		this.cont = tpeg.process("site/email/auth", context);
	}
}
