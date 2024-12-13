package com.masphoto.masphoto.controller;

import com.masphoto.masphoto.entities.Pictures;
import com.masphoto.masphoto.service.PicturesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pictures")
public class PicturesController {
    private final PicturesService picturesService;

    public PicturesController(PicturesService picturesService) {
        this.picturesService = picturesService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title
    ) {
        try {
            picturesService.savePicture(file, title);
            return ResponseEntity.status(HttpStatus.CREATED).body("Picture uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload picture.");
        }
    }

    @GetMapping("/all-pictures")
    public ResponseEntity<List<Pictures>> getAllPictures() {
        return ResponseEntity.ok(picturesService.getAllPictures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pictures> getPictureById(@PathVariable Long id) {
        Pictures picture = picturesService.getPictureById(id);
        return picture != null ? ResponseEntity.ok(picture) : ResponseEntity.notFound().build();
    }
}

