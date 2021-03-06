package com.koffikom.myrestapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koffikom.myrestapi.model.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	@Query("Select p from Post p where p.title = ?1")
	Post getPostByTitle(String title);
}
