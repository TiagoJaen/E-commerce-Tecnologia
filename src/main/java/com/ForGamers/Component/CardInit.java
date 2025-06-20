package com.ForGamers.Component;

import com.ForGamers.Model.Sale.CardDTO;
import com.ForGamers.Service.Sale.CardService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
@AllArgsConstructor
public class CardInit {
    private final CardService service;


    @PostConstruct
    public void createCards() {
        if(service.listCards().isEmpty()) {
            service.addCard(new CardDTO(
                    "rivato aravir",
                    "1111111111111111",
                    YearMonth.of(2025, 10),
                    555
            ));

            service.addCard(new CardDTO(
                    "pepito",
                    "2222222222222222",
                    YearMonth.of(2026, 11),
                    666
            ));

            service.addCard(new CardDTO(
                    "pepote",
                    "3333333333333333",
                    YearMonth.of(2027, 12),
                    777
            ));
        }
    }
}
