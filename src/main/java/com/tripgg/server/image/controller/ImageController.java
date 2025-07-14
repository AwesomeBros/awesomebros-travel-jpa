package com.tripgg.server.image.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tripgg.server.global.api.Api;
import com.tripgg.server.image.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @PostMapping("images")
  public Api<List<String>> uploads(@RequestParam("files") MultipartFile[] files) throws IOException {
    List<String> url = imageService.uploads(files);
    return Api.OK(url);
  }
}