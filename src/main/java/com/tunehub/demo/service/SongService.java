package com.tunehub.demo.service;

import java.util.List;

import com.tunehub.demo.entities.Song;

public interface SongService {

	public void addSong(Song song);

	public List<Song> viewSong();

	public boolean songExists(String name);

	public void updateSong(Song song);

}
