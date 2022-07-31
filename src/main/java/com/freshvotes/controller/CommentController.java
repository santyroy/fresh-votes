package com.freshvotes.controller;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.repository.CommentRepository;
import com.freshvotes.repository.UserRepository;
import com.freshvotes.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureService featureService;

    @GetMapping
    @ResponseBody
    public List<Comment> getComments(@PathVariable("featureId") int featureId) {
        System.out.println(commentRepository.findByFeature(featureService.findById(featureId).get()));
        return commentRepository.findByFeature(featureService.findById(featureId).get());
    }

    @PostMapping
    public String postComment(Principal principal,
                              @PathVariable("featureId") int featureId, @PathVariable("productId") int productId,
                              Comment rootComment,
                              @RequestParam(value = "parentId", required = false) Integer parentId,
                              @RequestParam(value = "childCommentText", required = false) String childCommentText) {
        User user = userRepository.findByUsername(principal.getName());
        Optional<Feature> feature = featureService.findById(featureId);

        // adding a root level comment
        if (StringUtils.hasText(rootComment.getText())) {
            if (user != null && feature.isPresent()) {
                rootComment.setUser(user);
                rootComment.setFeature(feature.get());
            }
            rootComment.setCreatedDate(LocalDateTime.now());
            commentRepository.save(rootComment);
        }
        // adding a child comment
        else if (parentId != null) {
            Comment childComment = new Comment();
            Optional<Comment> parentCommentOpt = commentRepository.findById(parentId);
            if (parentCommentOpt.isPresent()) {
                childComment.setComment(parentCommentOpt.get());
            }
            childComment.setText(childCommentText);

            // adding a root level comment
            if (user != null && feature.isPresent()) {
                childComment.setUser(user);
                childComment.setFeature(feature.get());
            }
            childComment.setCreatedDate(LocalDateTime.now());
            commentRepository.save(childComment);
        }
        return "redirect:/products/" + productId + "/features/" + featureId;
    }
}
