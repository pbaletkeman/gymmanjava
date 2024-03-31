package ca.letkeman.gymmanjava.service;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {


  private Path root;

  public FileStorageServiceImpl() {
    root = Paths.get("uploads");
  }

  public FileStorageServiceImpl(Path root) {
    this.root = root;
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("could not initialize folder for upload!");
    }

  }

  @Override
  public String save(MultipartFile file) {
    try {
      Path savedFileName = this.root.resolve(Objects.requireNonNull(file.getOriginalFilename()));
      Files.copy(file.getInputStream(), savedFileName);
      return savedFileName.toString();
    } catch (Exception e){
      if (e instanceof FileAlreadyExistsException){
        throw new RuntimeException("file with name exists.");
      }
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Resource load(String fileName) {
    try {
      Path file = root.resolve(fileName);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()){
        return resource;
      } else {
        throw new RuntimeException("can't read file");
      }
    } catch (MalformedURLException e){
      throw new RuntimeException("error: " + e.getMessage());
    }
  }

  @Override
  public boolean deleteAll() {
    return FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e){
      throw new RuntimeException("couldn't load files");
    }
  }

  @Override
  public boolean delete(String fileName) {
    try {
      return Files.deleteIfExists(root.resolve(fileName));
    } catch (java.io.IOException e){
      throw new RuntimeException("couldn't delete file because: " + e.getMessage());
    }
  }

  @Override
  public List<String> deleteMany(List<String> fileNames) {
    if (fileNames.isEmpty()){
      return new ArrayList<>();
    } else {
      ArrayList<String> problems = new ArrayList<>();
      for (String fileName : fileNames) {
        try {
          if (!Files.deleteIfExists(root.resolve(fileName))) {
            problems.add(fileName);
          }
        } catch (IOException e) {
          problems.add(fileName);
        }
      }
      return problems;
    }
  }

  @Override
  public Path getRoot() {
    return root;
  }

  @Override
  public void setRoot(Path root) {
    this.root = root;
  }
}
