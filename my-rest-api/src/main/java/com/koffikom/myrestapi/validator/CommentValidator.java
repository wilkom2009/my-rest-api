package com.koffikom.myrestapi.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.koffikom.myrestapi.model.Comment;

/**
 * Gestion des Validators pour le model Comment
 * @author Koffi
 *
 */
@Component
public class CommentValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Comment.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "NotEmpty.comment.body");
	}

}