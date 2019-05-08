package com.koffikom.myrestapi.controller;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koffikom.myrestapi.exception.BusinessResourceException;
import com.koffikom.myrestapi.model.Comment;
import com.koffikom.myrestapi.model.Post;
import com.koffikom.myrestapi.service.CommentService;
import com.koffikom.myrestapi.service.PostService;
import com.koffikom.myrestapi.validator.CommentValidator;

/**
 * Gestion des Comment Classe controller qui fournit les services REST et les
 * CRUD sur les pages JSP
 * 
 * @author Koffi
 *
 */
@Controller
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentValidator commentValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(commentValidator);
	}

	/**
	 * Enregistrer ou mettre à jour un commentaire
	 * 
	 * @param comment
	 * @param postId
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return page read d'un post
	 */
	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("commentForm") @Validated Comment comment,
			@PathVariable(value = "postId") Long postId, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "comments/commentForm";
		} else {
			redirectAttributes.addFlashAttribute("css", "success");
			if (comment.getId() == null) {
				redirectAttributes.addFlashAttribute("msg", "Commentaire créé avec succès!");
			} else {
				redirectAttributes.addFlashAttribute("msg", "Modification réussie!");
			}

			commentService.saveOrUpdateComment(postId, comment);

			return "redirect:/posts/{postId}";
		}
	}

	/**
	 * Affiche la page JSP d'ajout d'un commentaire
	 * 
	 * @param id
	 * @param model
	 * @return page d'ajout
	 */
	@GetMapping("/posts/{postId}/comments/add")
	public String showForm(@PathVariable(value = "postId") Long id, Model model) {

		Post post = postService.getPostById(id);
		Comment comment = new Comment("Commentaire", post);
		model.addAttribute("commentForm", comment);

		return "comments/commentForm";
	}

	/**
	 * Affiche la page JSP modification d'un commentaire
	 * 
	 * @param id
	 * @param commentId
	 * @param model
	 * @return page de modification
	 */
	@GetMapping("/posts/{postId}/comments/{commentId}/update")
	public String showUpdateForm(@PathVariable("postId") long id, @PathVariable("commentId") long commentId,
			Model model) {

		Comment comment = commentService.getCommentById(commentId);
		model.addAttribute("updateCommentForm", comment);

		return "comments/updateCommentForm";
	}

	/**
	 * Supprimer un commentaire
	 * @param id
	 * @param commentId
	 * @param redirectAttributes
	 * @return page read d'un post
	 */
	@PostMapping("/posts/{postId}/comments/{commentId}/delete")
	public String delete(@PathVariable("postId") long id, @PathVariable("commentId") long commentId,
			final RedirectAttributes redirectAttributes) {

		commentService.deleteComment(commentId);

		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Commentaire supprimé !");

		return "redirect:/posts/{postId}";
	}

	/**
	 * Méthode service REST pour afficher les commentaires d'un post
	 * @param postId
	 * @return
	 */
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<Collection<Comment>> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
		return new ResponseEntity<Collection<Comment>>(commentService.getCommentsByPostId(postId), HttpStatus.FOUND);
	}

	/**
	 * Méthode service REST pour ajouter un commentaire à un post
	 * @param postId
	 * @param comment
	 * @return
	 */
	@PostMapping("/post/{postId}/comment")
	@Transactional // Gérer les transactions
	public ResponseEntity<Comment> createComment(@PathVariable(value = "postId") Long postId,
			@Valid @RequestBody Comment comment) {
		return new ResponseEntity<Comment>(commentService.saveOrUpdateComment(postId, comment), HttpStatus.CREATED);
	}

	/**
	 * Méthode service REST pour mettre à jour un commentaire
	 * @param postId
	 * @param commentId
	 * @param commentRequest
	 * @return
	 */
	@PutMapping("/post/{postId}/comments/{commentId}")
	@Transactional // Gérer les transactions
	public ResponseEntity<Comment> updateComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId, @Valid @RequestBody Comment commentRequest) {
		if (!commentService.existsById(commentId)) {
			throw new BusinessResourceException("Commentaire inexistant",
					"Le commentaire d'ID : " + commentId + " n'existe pas!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Comment comment = commentService.getCommentById(commentId);
		comment.setBody(commentRequest.getBody());
		return new ResponseEntity<Comment>(commentService.saveOrUpdateComment(postId, comment), HttpStatus.CREATED);
	}

	/**
	 * Méthode service REST pour supprimer un commentaire
	 * @param postId
	 * @param commentId
	 * @return
	 */
	@DeleteMapping("/post/{postId}/comments/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {
		if (!commentService.existsById(commentId)) {
			throw new BusinessResourceException("Commentaire inexistant",
					"Le commentaire d'ID : " + commentId + " n'existe pas!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		commentService.deleteComment(commentId);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

}
