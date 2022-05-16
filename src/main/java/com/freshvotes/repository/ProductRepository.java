package com.freshvotes.repository;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Named Query
    // select * from product where user = :user;
    List<Product> findByUser(User user);

//    @Query(value = "select * from product p where p.id=:id", nativeQuery = true)
    @Query(value = "select p from Product p where p.id=:id", nativeQuery = false)
    Optional<Product> findByIdWithUser(Integer id);

    Optional<Product> findByName(String productName);
}
