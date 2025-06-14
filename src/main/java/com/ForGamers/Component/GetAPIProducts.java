package com.ForGamers.Component;

import com.ForGamers.Model.Product.APIProduct;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Repository.Product.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class GetAPIProducts {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ProductRepository productRepository;

    @PostConstruct
    public void getProducts() {
        String url = "https://fakestoreapi.com/products";

        ResponseEntity<APIProduct[]> response =
                restTemplate.getForEntity(url, APIProduct[].class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            System.out.println("No se pudieron obtener los productos de la API.");
            return;
        }

        List<APIProduct> APIProducts = Arrays.stream(response.getBody())
                .filter(p -> p.getCategory().equals("electronics"))
                .toList();

        for (APIProduct a : APIProducts) {
            boolean exists = productRepository.getByName(a.getTitle()).isPresent();
            if (!exists) {
                Product newProduct = new Product();
                newProduct.setName(a.getTitle());
                newProduct.setPrice(a.getPrice() * 1200);
                newProduct.setDescription(a.getDescription());
                newProduct.setImage(a.getImage());
                newProduct.setStock((int)(Math.random() * 101));

                productRepository.save(newProduct);
            }
        }
    }
}
