package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Sale.Card;
import com.ForGamers.Model.Sale.CardDTO;
import com.ForGamers.Security.AESEncriptService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Getter
public class CardDTOService {
    private AESEncriptService encoder;

    public Card DTOtoCard(CardDTO dto) throws Exception {
        return new Card(
                dto.getId(),
                encoder.encode(dto.getHolder()),
                encoder.encode(dto.getNumber()),
                encoder.encode(dto.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy"))),
                encoder.encode(dto.getCvv().toString())
        );
    }

    public CardDTO CardToDTO(Card card) throws Exception{
        return new CardDTO(
                card.getId(),
                encoder.decode(card.getHolder()),
                encoder.decode(card.getNumber()),
                LocalDate.parse(encoder.decode(card.getHolder()), DateTimeFormatter.ofPattern("MM/yy")),
                Integer.parseInt(encoder.decode(card.getCvv()))
        );
    }
}