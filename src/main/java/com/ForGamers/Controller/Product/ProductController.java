package com.ForGamers.Controller.Product;

import com.ForGamers.Exception.ProductAlreadyExistsException;
import com.ForGamers.Model.Product.Product;
import com.ForGamers.Service.Product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    //GET
    //Listado
    @Operation(summary = "Obtener listado de productos.", description = "Devuelve una lista de todos los productos.")
    @GetMapping("/all")
    public List<Product> listProducts() {
        return services.listProducts();
    }

    //Paginación
    @Operation(summary = "Obtener listado de productos con paginación.")
    @GetMapping("/paginated")
    public Page<Product> listProductsPaginated(@RequestParam(name = "page") int page,
                                                @RequestParam(name = "size") int size) {
        return services.listProductsPaginated(page, size);
    }

    //BUSCAR POR ID
    @Operation(summary = "Obtener un producto por id.")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id", required = false) Long id){
        Optional<Product> product = services.getById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    //BUSCAR POR NOMBRE
    @Operation(summary = "Obtener un producto por nombre.")
    @GetMapping("/name/{name}")
    public List<Product> getByNameIgnoringCase(@PathVariable(name = "name", required = false) String name) {
        if (name == null || name.isEmpty()) {
            return listProducts();
        } else {
            return services.getByNameIgnoringCase(name);
        }
    }

    //POST
    @Operation(summary = "Agregar un producto.", description = "No incluir id al agregar un producto.")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product saved = services.addProduct(product);
            return ResponseEntity.ok(saved);
        }catch (ProductAlreadyExistsException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }

    //DELETE
    @Operation(summary = "Eliminar un producto por id.")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id){
        return services.deleteProduct(id);
    }

    //PUT
    @Operation(summary = "Editar un producto.")
    @PutMapping
    public ResponseEntity<?> modifyProduct(@RequestBody Product updatedProduct) {
        try{
            services.modifyProduct(updatedProduct.getId(), updatedProduct);
            return ResponseEntity.ok().build();
        }catch (ProductAlreadyExistsException e) {
            return ResponseEntity.badRequest().body((e.getMessage()));
        }
    }
}