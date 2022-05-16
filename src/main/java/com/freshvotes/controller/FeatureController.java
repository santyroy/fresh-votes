package com.freshvotes.controller;

import com.freshvotes.domain.Feature;
import com.freshvotes.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @PostMapping
    public String createFeature(@PathVariable("productId") Integer productId) {
        Feature feature = featureService.createFeature(productId);

        return "redirect:/products/" + productId + "/features/" + feature.getId();
    }

    @GetMapping("/{featureId}")
    public String getFeature(@PathVariable Integer productId, @PathVariable Integer featureId, ModelMap modelMap) {
        Optional<Feature> feature = featureService.findById(featureId);
        if(feature.isPresent()){
            modelMap.put("feature", feature.get());
        }

        // TODO: handle situation if feature is not found by featureId
        return "feature";
    }

    @PostMapping("{featureId}")
    public String updateFeature(@ModelAttribute Feature feature, @PathVariable Integer productId, @PathVariable Integer featureId) {
        feature = featureService.save(feature);
        return "redirect:/products/" + productId + "/features/" + feature.getId();
    }
}
