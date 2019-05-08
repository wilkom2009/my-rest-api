package com.koffikom.myrestapi.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.koffikom.myrestapi.model.Comment;
import com.koffikom.myrestapi.model.Post;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private CommentRepository commentRepository;
	
	Post post = new Post("Post 0", "Body post0");
	Post post1 = new Post("Post 1", "Body post1");
	Comment comment;

	@Before
	public void setUp() {
		post = entityManager.persist(post);// on enregistre un post à chaque démarrage de test
		post1 = entityManager.persist(post1);// on enregistre un post à chaque démarrage de test
		comment = entityManager.persist(new Comment("Commentaire", post));// on enregistre un commentaire à chaque démarrage de test
		entityManager.flush();
	}

	/**
	 * Test de la méthode findAll
	 */
	@Test
	public void testFindAll() {
		List<Comment> comments = commentRepository.findAll();
		assertThat(1, is(comments.size()));// on vérifie si la requete renvoie exactement le nombre d'enregistrement
	}

	/**
	 * Test de la méthode save
	 */
	@Test
	public void testSave() {
		Comment oldComment = new Comment("Commentaire1", post);
		Comment newComment = commentRepository.save(oldComment);
		assertNotNull(newComment.getId());// vérifie si enregistrement bien effectué
		assertThat("Commentaire1", is(newComment.getBody()));// test l'intégrité de l'enregistrement
	}
	
	/**
	 * Test de la méthode de mise à jour
	 */
	@Test
	public void testUpdate() {
		Comment commentToUpdate = commentRepository.getOne(comment.getId());
		commentToUpdate.setBody("CC");
		commentRepository.save(commentToUpdate);
		Comment commentUpdate = commentRepository.getOne(commentToUpdate.getId());
		assertNotNull(commentUpdate.getId());// vérifie si enregistrement bien effectué
		assertThat("CC", is(commentUpdate.getBody()));// test l'intégrité de l'enregistrement
	}
	
	/**
	 * Test de la méthode de suppression
	 */
	@Test
	public void testDelete() {
		commentRepository.deleteById(comment.getId());
		assertNull(commentRepository.getOne(comment.getId()));
	}
	
	/**
	 * Test de la méthode getCommentsByPostId
	 */
	@Test
	public void testCommentsByPostId() {
		entityManager.persist(new Comment("Commentaire1", post));//ajout d'un autre commentaire
		Collection<Comment> comments = commentRepository.getCommentsByPostId(post.getId());
		assertThat(2, is(comments.size()));//comments doit contenir 2 elements
	}
}
