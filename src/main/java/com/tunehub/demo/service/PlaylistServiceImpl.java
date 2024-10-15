package com.tunehub.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.demo.entities.Playlist;
import com.tunehub.demo.repositories.PlaylistRepository;

@Service
public class PlaylistServiceImpl implements PlaylistService {
	@Autowired
	PlaylistRepository repo;

	@Override
	public void addPlaylist(Playlist playlist) {
		repo.save(playlist);
	}

	@Override
	public List<Playlist> fetchAllPlaylist() {
		return repo.findAll();
	}

}
