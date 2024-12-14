package com.masphoto.masphoto.service;

import com.masphoto.masphoto.config.SupabaseConfig;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class SupabaseStorageService {

    private final OkHttpClient client;
    private final String supabaseUrl;
    private final String supabaseKey;
    private final String bucketName = "masphoto-bucket";

    @Autowired
    public SupabaseStorageService(SupabaseConfig supabaseConfig, OkHttpClient client) {
        this.client = client;
        this.supabaseUrl = supabaseConfig.getSupabaseUrl();
        this.supabaseKey = supabaseConfig.getSupabaseKey();
    }

    // Méthode pour télécharger une image
    public String uploadImage(String fileName, byte[] fileData, String contentType) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse(contentType), fileData);

        Request request = new Request.Builder()
                .url(supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .put(body)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Erreur lors de l'upload de l'image : " + response);
        }
        return supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + fileName;
    }

    // Méthode pour supprimer une image
    public void deleteImage(String fileName) throws IOException {
        Request request = new Request.Builder()
                .url(supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName)
                .addHeader("Authorization", "Bearer " + supabaseKey)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Erreur lors de la suppression de l'image : " + response);
        }
    }
}
