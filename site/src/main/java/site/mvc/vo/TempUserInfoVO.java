package site.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TempUserInfoVO {
	private String id;
	private String name;
	private String email;
	private String certNum;
	private String date;
	private int isCert;	
}
