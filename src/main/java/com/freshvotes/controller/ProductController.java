package com.freshvotes.controller;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.ProductRepository;
import com.freshvotes.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Optional;

@Controller
public class ProductController {

    private Logger log = LoggerFactory.getLogger(ProductController.class);
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

    @GetMapping("/p/{productName}")
    public String productUserView(@PathVariable("productName") String productName, ModelMap modelMap) {
        if(productName != null) {
            String decodeProductName = URLDecoder.decode(productName, StandardCharsets.UTF_8);
            log.info("Decoded Product name: " + decodeProductName);
            Optional<Product> productOpt = productRepository.findByName(decodeProductName);
            if(productOpt.isPresent()) {
                modelMap.put("product", productOpt.get());
            }
        }
        return "productUserView";
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
