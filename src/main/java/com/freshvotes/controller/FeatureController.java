package com.freshvotes.controller;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;
import com.freshvotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String createFeature(Principal principal, @PathVariable("productId") Integer productId) {
        Feature feature = featureService.createFeature(principal.getName(), productId);

        return "redirect:/products/" + productId + "/features/" + feature.getId();
    }

    @GetMapping("/{featureId}")
    public String getFeature(Principal principal, @PathVariable Integer productId, @PathVariable Integer featureId, ModelMap modelMap) {
        Optional<Feature> featureOpt = featureService.findById(featureId);
        if (featureOpt.isPresent()) {
            Feature feature = featureOpt.get();
            modelMap.put("feature", feature);

            Set<Comment> commentsWithoutDuplicates = getCommentsWithoutDuplicates(0, new HashSet<>(), feature.getComments());
            modelMap.put("thread", commentsWithoutDuplicates);
            modelMap.put("rootComment", new Comment());
        }
        User user = userService.getUser(principal.getName());
        modelMap.put("user", user);

        return "feature";
    }

    // recursive solution to find the duplicate comments and add then to a set
    private SortedSet<Comment> getCommentsWithoutDuplicates(int page, Set<Integer> visitedComments, SortedSet<Comment> comments) {
        page++;
        Iterator<Comment> itr = comments.iterator();
        while (itr.hasNext()) {
            Comment comment = itr.next();
            boolean addToVisitedComments = visitedComments.add(comment.getId());

            // if not added means its already present, which refers to as duplicate comment
            if (!addToVisitedComments) {
                // remove the duplicate comment from the original comments Set
                itr.remove();
                if(page != 1) {
                    return comments;
                }
            }

            if (addToVisitedComments && !comment.getComments().isEmpty()) {
                getCommentsWithoutDuplicates(page, visitedComments, comment.getComments());
            }
        }
        return comments;
    }

    @PostMapping("{featureId}")
    public String updateFeature(Principal principal, @ModelAttribute Feature feature, @PathVariable Integer productId, @PathVariable Integer featureId) {
        feature = featureService.save(principal.getName(), feature);
        String encodeProductName = URLEncoder.encode(feature.getProduct().getName(), StandardCharsets.UTF_8);
        return "redirect:/p/" + encodeProductName;
    }
}
