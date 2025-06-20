package site.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ExcController {
	
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseEntity<?> fileSizeException() {
    	Map<String, Object> result = new HashMap<>();
    	result.put("message", "파일의 크기가 용량을 초과하였습니다.(최대 10MB)");
    	return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    
}
