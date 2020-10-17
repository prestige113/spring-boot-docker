package ru.aldar.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import ru.aldar.demo.components.ReadXMLFile;

@Service
public class InitService implements IInitService {

  private String FILE_URL = "http://frontend.tanuki.ru/feeds/raiden-delivery-club/";

  @Autowired
  RestTemplate restTemplate;
  @Autowired
  ReadXMLFile readXMLFile;
  @Override
  public void init() {
    File file = null;
    try {
      file = restTemplate.execute(FILE_URL, HttpMethod.GET, null, clientHttpResponse -> {
        if (clientHttpResponse.getStatusCode() != HttpStatus.OK) {
            throw new IOException("Not Download");
        }
        File ret = File.createTempFile("download", ".xml");
        StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
        return ret;
      });
      readXMLFile.parse(file);
    } finally {
      file.deleteOnExit();
    }
  }
}
