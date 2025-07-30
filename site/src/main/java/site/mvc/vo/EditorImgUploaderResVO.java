package site.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 응답 형식 : json
 * (예)
 {
	"fileName": "example.png",
    "uploaded": 1,
    "url": "https:\/\/ckeditor.com\/apps\/ckfinder\/userfiles\/images\/파일명.png"
 }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorImgUploaderResVO {
	private String fileName;
	private int uploaded;
	private String url;
}
