package com.ForGamers.Model.Sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardDTO {
    private Long id;

    @Size(max = 20, message = "El titular debe ser de maximo 20 caracteres")
    private String holder;

    @Pattern(regexp = "\\d{16}", message = "El numero de tarjeta debe ser de 16 numeros")
    private String number;

    private YearMonth expirationDate;

    @Min(value = 100, message = "El codigo de seguridad debe ser de 3 digitos")
    @Max(value = 999, message = "El codigo de seguridad debe ser de 3 digitos")
    @Positive(message = "El codigo de seguridad debe ser positivo")
    private Integer cvv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(holder, cardDTO.holder) && Objects.equals(number, cardDTO.number) && Objects.equals(expirationDate, cardDTO.expirationDate) && Objects.equals(cvv, cardDTO.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(holder, number, expirationDate, cvv);
    }
}