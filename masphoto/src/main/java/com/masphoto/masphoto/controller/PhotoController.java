package com.masphoto.masphoto.controller;

import com.masphoto.masphoto.service.PhotoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/photos")
public class PhotoController {

    private PhotoService photoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String savedFilePath = photoService.saveFile(file.getOriginalFilename(), file.getBytes());
            return ResponseEntity.ok("Fichier sauvegardé avec succès : " + savedFilePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listAllFiles() {
        try {
            List<String> fileNames = photoService.listAllPhotos();
            return ResponseEntity.ok(fileNames);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(List.of("Erreur lors de la récupération des fichiers : " + e.getMessage()));
        }
    }
}
