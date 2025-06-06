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
import site.mvc.vo.TempUserInfoVO;

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

		TempUserInfoDTO tempUserInfoDTO = new TempUserInfoDTO(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), mailer.getCertNum());
		// 인증을 재시도하는 경우
		// 기존의 임시정보를 삭제한다.
		tempUserInfoMapper.deleteByIdAndEmail(tempUserInfoDTO);
		
		// 임시회원 정보 테이블에 저장(아이디, 이메일, 인증번호)
		tempUserInfoMapper.insert(tempUserInfoDTO);
		
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
			rs.put("msg", "이메일 전송에 실패하였습니다.\n다시 시도하여 주십시오.");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			rs.put("result", false);
			rs.put("msg", "이메일 전송에 실패하였습니다.\n다시 시도하여 주십시오.");
		}
		return rs;
	}
	
	/**
	 * 인증번호 확인
	 * @param tempUserDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> checkCertNum(TempUserInfoDTO tempUserDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		// 임시회원 정보를 불러온다.
		TempUserInfoVO tempUserVO = tempUserInfoMapper.select(tempUserDTO);
		
		// 이용자가 입력한 인증번호와 불러온 정보의 인증번호를 비교한다.
		String inputNum = tempUserDTO.getCertNum();
		String loadedNum = tempUserVO.getCertNum();
		
		if(inputNum.equals(loadedNum)) {
			// 동일한 경우 임시회원 정보 테이블의 인증여부를 업데이트한다. 결과값을 true로 설정
			tempUserDTO.setIsCert(1);
			tempUserInfoMapper.updateColThatIsCert(tempUserDTO);
			rs.put("result", true);
			rs.put("msg", "인증이 성공적으로 완료되었습니다.\n회원가입 절차를 완료하여 주십시오.");
		} else {
			// 동일하지 않은 경우 결과값을 false으로 설정
			rs.put("result", false);
			rs.put("msg", "인증에 실패하였습니다.\n인증번호를 확인하여 주십시오.");
		}
		return rs;
	}
	
	/**
	 * 인증여부 확인
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkThatWhetherCert(TempUserInfoDTO tempUserDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		// 임시회원 정보를 불러온다.
		TempUserInfoVO tempUserVo = tempUserInfoMapper.select(tempUserDTO);		
		
		// 인증여부 확인 
		// 1. 인증시도를 아예하지 않은 경우
		if(tempUserVo == null) {
			rs.put("result", false);
			rs.put("msg", "회원가입을 위해서는 이메일을 통하여 본인 확인이 필요합니다.");
		// 2. 인증번호를 발급만 받고 확인을 하지 않은 경우	
		} else {
			int isCert = tempUserVo.getIsCert();
			// 인증 여부를 확인한다.
			if(isCert == 1) {
				// 그 값이 1(yes)이면 true를 반환
				rs.put("result", true);
				rs.put("msg", "");
			} else {
				// 그 값이 0(no)이면 false를 반환한다.
				rs.put("result", false);
				rs.put("msg", "본인 확인을 완료하여 주십시오.");
			}
		}
		return rs;		
	}
	
	/**
	 * 회원 등록
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> join(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		// 회원등록
		userMapper.insert(userDTO);
		// 임시회원 정보 삭제
		TempUserInfoDTO tempUserDTO = new TempUserInfoDTO();
		tempUserDTO.setId(userDTO.getId());
		tempUserDTO.setEmail(userDTO.getEmail());
		tempUserInfoMapper.deleteByIdAndEmail(tempUserDTO);
		
		rs.put("result", true);
		rs.put("msg", "회원 가입이 완료되었습니다.\n로그인 화면으로 가시겠습니까?");
		return rs;
	}
	
	
	/**
	 * 회원정보 수정
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> modify(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		userMapper.update(userDTO);
		rs.put("result", true);
		rs.put("msg", "변경사항이 적용되었습니다.");		
		return rs;
	}
}
