package com.freshvotes.service;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repository.FeatureRepository;
import com.freshvotes.repository.ProductRepository;
import com.freshvotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private UserRepository userRepository;

    public Feature createFeature(String username, int productId) {
        Optional<Product> productOpt = productRepository.findById(productId);

        Feature feature = new Feature();

        if(productOpt.isPresent()) {
            Product product = productOpt.get();

            // As the relationship between Product and Feature is bidirectional
            // We need to set feature with product
            feature.setProduct(product);
            // And product with feature
            product.getFeatures().add(feature);

            User user = userRepository.findByUsername(username);
            user.getFeatures().add(feature);

            feature.setUser(user);

            feature.setStatus("Pending review");
            featureRepository.save(feature);
        }

        return feature;
    }

    public Feature save(String username, Feature feature) {
        User user = userRepository.findByUsername(username);
        feature.setUser(user);
        return featureRepository.save(feature);
    }

    public Optional<Feature> findById(Integer featureId) {
        return featureRepository.findById(featureId);
    }
}
