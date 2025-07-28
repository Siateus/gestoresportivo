package br.com.gestoresportivo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidoValidator implements ConstraintValidator<CpfValido, String> {

    @Override
    public void initialize(CpfValido constraintAnnotation) {
        // Nada a inicializar para esta validação
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return true; // Considera CPF nulo/vazio válido se não houver @NotBlank
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: "111.111.111-11") - inválido
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Validação dos dígitos verificadores
        try {
            int d1, d2;
            int digito1, digito2, resto;
            int soma = 0;
            String cpfCalculo;

            cpfCalculo = cpf.substring(0, 9);

            // Calcula o primeiro dígito verificador
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(cpfCalculo.substring(i, i + 1)) * (10 - i);
            }
            resto = 11 - (soma % 11);
            if (resto == 10 || resto == 11) {
                digito1 = 0;
            } else {
                digito1 = resto;
            }

            soma = 0;
            cpfCalculo = cpfCalculo + digito1;

            // Calcula o segundo dígito verificador
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(cpfCalculo.substring(i, i + 1)) * (11 - i);
            }
            resto = 11 - (soma % 11);
            if (resto == 10 || resto == 11) {
                digito2 = 0;
            } else {
                digito2 = resto;
            }

            // Pega os dígitos verificadores do CPF original
            d1 = Integer.parseInt(cpf.substring(9, 10));
            d2 = Integer.parseInt(cpf.substring(10, 11));

            // Compara os dígitos calculados com os originais
            return (digito1 == d1) && (digito2 == d2);

        } catch (NumberFormatException e) {
            return false; // Se não for número, é inválido
        }
    }
}