package ca.letkeman.gymmanjava.model;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class FileInfo {
  private String name;
  private String url;
  private Long size;
  private String humanSize;
  private LocalDateTime createDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getHumanSize() {
    return humanSize;
  }

  public void setHumanSize(String humanSize) {
    this.humanSize = humanSize;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public FileInfo() {
  }

  public FileInfo(String name, String url, Long size, String humanSize, LocalDateTime createDate) {
    this.name = name;
    this.url = url;
    this.size = size;
    this.humanSize = humanSize;
    this.createDate = createDate;
  }

  public static String humanSize(long size){
    String returnValue = "0";
    final long ONE_MB = 1048576L;
    final long ONE_KB = 1024L;
    if (size > ONE_MB){
      returnValue = new DecimalFormat("0.00").format( (double) size / ONE_MB) + " mb";
    } else if (size > ONE_KB) {
      returnValue = new DecimalFormat("0.00").format( (double) size / ONE_KB) + " kb";
    } else {
      returnValue = size + " bytes";
    }
    return returnValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileInfo fileInfo = (FileInfo) o;
    return Objects.equals(getName(), fileInfo.getName()) && Objects.equals(getUrl(),
        fileInfo.getUrl()) && Objects.equals(getSize(), fileInfo.getSize()) && Objects.equals(
        getHumanSize(), fileInfo.getHumanSize()) && Objects.equals(getCreateDate(), fileInfo.getCreateDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getUrl(), getSize(), getHumanSize(), getCreateDate());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FileInfo.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("url='" + url + "'")
        .add("size=" + size)
        .add("humanSize='" + humanSize + "'")
        .add("createDate=" + createDate)
        .toString();
  }
}
