package movieDbApp;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import movieDbApp.coreUtilites.exceptions.BusinessException;
import movieDbApp.coreUtilites.exceptions.ProblemDetails;
import movieDbApp.coreUtilites.exceptions.ValidationProblemDetails;

@SpringBootApplication
@RestControllerAdvice
public class MovieCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCrudApplication.class, args);
	}
	
	
	@ExceptionHandler 
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetails handleBusinessException(BusinessException businessException) {
		ProblemDetails details = new ProblemDetails();
		details.setMessage(businessException.getMessage());
		
		return details;
	}
	
	@ExceptionHandler 
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetails handleValidationException(MethodArgumentNotValidException argumentNotValidException) {
		ValidationProblemDetails details = new ValidationProblemDetails();
		details.setMessage("validation.exception");
		details.setValidationErrors(new HashMap<String,String>());
		
		for(FieldError fieldError: argumentNotValidException.getBindingResult().getFieldErrors()) {
			details.getValidationErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return details;
	}
	
	@Bean  
	public ModelMapper getModelMpaper() {
		return new ModelMapper();
	}
	
	

}
