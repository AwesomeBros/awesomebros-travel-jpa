package com.tripgg.server.image.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  List<String> uploads(MultipartFile[] files) throws IOException;

  List<String> manageEntityImages(String[] newImageUrls, String entityType, String entityId,
      List<String> existingImageUrls);

}
