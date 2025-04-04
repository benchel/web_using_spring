package site.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TempUserInfoDTO {
	private String id;
	private String name;
	private String email;
	private String certNum;
	private String date;
	private int isCert;
	
	public TempUserInfoDTO(String id, String name, String email, String certNum) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.certNum = certNum;
	}
	
}
