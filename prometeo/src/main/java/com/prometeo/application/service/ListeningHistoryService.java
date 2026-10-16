package com.prometeo.application.service;

import com.prometeo.application.entity.AppUser;
import com.prometeo.application.entity.ListeningHistory;
import com.prometeo.application.entity.Song;
import com.prometeo.application.repository.AppUserRepository;
import com.prometeo.application.repository.ListeningHistoryRepository;
import com.prometeo.application.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListeningHistoryService {

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final AppUserRepository appUserRepository;
    private final SongRepository songRepository;

    public ListeningHistoryService(
            ListeningHistoryRepository listeningHistoryRepository,
            AppUserRepository appUserRepository,
            SongRepository songRepository
    ) {
        this.listeningHistoryRepository = listeningHistoryRepository;
        this.appUserRepository = appUserRepository;
        this.songRepository = songRepository;
    }

    public List<ListeningHistory> findAll() {
        return listeningHistoryRepository.findAll();
    }

    public ListeningHistory findById(Long id) {
        return listeningHistoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Listening history not found")
                );
    }

    public List<ListeningHistory> findByUsername(String username) {
        return listeningHistoryRepository.findByUserUsername(username);
    }

    public List<ListeningHistory> findBySong(
            String songName,
            String artist
    ) {
        return listeningHistoryRepository
                .findBySongIdSongNameAndSongIdSongArtist(
                        songName,
                        artist
                );
    }

    @Transactional
    public ListeningHistory save(ListeningHistory history) {

        if (history.getUser() == null
                || history.getUser().getUsername() == null) {
            throw new IllegalArgumentException(
                    "El historial debe tener un usuario"
            );
        }

        String username = history.getUser().getUsername();

        AppUser user = appUserRepository.findById(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe el usuario: " + username
                        )
                );

        if (history.getSong() == null
                || history.getSong().getId() == null) {
            throw new IllegalArgumentException(
                    "El historial debe tener una canción"
            );
        }

        Song song = songRepository.findById(history.getSong().getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "La canción indicada no existe"
                        )
                );

        history.setUser(user);
        history.setSong(song);

        return listeningHistoryRepository.save(history);
    }

    public void delete(Long id) {
        listeningHistoryRepository.deleteById(id);
    }
}