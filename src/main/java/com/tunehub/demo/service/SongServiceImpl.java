package com.tunehub.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.demo.entities.Song;
import com.tunehub.demo.repositories.SongRepository;

@Service
public class SongServiceImpl implements SongService {
	@Autowired
	SongRepository repo;

	@Override
	public void addSong(Song song) {
		repo.save(song);
	}

	@Override
	public List<Song> viewSong() {
		return repo.findAll();
	}

	@Override
	public boolean songExists(String name) {
		if (repo.findByName(name) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void updateSong(Song song) {
		repo.save(song);
	}

}
