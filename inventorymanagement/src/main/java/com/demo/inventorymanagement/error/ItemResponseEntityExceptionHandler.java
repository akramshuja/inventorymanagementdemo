package com.demo.inventorymanagement.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ItemResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ItemExistenceException.class)
    public ResponseEntity<Void> handleItemAlreadyExistsException(ItemExistenceException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleItemAlreadyExistsException(RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
