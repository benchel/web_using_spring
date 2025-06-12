package site.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachedFileDTO {
	private int parent;
	private String category;
	private String key;
	private String name;
	private String size;
	private String mediaType;
}
