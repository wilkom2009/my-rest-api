package com.koffikom.myrestapi.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.koffikom.myrestapi.dao.CommentRepository;
import com.koffikom.myrestapi.dao.PostRepository;
import com.koffikom.myrestapi.model.Comment;
import com.koffikom.myrestapi.model.Post;
import com.koffikom.myrestapi.service.impl.CommentServiceImpl;
import com.koffikom.myrestapi.service.impl.PostServiceImpl;

@RunWith(SpringRunner.class)
public class CommentServiceTest {

	@TestConfiguration // création du bean uniquement nécessaire pour les tests
	static class CommentServiceImplConfiguration {
		@Bean
		public CommentService commentService() {
			return new CommentServiceImpl();
		}

		@Bean
		public PostService postService() {
			return new PostServiceImpl();
		}

	}

	@Autowired
	private CommentService commentService;
	@Autowired
	private PostService postService;
	@MockBean // création d'un mockBean pour PostRepository
	private CommentRepository commentRepository;
	@MockBean//création d'un mockBean pour PostRepository
	private PostRepository postRepository;

	/**
	 * Test de la méthode getCommentsByPostId
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCommentsByPostId() throws Exception {
		Post post1 = new Post(1L, "Post1", "Body post1");
		Comment comment1 = new Comment("Commentaire1", post1);
		List<Comment> allComments = Arrays.asList(comment1);
		Mockito.when(commentRepository.getCommentsByPostId(post1.getId())).thenReturn(allComments);
		Collection<Comment> comments = commentService.getCommentsByPostId(post1.getId());
		assertNotNull(comments);
		assertEquals(comments, allComments);
		assertEquals(comments.size(), allComments.size());
		verify(commentRepository).getCommentsByPostId(any(Long.class));
	}

	/**
	 * Test de la méthode saveOrUpdatePost
	 */
	@Test
	public void testSave() throws Exception {
		Post post1 = new Post(1L,"Post", "Body post");
		Mockito.when(postRepository.save(post1)).thenReturn(post1);
		post1 = postService.saveOrUpdatePost(post1);
		Comment comment1 = new Comment("Commentaire1", post1);
		Comment commentMock = new Comment("Commentaire1", post1);
		Mockito.when(commentRepository.save(comment1)).thenReturn(commentMock);
		Comment commentSaved = commentService.saveOrUpdateComment(1L, comment1);
		assertNotNull(commentSaved);
		assertEquals(commentMock.getId(), commentSaved.getId());// on vérifie l'egalité des ID
		assertEquals(commentMock.getBody(), commentSaved.getBody());// vérification des contenus
		verify(commentRepository).save(any(Comment.class));
	}

//	/**
//	 * Test de la méthode de suppression
//	 */
//	@Test
//	public void testDeletePost() throws Exception {
//		Post postMock = new Post(1L, "Post", "Body post");
//		Post post1 = new Post(1L,"Post1", "Body post1");
//		Mockito.when(postRepository.save((post1))).thenReturn(postMock);
//		Post postSaved = postService.saveOrUpdatePost(post1);
//		assertNotNull(postSaved);
//		assertEquals(postMock.getId(), postSaved.getId());//on vérifie l'egalité des ID
//		postService.deletePost(postSaved.getId()+1);
//		verify(postRepository).deleteById(any(Long.class));
//	}
}
