package com.freshvotes.controller;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.ProductRepository;
import com.freshvotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable int productId, ModelMap modelMap, HttpServletResponse response) throws Exception {
        Optional<Product> productOpt = productRepository.findByIdWithUser(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            modelMap.put("product", product);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product with id " + productId + " not found.");
        }
        return "product";
    }

    @PostMapping("/products")
    public String createProduct(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Product product = new Product();
        product.setPublished(false);
        product.setUser(user);
        product = productRepository.save(product);

        return "redirect:/products/" + product.getId();
    }

    @PostMapping("/products/{productId}")
    public String updateProduct(@PathVariable int productId, @ModelAttribute Product product) {
        product = productRepository.save(product);
        return "redirect:/products/" + product.getId();
    }
}
