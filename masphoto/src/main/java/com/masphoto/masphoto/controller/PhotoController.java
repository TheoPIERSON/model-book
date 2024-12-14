package com.masphoto.masphoto.controller;

import com.masphoto.masphoto.service.SupabaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private SupabaseStorageService storageService;

    // Endpoint pour uploader une image
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String url = storageService.uploadImage(file.getOriginalFilename(), file.getBytes(), file.getContentType());
            return ResponseEntity.ok("Image uploadée avec succès : " + url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }

    // Endpoint pour supprimer une image
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deletePhoto(@PathVariable String fileName) {
        try {
            storageService.deleteImage(fileName);
            return ResponseEntity.ok("Image supprimée avec succès : " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }
}
