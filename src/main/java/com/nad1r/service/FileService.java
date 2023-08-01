package com.nad1r.service;

import com.amazonaws.services.rekognition.model.Video;
import com.nad1r.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final UserService userService;
    private final S3Service s3Service;

    private final List<String> IMAGE_TYPES = Arrays.asList(IMAGE_JPEG.getMimeType(),
            IMAGE_PNG.getMimeType(),

            IMAGE_GIF.getMimeType());

    public void uploadProfileImage(String username, MultipartFile file){
        if(file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file!");
        if(!IMAGE_TYPES.contains(file.getContentType()))
            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");

        User user = userService.findUserByUsername(username);

        Map<String, String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", bucketName, user.getUsername());
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            s3Service.save(path, fileName, file.getInputStream(), Optional.of(metaData));
            userService.updateUserProfileImage(fileName, username);
        }catch (IOException exp){
            throw new IllegalStateException(exp);
        }
    }

    public byte[] downloadProfileImage(String username){
        User user = userService.findUserByUsername(username);
        String path = String.format("%s/%s",
                bucketName,
                user.getUsername());
        return Optional.ofNullable(user.getProfileImageLink())
                .map(key -> s3Service.download(path, key))
                .orElse(new byte[0]);
    }





}
