package com.prometeo.application.controller;

import com.prometeo.application.entity.ListeningHistory;
import com.prometeo.application.service.ListeningHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listening-history")
public class ListeningHistoryController {

    private final ListeningHistoryService listeningHistoryService;

    public ListeningHistoryController(
            ListeningHistoryService listeningHistoryService
    ) {
        this.listeningHistoryService = listeningHistoryService;
    }

    @GetMapping
    public List<ListeningHistory> getHistory() {
        System.out.println("[GET] /api/listening-history - Consultando todo el historial");

        return listeningHistoryService.findAll();
    }

    @GetMapping("/{id}")
    public ListeningHistory getHistoryById(
            @PathVariable Long id
    ) {
        System.out.println("[GET] /api/listening-history/" + id
                + " - Consultando historial por ID");

        return listeningHistoryService.findById(id);
    }

    @GetMapping("/user/{username}")
    public List<ListeningHistory> getUserHistory(
            @PathVariable String username
    ) {
        System.out.println("[GET] /api/listening-history/user/" + username
                + " - Consultando historial del usuario");

        return listeningHistoryService.findByUsername(username);
    }

    @GetMapping("/song/{artist}/{songName}")
    public List<ListeningHistory> getSongHistory(
            @PathVariable String artist,
            @PathVariable String songName
    ) {
        System.out.println("[GET] /api/listening-history/song/"
                + artist + "/" + songName
                + " - Consultando historial de la canción");

        return listeningHistoryService.findBySong(
                songName,
                artist
        );
    }

    @PostMapping
    public ListeningHistory createHistory(
            @RequestBody ListeningHistory history
    ) {
        System.out.println("[POST] /api/listening-history"
                + " - Creando registro de historial: " + history);

        return listeningHistoryService.save(history);
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(
            @PathVariable Long id
    ) {
        System.out.println("[DELETE] /api/listening-history/" + id
                + " - Eliminando registro del historial");

        listeningHistoryService.delete(id);
    }
}