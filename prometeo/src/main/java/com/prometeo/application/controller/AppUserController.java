package com.prometeo.application.controller;

import com.prometeo.application.entity.AppUser;
import com.prometeo.application.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getUsers() {
        System.out.println("[GET] /api/users - Consultando todos los usuarios");

        return appUserService.findAll();
    }

    @GetMapping("/{username}")
    public AppUser getUser(
            @PathVariable String username
    ) {
        System.out.println("[GET] /api/users/" + username
                + " - Buscando usuario");

        return appUserService.findByUsername(username);
    }

    @PostMapping
    public AppUser createUser(
            @RequestBody AppUser user
    ) {
        System.out.println("[POST] /api/users - Creando usuario: " + user);

        return appUserService.save(user);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(
            @PathVariable String username
    ) {
        System.out.println("[DELETE] /api/users/" + username
                + " - Eliminando usuario");

        appUserService.delete(username);
    }
}