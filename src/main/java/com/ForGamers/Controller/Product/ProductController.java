package com.ForGamers.Controller.Product;

import com.ForGamers.Model.Product.Product;
import com.ForGamers.Service.Product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "products", description = "Operaciones relacionadas a los productos.")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //METODOS
    @Operation(summary = "Obtener listado de productos.", description = "Devuelve una lista de todos los productos.")
    @GetMapping
    public List<Product> listProducts() {
        return productService.listProducts();
    }

    @Operation(summary = "Agregar un producto.", description = "No incluir id al agregar un producto.")
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @Operation(summary = "Eliminar un producto por id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){

        return productService.deleteProduct(id);
    }

    @Operation(summary = "Obtener un producto por id.")
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        return productService.getById(id);
    }

    @Operation(summary = "Editar un producto.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.modifyProduct(id, updatedProduct);
    }
}
