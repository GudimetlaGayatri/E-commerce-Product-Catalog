---

package com.example.productcatalog.controller;

import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(repository.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            Product existing = optional.get();
            existing.setName(updatedProduct.getName());
            existing.setDescription(updatedProduct.getDescription());
            existing.setPrice(updatedProduct.getPrice());
            existing.setCategory(updatedProduct.getCategory());
            existing.setStockQuantity(updatedProduct.getStockQuantity());
            return ResponseEntity.ok(repository.save(existing));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> filterByCategory(@PathVariable String category) {
        return ResponseEntity.ok(repository.findByCategory(category));
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>> filterByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(repository.findByPriceRange(min, max));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(repository.searchByName(name));
    }
}

