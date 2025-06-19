package com.ForGamers.Service.Product;

import com.ForGamers.Exception.ProductAlreadyExistsException;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Repository.Product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            throw new ProductAlreadyExistsException("Este producto ya se encuentra registrado.");
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

    public Page<Product> listProductsPaginated(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Product> getById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getByNameIgnoringCase(String name) {
        return productRepository.getByNameContainingIgnoreCase(name);
    }

    public ResponseEntity<Void> modifyProduct(Long id, Product product){
        Product oldProduct = productRepository.getReferenceById(id);
        //Verificar si se cambió el nombre
        if (!oldProduct.getName().equals(product.getName())) {
            //Verificar que el nuevo nombre no esté en uso
            if (productRepository.getByName(product.getName()).isPresent()) {
                throw new ProductAlreadyExistsException("Este producto ya se encuentra registrado.");
            }
        }
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setImage(product.getImage());
        oldProduct.setStock(product.getStock());

        productRepository.save(oldProduct);
        return ResponseEntity.ok().build();
        }
}
