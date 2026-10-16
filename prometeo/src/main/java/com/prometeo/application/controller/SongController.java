package com.prometeo.application.controller;

import com.prometeo.application.entity.Song;
import com.prometeo.application.service.SongService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<Song> getSongs() {
        System.out.println("[GET] /api/songs - Consultando todas las canciones (máximo 100)");

        return songService.findAll()
                .stream()
                .limit(100)
                .toList();
    }

    @GetMapping("/{artist}/{songName}")
    public Song getSong(
            @PathVariable String artist,
            @PathVariable String songName
    ) {
        System.out.println("[GET] /api/songs/" + artist + "/" + songName
                + " - Buscando canción");

        return songService.findById(songName, artist);
    }

    @PostMapping
    public Song createSong(@RequestBody Song song) {
        System.out.println("[POST] /api/songs - Creando canción: " + song);

        return songService.save(song);
    }

    @DeleteMapping("/{artist}/{songName}")
    public void deleteSong(
            @PathVariable String artist,
            @PathVariable String songName
    ) {
        System.out.println("[DELETE] /api/songs/" + artist + "/" + songName
                + " - Eliminando canción");

        songService.delete(songName, artist);
    }

    @GetMapping("/similar")
    public List<SongService.SimilarSongDTO> getSimilarSongs(
            @RequestParam String artist,
            @RequestParam String songName
    ) {
        System.out.println("[GET] /api/songs/similar"
                + " - Buscando canciones similares"
                + " | Artista: " + artist
                + " | Canción: " + songName);

        List<SongService.SimilarSongDTO> songs =
                songService.findSimilarSongs(songName, artist);

        int maxSize = 100;

        songs.stream()
                .limit(maxSize)
                .forEach(System.out::println);

        System.out.println("[GET] /api/songs/similar"
                + " - Resultados encontrados: " + songs.size());

        return songs.stream()
                .limit(maxSize)
                .toList();
    }
}