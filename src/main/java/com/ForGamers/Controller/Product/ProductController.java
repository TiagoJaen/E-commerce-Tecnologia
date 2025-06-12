package com.ForGamers.Controller.Product;

import com.ForGamers.Exception.ExistentProductException;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Service.Product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Tag(name = "products", description = "Operaciones relacionadas a los productos.")
public class ProductController {
    private ProductService services;

    public ProductController(ProductService productService) {
        this.services = productService;
    }

    //METODOS
    @Operation(summary = "Obtener listado de productos.", description = "Devuelve una lista de todos los productos.")
    @GetMapping
    public List<Product> listProducts() {
        return services.listProducts();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Agregar un producto.", description = "No incluir id al agregar un producto.")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product saved = services.addProduct(product);
            return ResponseEntity.ok(saved);
        }catch (ExistentProductException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Eliminar un producto por id.")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteProduct(@RequestParam(name = "id") Long id){
        return services.deleteProduct(id);
    }

    //BUSCAR POR ID
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Obtener un producto por id.")
    @GetMapping(value = "/id", params = "id")
    public ResponseEntity<?> getById(@RequestParam(name = "id", required = false) Long id){
        Optional<Product> product = services.getById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    //BUSCAR POR NOMBRE
    @GetMapping(value = "/name", params = "name")
    public List<Product> getByName(@RequestParam(name = "name", required = false) String name) {
        if (name == null || name.isEmpty()) {
            return listProducts();
        } else {
            return services.getByNameIgnoringCase(name);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Editar un producto.")
    @PutMapping
    public ResponseEntity<Void> modifyProduct(@RequestBody Product updatedProduct) {
        return services.modifyProduct(updatedProduct.getId(), updatedProduct);
    }
}