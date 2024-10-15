package com.tunehub.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.demo.entities.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

}
