package br.com.minhaempresa.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errorsList = new ArrayList<>();

	public ValidationError(Integer status, String mensagem, Long timeStamp) {
		super(status, mensagem, timeStamp);
		
	}

	public List<FieldMessage> getErrorsList() {
		return errorsList;
	}

	public void addError(String fieldName, String message) {
		errorsList.add(new FieldMessage(fieldName, message));
	}
}
