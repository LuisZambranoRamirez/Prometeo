package com.prometeo.application.controller;

import com.prometeo.application.entity.DeviceToken;
import com.prometeo.application.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/register-token")
    public ResponseEntity<DeviceToken> registerToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String userUsername = request.get("userUsername");
        String platform = request.get("platform");

        System.out.println("[POST] /api/notifications/register-token - Usuario: "
                + userUsername + ", Plataforma: " + platform);

        DeviceToken deviceToken = notificationService.registerToken(token, userUsername, platform);
        return ResponseEntity.ok(deviceToken);
    }

    @DeleteMapping("/unregister-token")
    public ResponseEntity<Void> unregisterToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        System.out.println("[DELETE] /api/notifications/unregister-token - Eliminando token");

        notificationService.unregisterToken(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tokens/{userUsername}")
    public List<DeviceToken> getTokensByUser(@PathVariable String userUsername) {
        System.out.println("[GET] /api/notifications/tokens/" + userUsername
                + " - Consultando tokens");

        return notificationService.getTokensByUser(userUsername);
    }

    @PostMapping("/send-test")
    public ResponseEntity<String> sendTest(@RequestBody Map<String, String> request) {
        String userUsername = request.get("userUsername");

        System.out.println("[POST] /api/notifications/send-test - Enviando prueba a: "
                + userUsername);

        notificationService.sendTestNotification(userUsername);
        return ResponseEntity.ok("Notificación de prueba enviada a " + userUsername);
    }

    @PostMapping("/send-song")
    public ResponseEntity<String> sendSongNotification(@RequestBody Map<String, String> request) {
        String userUsername = request.get("userUsername");
        String songTitle = request.get("songTitle");
        String artist = request.get("artist");

        System.out.println("[POST] /api/notifications/send-song - Canción: "
                + songTitle + " - " + artist + " para " + userUsername);

        notificationService.sendSongNotification(userUsername, songTitle, artist);
        return ResponseEntity.ok("Notificación de canción enviada a " + userUsername);
    }
}
