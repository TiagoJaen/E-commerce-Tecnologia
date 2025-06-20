package com.ForGamers.Service.Sale;

import com.ForGamers.Exception.CardAlreadyExistsException;
import com.ForGamers.Model.Sale.Card;
import com.ForGamers.Model.Sale.CardDTO;
import com.ForGamers.Repository.Sale.CardRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class CardService {
    private CardRepository cardRepository;
    private CardDTOService dtoService;

    public Card addCard(CardDTO dto){
        if (cardRepository.getCard(dto.hashCode()).isPresent()) {
            throw new CardAlreadyExistsException("Ya existe la tarjeta.");
        }
        return cardRepository.save(dtoService.DTOtoCard(dto));
    }

    public ResponseEntity<Void> deleteCard(Long id){
        if (!cardRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cardRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Card> listCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getById(Long id){
        return cardRepository.findById(id);
    }

    public Optional<Card> getCard(int hashcode) {
        return cardRepository.getCard(hashcode);
    }
}