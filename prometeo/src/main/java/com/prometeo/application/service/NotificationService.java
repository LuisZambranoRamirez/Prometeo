package com.prometeo.application.service;

import com.prometeo.application.entity.DeviceToken;
import com.prometeo.application.repository.DeviceTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final DeviceTokenRepository deviceTokenRepository;

    public NotificationService(DeviceTokenRepository deviceTokenRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
    }

    public DeviceToken registerToken(String token, String userUsername, String platform) {
        return deviceTokenRepository.findByToken(token)
                .map(existing -> {
                    existing.setUserUsername(userUsername);
                    existing.setPlatform(platform);
                    existing.setLastUsedAt(LocalDateTime.now());
                    return deviceTokenRepository.save(existing);
                })
                .orElseGet(() -> {
                    DeviceToken deviceToken = new DeviceToken();
                    deviceToken.setToken(token);
                    deviceToken.setUserUsername(userUsername);
                    deviceToken.setPlatform(platform);
                    return deviceTokenRepository.save(deviceToken);
                });
    }

    public void unregisterToken(String token) {
        deviceTokenRepository.deleteByToken(token);
    }

    public List<DeviceToken> getTokensByUser(String userUsername) {
        return deviceTokenRepository.findByUserUsername(userUsername);
    }

    public void sendTestNotification(String userUsername) {
        List<DeviceToken> tokens = deviceTokenRepository.findByUserUsername(userUsername);
        System.out.println("[Notification] Enviando notificación de prueba a: " + userUsername);
        System.out.println("[Notification] Tokens encontrados: " + tokens.size());

        for (DeviceToken deviceToken : tokens) {
            System.out.println("[Notification] Token: " + deviceToken.getToken()
                    + " | Plataforma: " + deviceToken.getPlatform());
        }
    }

    public void sendSongNotification(String userUsername, String songTitle, String artist) {
        List<DeviceToken> tokens = deviceTokenRepository.findByUserUsername(userUsername);
        System.out.println("[Notification] Canción detectada para " + userUsername
                + ": " + songTitle + " - " + artist);
        System.out.println("[Notification] Tokens disponibles: " + tokens.size());
    }
}
