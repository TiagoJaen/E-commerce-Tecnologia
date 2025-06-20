package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Sale.Card;
import com.ForGamers.Model.Sale.CardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Getter
public class CardDTOService {
    private PasswordEncoder encoder;

    public Card DTOtoCard(CardDTO dto) throws Exception {
        return new Card(
                dto.getId(),
                encoder.encode(dto.getHolder()),
                encoder.encode(dto.getNumber()),
                encoder.encode(dto.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy"))),
                encoder.encode(dto.getCvv().toString()),
                dto.hashCode()
        );
    }
}