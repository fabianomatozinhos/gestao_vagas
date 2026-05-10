package gestao_vagas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PrimeiroTeste {

    @Test
    public void deve_ser_possivel_calcular_dois_numero() {
        var result = calculate(5, 3);
        assertEquals(result, 8);
    }

    @Test
    public void validar_valor_incorreto() {
        var result = calculate(5, 7);
        assertNotEquals(result, 8);
    }

    public static double calculate(int num1, int num2) {
        return num1 + num2;
    }

}
