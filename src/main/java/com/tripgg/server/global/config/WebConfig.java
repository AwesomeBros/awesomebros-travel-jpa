package com.tripgg.server.global.config;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tripgg.server.global.resolver.CurrentUserResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final CurrentUserResolver currentUserResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(currentUserResolver);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String UPLOAD_BASE_DIR = System.getProperty("user.dir");
    String UPLOAD_FILE_SYSTEM_PATH = UPLOAD_BASE_DIR + File.separator + "uploads" + File.separator;
    registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:" + UPLOAD_FILE_SYSTEM_PATH)
        .setCachePeriod(3600)
        .resourceChain(true);
    ;
  }
}