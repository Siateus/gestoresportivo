package br.com.gestoresportivo.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Indica que esta classe é um "advisor" global para controladores
public class GlobalExceptionHandler {

    // --- Tratamento para Erros de Validação (@Valid) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // --- Tratamento para ResponseStatusException (lançadas no Service) ---
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

    // --- Tratamento para Erros de Integridade de Dados do Banco de Dados (inclui triggers) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Ou HttpStatus.CONFLICT (409) dependendo do caso
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Erro de integridade de dados.";
        // Tenta extrair a mensagem da trigger ou do erro SQL
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            String causeMessage = ex.getCause().getMessage();
            // Exemplo de como pegar a mensagem da trigger para MySQL
            if (causeMessage.contains("Não é possível inscrever uma equipe em um torneio de modalidade diferente.")) {
                errorMessage = "Erro de regra de negócio: " + causeMessage;
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); // Retorna 400
            }
            // Para erros de duplicidade de PK/UNIQUE, pode ser 409 CONFLICT
            if (causeMessage.contains("Duplicate entry") || causeMessage.contains("for key 'PRIMARY'")) {
                errorMessage = "Conflito de dados: Registro duplicado ou violação de unicidade.";
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage); // Retorna 409
            }
            // Mensagem genérica se não for um dos casos acima
            errorMessage = "Erro no banco de dados: " + causeMessage;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    // --- Tratamento para outras exceções genéricas (opcional, para capturar tudo que não foi tratado) ---
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllUncaughtException(Exception ex) {
        // Logar a exceção completa para depuração
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor: " + ex.getMessage());
    }
}