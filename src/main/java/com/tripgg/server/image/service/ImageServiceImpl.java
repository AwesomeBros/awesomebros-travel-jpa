package com.tripgg.server.image.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tripgg.server.global.constants.AppConstants;
import com.tripgg.server.global.error.ErrorCode;
import com.tripgg.server.global.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
  private final AppConstants appConstants;
  private final String UPLOAD_BASE_DIR = System.getProperty("user.dir");

  Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

  @Override
  public List<String> uploads(MultipartFile[] files) throws IOException {
    String UPLOAD_SUB_DIR = appConstants.getFilePath() + "temp/";
    String SERVER_URL = appConstants.getServerUrl();

    Path uploadPath = Paths.get(UPLOAD_BASE_DIR + UPLOAD_SUB_DIR);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    List<String> uploadedUrls = new ArrayList<>();
    for (int i = 0; i < files.length; i++) {
      MultipartFile file = files[i];
      if (file.isEmpty()) {
        // LOGGER.warn("파일 없음, 스킵");
        continue;
      }

      try {
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String newFileName = timestamp + "_" + (i + 1) + "_" + originalFilename;
        Path filePath = uploadPath.resolve(newFileName);
        file.transferTo(filePath.toFile());
        String fileUrl = SERVER_URL + UPLOAD_SUB_DIR + newFileName;
        uploadedUrls.add(fileUrl);
        // LOGGER.info("파일 업로드 완료: {}", fileUrl);
      } catch (Exception e) {
        // LOGGER.error("파일 업로드 실패: {}", file.getOriginalFilename(), e);
        cleanupUploadedFiles(uploadedUrls);
        throw new ApiException(ErrorCode.FILE_UPLOAD_FAILED);
      }
    }

    if (uploadedUrls.isEmpty()) {
      throw new ApiException(ErrorCode.NO_FILES_UPLOADED);
    }

    // LOGGER.info("총 {} 개 파일 업로드 완료", uploadedUrls.size());
    return uploadedUrls;
  }

  private void cleanupUploadedFiles(List<String> uploadedUrls) {
    for (String url : uploadedUrls) {
      try {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String filePath = UPLOAD_BASE_DIR + appConstants.getFilePath() + "temp/" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
          boolean deleted = file.delete();
          if (deleted) {
            LOGGER.info("정리 완료: {}", fileName);
          }
        }
      } catch (Exception e) {
        LOGGER.warn("정리 실패: {}", url, e);
      }
    }
  }

  @Override
  public List<String> manageEntityImages(String[] newImageUrls, String entityType, String entityId,
      List<String> existingImageUrls) {
    try {
      if (existingImageUrls != null && !existingImageUrls.isEmpty()) {
        deleteExistingImages(existingImageUrls, entityType, entityId);
      }
      List<String> finalImageUrls = new ArrayList<>();
      for (String imageUrl : newImageUrls) {
        if (imageUrl.contains("/uploads/temp/")) {
          LOGGER.info("Processing temp image: {}", imageUrl);
          String movedUrl = moveImageToEntityFolder(imageUrl, entityType, entityId);
          if (movedUrl != null) {
            finalImageUrls.add(movedUrl);
          }
        } else {
          finalImageUrls.add(imageUrl);
        }
      }

      // LOGGER.info("Successfully managed {} images for {} {}",
      // finalImageUrls.size(), entityType, entityId);
      return finalImageUrls;

    } catch (Exception e) {
      // LOGGER.error("Error managing images for {} {}: {}", entityType, entityId,
      // e.getMessage());
      throw new ApiException(ErrorCode.IMAGE_PROCESSING_FAILED);
    }
  }

  private void deleteExistingImages(List<String> existingImageUrls, String entityType, String entityId) {
    for (String imageUrl : existingImageUrls) {
      try {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String filePath = UPLOAD_BASE_DIR + appConstants.getFilePath() + "images/" + entityType + "/" + entityId
            + "/" + fileName;
        // LOGGER.info("삭제 시도 파일명: {}", fileName);
        // LOGGER.info("삭제 시도 파일경로: {}", filePath);
        File file = new File(filePath);
        if (file.exists()) {
          boolean deleted = file.delete();
          if (deleted) {
            LOGGER.info("삭제된 기존파일: {}", fileName);
          } else {
            LOGGER.warn("삭제실패한 기존파일: {}", fileName);
          }
        }
      } catch (Exception e) {
        LOGGER.error("파일 삭제 에러 {}: {}", imageUrl, e.getMessage());
      }
    }
  }

  private String moveImageToEntityFolder(String tempImageUrl, String entityType, String entityId) {
    try {
      String fileName = tempImageUrl.substring(tempImageUrl.lastIndexOf("/") + 1);
      String tempPath = UPLOAD_BASE_DIR + appConstants.getFilePath() + "temp/" + fileName;
      String entityFolderPath = UPLOAD_BASE_DIR + appConstants.getFilePath() + "images/" + entityType + "/"
          + entityId + "/";
      String newPath = entityFolderPath + fileName;
      // LOGGER.info("파일 이동 시도:");
      // LOGGER.info("- 소스 경로: {}", tempPath);
      // LOGGER.info("- 목적 경로: {}", newPath);
      File entityFolder = new File(entityFolderPath);
      if (!entityFolder.exists()) {
        boolean created = entityFolder.mkdirs();
        if (!created) {
          // LOGGER.error("Failed to create {} folder: {}", entityType, entityFolderPath);
          return null;
        }
        // LOGGER.info("엔터티 폴더 생성: {}", entityFolderPath);
      }

      File tempFile = new File(tempPath);
      File newFile = new File(newPath);

      if (tempFile.exists()) {
        boolean moved = tempFile.renameTo(newFile);
        if (moved) {
          String newImageUrl = appConstants.getServerUrl() + "/uploads/images/" + entityType + "/" + entityId
              + "/" + fileName;
          // LOGGER.info("파일 이동 성공: {} -> {}", tempImageUrl, newImageUrl);
          return newImageUrl;
        } else {
          // LOGGER.error("파일 이동 실패: {} -> {}", tempPath, newPath);
          return null;
        }
      } else {
        // LOGGER.error("Temp file not found: {}", tempPath);
        File tempDir = new File(UPLOAD_BASE_DIR + appConstants.getFilePath() + "temp/");
        if (tempDir.exists()) {
          String[] files = tempDir.list();
          LOGGER.info("temp 디렉토리 내용: {}", files != null ? String.join(", ", files) : "empty");
          LOGGER.info("temp 디렉토리 절대 경로: {}", tempDir.getAbsolutePath());
        } else {
          // LOGGER.error("temp 디렉토리가 존재하지 않음: {}", tempDir.getAbsolutePath());
        }
        return null;
      }
    } catch (Exception e) {
      LOGGER.error("Error moving image: {}", e.getMessage());
      return null;
    }
  }
}
