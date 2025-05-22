package com.ForGamers.Service;

import com.ForGamers.Model.Product;
import com.ForGamers.Repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
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

    public Product getById(Long id){
        return productRepository.getReferenceById(id);
    }
}
