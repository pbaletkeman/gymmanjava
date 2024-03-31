package ca.letkeman.gymmanjava.service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  public void init();
  public String save(MultipartFile file);

  public Resource load(String fileName);

  public boolean deleteAll();

  public Stream<Path> loadAll();

  public boolean delete(String fileName);

  public List<String> deleteMany(List<String> fileNames);

  public Path getRoot();

  public void setRoot(Path path);
}
