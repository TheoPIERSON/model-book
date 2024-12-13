package com.masphoto.masphoto.service;

import com.masphoto.masphoto.entities.Pictures;
import com.masphoto.masphoto.repositories.PicturesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PicturesService {
    private final PicturesRepository picturesRepository;

    public PicturesService(PicturesRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }

    public Pictures savePicture(MultipartFile file, String title) throws IOException {
        Pictures picture = new Pictures();
        picture.setTitle(title);
        picture.setImageData(file.getBytes()); // Convertit le fichier en tableau de bytes
        return picturesRepository.save(picture);
    }

    public List<Pictures> getAllPictures() {
        return picturesRepository.findAll();
    }

    public Pictures getPictureById(Long id) {
        return picturesRepository.findById(id).orElse(null);
    }
}
