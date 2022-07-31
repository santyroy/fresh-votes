package com.freshvotes.repository;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByFeature(Feature feature);
}
