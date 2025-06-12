package com.ForGamers.Model.Sale;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardDTO {
    private Long id;

    @Size(max = 20, message = "El titular debe ser de maximo 20 caracteres")
    private String holder;

    @Pattern(regexp = "\\d{16}", message = "El numero de tarjeta debe ser de 16 numeros")
    private String number;

    private LocalDate fecha;

    @Size(min = 3, max = 3, message = "El codigo de seguridad debe ser de 3 numeros enteros")
    private Integer cvv;
}