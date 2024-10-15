package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.demo.entities.Playlist;
import com.tunehub.demo.entities.Song;
import com.tunehub.demo.service.PlaylistService;
import com.tunehub.demo.service.SongService;


@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	@Autowired
	PlaylistService playlistService;

	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songList = songService.viewSong();
		model.addAttribute("songs", songList);

		return "createPlaylist";
	}

	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		
		//Updating Playlists
		playlistService.addPlaylist(playlist);
		
		//Updating Song table
		List<Song> songList =  playlist.getSongs();
		for(Song s:songList) {
			s.getPlaylist().add(playlist);
			songService.updateSong(s);
		}
		return "admin";
	}
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {
		List<Playlist> allPlaylist = playlistService.fetchAllPlaylist();
		model.addAttribute("allPlaylist", allPlaylist);
		return "displayPlaylist";
	}
	

}
