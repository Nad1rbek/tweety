package com.nad1r.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    public void save(String path,
                     String fileName,
                     InputStream inputStream,
                     Optional<Map<String, String>> optionalMetaData){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent((map) -> {
            if(!map.isEmpty()){
                map.forEach(objectMetadata::addUserMetadata);
            }
        });

        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        }catch (AmazonServiceException exp){
            throw new IllegalStateException("Failed to store file s3");
        }
    }

    public byte[] download(String path, String key){

        try {
            S3Object s3Object = amazonS3.getObject(path, key);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            return IOUtils.toByteArray(inputStream);
        } catch (AmazonServiceException | IOException exp) {
            throw new IllegalStateException("Failed to download file to s3", exp);
        }

    }
}
