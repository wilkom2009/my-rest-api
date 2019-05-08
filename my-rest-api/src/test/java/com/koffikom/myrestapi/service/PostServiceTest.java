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

import com.koffikom.myrestapi.dao.PostRepository;
import com.koffikom.myrestapi.model.Post;
import com.koffikom.myrestapi.service.impl.PostServiceImpl;

@RunWith(SpringRunner.class)
public class PostServiceTest {

	@TestConfiguration // création du bean uniquement nécessaire pour les tests
	static class PostServiceImplConfiguration {
		@Bean
		public PostService postService() {
			return new PostServiceImpl();
		}
	}
	
	@Autowired
	private PostService postService;
	@MockBean//création d'un mockBean pour PostRepository
	private PostRepository postRepository;
	
	@Test
	public void testFindAll() throws Exception {
		Post post1 = new Post("Post1", "Body post1");
		List<Post> allPosts = Arrays.asList(post1);
		Mockito.when(postRepository.findAll()).thenReturn(allPosts);
		Collection<Post> posts = postService.getAllPosts();
		assertNotNull(posts);
		assertEquals(posts, allPosts);
		assertEquals(posts.size(), allPosts.size());
		verify(postRepository).findAll();
	}
	
	/**
	 * Test de la méthode saveOrUpdatePost
	 */
	@Test
	public void testSave() throws Exception {
		Post postMock = new Post(1L, "Post", "Body post");
		Post post1 = new Post(1L,"Post1", "Body post1");
		Mockito.when(postRepository.save((post1))).thenReturn(postMock);
		Post postSaved = postService.saveOrUpdatePost(post1);
		assertNotNull(postSaved);
		assertEquals(postMock.getId(), postSaved.getId());//on vérifie l'egalité des ID
		assertEquals(postMock.getTitle(), postSaved.getTitle());//vérification des titres
		verify(postRepository).save(any(Post.class));
	}
	
	/**
	 * Test de la méthode de suppression
	 */
	@Test
	public void testDeletePost() throws Exception {
		Post postMock = new Post(1L, "Post", "Body post");
		Post post1 = new Post(1L,"Post1", "Body post1");
		Mockito.when(postRepository.save((post1))).thenReturn(postMock);
		Post postSaved = postService.saveOrUpdatePost(post1);
		assertNotNull(postSaved);
		assertEquals(postMock.getId(), postSaved.getId());//on vérifie l'egalité des ID
		postService.deletePost(postSaved.getId()+1);
		verify(postRepository).deleteById(any(Long.class));
	}
}
