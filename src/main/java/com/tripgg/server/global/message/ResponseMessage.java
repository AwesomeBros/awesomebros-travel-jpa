package com.tripgg.server.global.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage implements ResponseMessageInterface {
  CREATE_POST_SUCCESS("게시글이 성공적으로 등록되었습니다."),
  ;

  private final String message;
}
