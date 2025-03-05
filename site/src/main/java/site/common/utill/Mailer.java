package site.common.utill;

import java.util.Properties;

public class Mailer {
	
	private String google_app_num;
	private String sender;
	private String receiver;
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
	
}
