package com.masphoto.masphoto.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(String fileName, byte[] content) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, content);
        System.out.println("Chemin d'upload : " + uploadPath.toAbsolutePath());

        return filePath.toString(); // Retourne le chemin complet
    }
    public List<String> listAllPhotos() throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            return List.of(); // Si aucun fichier n'existe
        }
        return Files.walk(uploadPath, 1)
                .filter(path -> !Files.isDirectory(path)) // Ignore les dossiers
                .map(path -> path.getFileName().toString()) // Récupère uniquement le nom des fichiers
                .collect(Collectors.toList());
    }

}
