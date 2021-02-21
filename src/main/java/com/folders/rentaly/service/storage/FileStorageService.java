package com.folders.rentaly.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.IndexOutOfBoundsException;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("storageService")
public class FileStorageService implements StorageService {

	@Value("${storage.file.sizelimit}")
	public final long SIZELIMIT = 10000;
	@Value("${storage.file.directory}")
    public final String DIRECTORY = "pictures";
	public final Set<String> ALLOWEDFILETYPES;
	
	public FileStorageService(@Value("${storage.file.filetypes}") List<String> filetypes) {
		ALLOWEDFILETYPES = new HashSet<String>(filetypes);
	}
	
	public void save(MultipartFile file) throws FileUploadException, FileSizeLimitExceededException, IOException {
		if (ALLOWEDFILETYPES.contains(file.getContentType())) {
			throw new FileUploadException(String.format("Wrong file type, %s not allowed", file.getContentType()));
		}
		if (file.getSize() > SIZELIMIT) {
			throw new FileSizeLimitExceededException(
					"Trying to upload a file that is too big",
					file.getSize(), 
					SIZELIMIT
			);
		}
		log.info(file.getContentType());
		Path updir = Paths.get(DIRECTORY);
		if (!Files.exists(updir)) {
			Files.createDirectory(updir);
		}
		
		InputStream imgstream = file.getInputStream();
		Path filepath = updir.resolve(file.getName());
		Files.copy(imgstream, filepath, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public void delete(String filename) throws IOException {
		Path updir = Paths.get(DIRECTORY);
		Path filepath = updir.resolve(filename);
		Files.delete(filepath);
	}

	public Path getOneById(Integer id) throws IOException {
		try {
			return getAllById(id).get(0);
		}
		catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public List<Path> getAllById(Integer id) throws IOException {
        Path updir = Paths.get(DIRECTORY, id.toString());
		List<Path> result;
		try (Stream<Path> walk = Files.walk(updir)) {
			result = walk.filter(Files::isRegularFile)
						.collect(Collectors.toList());
		}
		return result;
    }
}
