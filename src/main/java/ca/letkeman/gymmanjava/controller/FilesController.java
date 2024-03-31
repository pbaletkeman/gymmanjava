package ca.letkeman.gymmanjava.controller;

import ca.letkeman.gymmanjava.message.ResponseMessage;
import ca.letkeman.gymmanjava.model.FileInfo;
import ca.letkeman.gymmanjava.service.FileStorageService;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
public class FilesController {

  @Autowired
  FileStorageService fileStorageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
    String message;
    try {
      message = "success: " + fileStorageService.save(file);
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getFileListing() {
    List<FileInfo> fileInfos = fileStorageService.loadAll().map(path -> {
      long fileSize = 0;
      try {
        Path rootPath = fileStorageService.getRoot();
        fileSize = Files.size(Paths.get(rootPath.toString() + FileSystems.getDefault().getSeparator() + path.getFileName().toString()));

      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
      String fileName = path.getFileName().toString();
      String url = MvcUriComponentsBuilder.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(fileName, url, fileSize, FileInfo.humanSize(fileSize), LocalDateTime.now());
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }


  @GetMapping("/files/{fileName:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String fileName){
    Resource resource = fileStorageService.load(fileName);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + resource.getFilename() + "\"").body(resource);
  }
}
