package br.com.vrbeneficio.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ExceptionResponse implements Response {
	private Date timestamp;
	private HttpStatus httpCodeMessage;
	private String message = "";

	public ExceptionResponse() {
		super();
	}

	public ExceptionResponse(Date timestamp, String message, HttpStatus httpCodeMessage) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.httpCodeMessage = httpCodeMessage;
	}
	
}