package com.koffikom.myrestapi.dao;

import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.koffikom.myrestapi.model.Post;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private PostRepository postRepository;
	Post post = new Post("Post test 1", "Body test post1");

	@Before
	public void setUp() {
		post = entityManager.persist(post);// on enregistre un post à chaque démarrage de test
		entityManager.flush();
	}

	/**
	 * Test de la méthode findAll
	 */
	@Test
	public void testFindAll() {
		List<Post> posts = postRepository.findAll();
		assertThat(1, is(posts.size()));// on vérifie si la requete renvoie exactement le nombre d'enregistrement
	}

	/**
	 * Test de la méthode save
	 */
	@Test
	public void testSave() {
		Post post = new Post("MTitle", "mon blog");
		Post persistPost = postRepository.save(post);
		assertNotNull(persistPost.getId());// vérifie si enregistrement bien effectué
		assertThat("MTitle", is(persistPost.getTitle()));// test l'intégrité de l'enregistrement
	}
	
	/**
	 * Test de la méthode de mise à jour
	 */
	@Test
	public void testUpdate() {
		Post postToUpdate = postRepository.getOne(post.getId());
		postToUpdate.setBody("DD");
		postRepository.save(postToUpdate);
		Post postUpdated = postRepository.getOne(postToUpdate.getId());
		assertNotNull(postUpdated.getId());// vérifie si enregistrement bien effectué
		assertThat("DD", is(postUpdated.getBody()));// test l'intégrité de l'enregistrement
	}
	
	/**
	 * Test de la méthode de suppression
	 */
	@Test
	public void testDelete() {
		postRepository.deleteById(post.getId());
		assertNull(postRepository.getOne(post.getId()));
	}
}
