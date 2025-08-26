package br.edu.infnet.heitorapi.controller.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.infnet.heitorapi.model.domain.exceptions.OcorrenciaInvalidaException;
import br.edu.infnet.heitorapi.model.domain.exceptions.OcorrenciaNaoEncontradaException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }    

    @ExceptionHandler(OcorrenciaInvalidaException.class)
    public ResponseEntity<Map<String, String>> handleOcorrenciaInvalidaException(OcorrenciaInvalidaException ex) {
        Map<String, String> errors = new HashMap<>();
        
        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        errors.put("Mensagem", ex.getMessage());
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }    
    
    @ExceptionHandler(OcorrenciaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleOcorrenciaNaoEncontradaException(OcorrenciaNaoEncontradaException ex) {
        Map<String, String> errors = new HashMap<>();
        
        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.NOT_FOUND.toString());
        errors.put("Mensagem", ex.getMessage());
        
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }    
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        
        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        errors.put("Mensagem", ex.getMessage());
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        
        errors.put("Data/hora", LocalDateTime.now().toString());
        errors.put("Status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errors.put("Mensagem", "Erro interno do servidor: " + ex.getMessage());
        
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }    
}

