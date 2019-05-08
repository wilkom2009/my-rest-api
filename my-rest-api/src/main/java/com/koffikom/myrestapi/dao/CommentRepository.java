package com.koffikom.myrestapi.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.koffikom.myrestapi.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("Select c from Comment c where c.post.id = ?1")
	Collection<Comment> getCommentsByPostId(Long postId);
}
