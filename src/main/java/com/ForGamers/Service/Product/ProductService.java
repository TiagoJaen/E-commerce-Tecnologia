package com.ForGamers.Service.Product;

import com.ForGamers.Exception.ExistentEmailException;
import com.ForGamers.Exception.ExistentProductException;
import com.ForGamers.Exception.ExistentUsernameException;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Repository.Product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        if (productRepository.getByName(product.getName()).isPresent()) {
            throw new ExistentProductException();
        }
        return productRepository.save(product);
    }

    public ResponseEntity<Void> deleteProduct(Long id){
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public ResponseEntity<Void> modifyProduct(Long id, Product product){
        if (productRepository.existsById(id)) {
            Product oldProduct = productRepository.getReferenceById(id);
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setImage(product.getImage());

            productRepository.save(oldProduct);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public Optional<Product> getById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getByNameIgnoringCase(String name) {
        return productRepository.getByNameContainingIgnoreCase(name);
    }
}
