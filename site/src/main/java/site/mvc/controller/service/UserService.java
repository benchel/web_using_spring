package site.mvc.controller.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.common.utill.Mailer;
import site.mvc.dto.TempUserInfoDTO;
import site.mvc.dto.UserDTO;
import site.mvc.mapper.TempUserInfoMapper;
import site.mvc.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final TempUserInfoMapper tempUserInfoMapper;
	private final Environment env;
	
	/**
	 * 아이디 중복 확인
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> checkOverlap(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		int count = userMapper.checkOverlap(userDTO);
		if(count > 0) {
			rs.put("msg", "해당 아이디는 중복되는 아이디로 사용하실 수 없습니다.");
			rs.put("result", false);
		} else {
			rs.put("msg", "사용할 수 있는 아이디입니다.");
			rs.put("result", true);
		}
		
		return rs;
	}
	
	/**
	 * 회원 생성
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> createUser(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		int is_success = userMapper.insert(userDTO);
		if(is_success == 0) rs.put("result", false);
		else rs.put("result", true);
		
		return rs;
	}
	
	/**
	 * 인증번호 발송(with 이메일)
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> sendAuthNum(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		Mailer mailer = new Mailer();
		mailer.setAppNum(env.getProperty("mail.pwd"));
		mailer.setSender(env.getProperty("mail.addr"));
		mailer.setReceiver(userDTO.getEmail());
		mailer.setReceiver_name(userDTO.getName());
		mailer.generateCertNum();
		mailer.generateMailContent();
		
		// 임시회원 정보 테이블에 저장(아이디, 이메일, 인증번호)
		TempUserInfoDTO tempinfoDTO = new TempUserInfoDTO(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), mailer.getCertNum());
		tempUserInfoMapper.insert(tempinfoDTO);
		
		Session session = Session.getInstance(mailer.getProperties(), null);
		MimeMessage msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(mailer.getSender(), "관리자"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailer.getReceiver(), userDTO.getName()+"님"));
			msg.setSubject("[게시판] 회원가입 인증 번호입니다.");
			msg.setText(mailer.getCont(), "utf-8", "html");
			msg.setSentDate(new Date());
			Transport.send(msg, mailer.getSender(), mailer.getAppNum());
			rs.put("result", true);
			rs.put("msg", "이메일이 성공적으로 전송되었습니다. 메일을 확인하여 주십시오.");
		} catch (MessagingException e) {
			e.printStackTrace();
			rs.put("result", false);
			rs.put("msg", "이메일 전송에 실패하였습니다.\r 다시 시도하여 주십시오.");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			rs.put("result", false);
			rs.put("msg", "이메일 전송에 실패하였습니다.\r 다시 시도하여 주십시오.");
		}
		return rs;
	}
	
	
	public Map<String, Object> checkCertNum(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		return rs;
	}
}
