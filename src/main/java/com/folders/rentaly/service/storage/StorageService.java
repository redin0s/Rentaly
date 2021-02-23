package com.folders.rentaly.service.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void save(MultipartFile file, Integer id) throws FileUploadException, FileSizeLimitExceededException, IOException;
    void delete(String filename) throws IOException;
    List<Path> getAllById(Integer id);
    void saveAll(MultipartFile[] files, Integer id);
}