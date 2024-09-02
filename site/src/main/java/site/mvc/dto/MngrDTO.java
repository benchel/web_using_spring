package site.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MngrDTO {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String contact;
	private String jDate;
}
