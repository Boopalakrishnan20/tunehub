package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tunehub.demo.entities.Song;
import com.tunehub.demo.entities.Users;
import com.tunehub.demo.service.SongService;
import com.tunehub.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UsersService service;
	
	@Autowired
	SongService songService;

	@PostMapping("/registration")
	public String addUser(@ModelAttribute Users user) {

		boolean userStatus = service.emailExists(user.getEmail());
		if (userStatus == false) {
			service.addUser(user);
			System.out.println("User Added!");
		} else {
			System.out.println("User already exists");
		}
		return "Customer";

	}

	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, Model model) {
		if (service.validateUser(email, password) == true) {
			String role = service.getRole(email);
			session.setAttribute("email", email);
			if (role.equals("admin")) {
				return "admin";
			} else {
				Users user = service.getUser(email);
				boolean userStatus =  user.isPremium();
				List<Song> songList = songService.viewSong();
				model.addAttribute("songs", songList);
				model.addAttribute("isPremium", userStatus);
				return "customer";
			}
		} else {
			return "login";
		}

	}

	

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

}
