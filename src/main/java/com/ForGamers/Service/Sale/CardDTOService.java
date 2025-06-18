package com.ForGamers.Service.Sale;

import com.ForGamers.Model.Sale.Card;
import com.ForGamers.Model.Sale.CardDTO;
import com.ForGamers.Security.AESEncriptService;

import java.time.format.DateTimeFormatter;

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
}