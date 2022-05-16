package com.freshvotes.service;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.repository.FeatureRepository;
import com.freshvotes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FeatureRepository featureRepository;

    public Feature createFeature(int productId) {
        Optional<Product> productOpt = productRepository.findById(productId);

        Feature feature = new Feature();

        if(productOpt.isPresent()) {
            Product product = productOpt.get();

            // As the relationship between Product and Feature is bidirectional
            // We need to set feature with product
            feature.setProduct(product);
            // And product with feature
            product.getFeatures().add(feature);

            feature.setStatus("Pending review");
            featureRepository.save(feature);
        }

        return feature;
    }

    public Feature save(Feature feature) {
        return featureRepository.save(feature);
    }

    public Optional<Feature> findById(Integer featureId) {
        return featureRepository.findById(featureId);
    }
}
