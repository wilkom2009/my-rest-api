package com.koffikom.myrestapi.service.impl;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koffikom.myrestapi.dao.CommentRepository;
import com.koffikom.myrestapi.dao.PostRepository;
import com.koffikom.myrestapi.exception.BusinessResourceException;
import com.koffikom.myrestapi.model.Comment;
import com.koffikom.myrestapi.service.CommentService;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;

	@Override
	public Collection<Comment> getCommentsByPostId(Long postId) {
		return commentRepository.getCommentsByPostId(postId);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Comment saveOrUpdateComment(Long postId, Comment comment) {
		if (!postRepository.existsById(postId)) {
			throw new BusinessResourceException("Post inexistant", "Le post d'ID : " + postId + " n'existe pas!",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return postRepository.findById(postId).map(post -> {
			comment.setPost(post);
			return commentRepository.save(comment);
		}).orElseThrow(() -> new BusinessResourceException("Erreur de création/mise à jour",
				"Erreur de création/mise à jour du post : " + comment.getBody(), HttpStatus.INTERNAL_SERVER_ERROR));

	}

	@Override
	public boolean existsById(Long id) {
		return commentRepository.existsById(id);
	}

	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.getOne(id);
	}

}
