package com.koffikom.myrestapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller par défaut affichant la page d'accueil
 * @author Koffi
 *
 */
@Controller
public class DefaultController {

	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

	@GetMapping(value="/")
	public String index(Model model) {
		logger.debug("Accueil");
		return "redirect:/posts";
	}
}
