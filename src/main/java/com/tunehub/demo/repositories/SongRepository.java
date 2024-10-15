package com.tunehub.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.demo.entities.Song;

public interface SongRepository extends JpaRepository<Song, Integer>{

	public Song findByName(String name);

}
