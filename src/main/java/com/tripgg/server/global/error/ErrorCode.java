package com.tripgg.server.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeInterface {
  OK(HttpStatus.OK.value(), 200, "성공"),
  BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청입니다."),
  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러가 발생했습니다."),
  NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null point 에러"),

  DUPLICATED_USERNAME(HttpStatus.CONFLICT.value(), 409, "사용중인 유저아이디입니다."),
  DUPLICATED_EMAIL(HttpStatus.CONFLICT.value(), 409, "이미 사용중인 이메일입니다."),
  FAILD_TO_SIGN_UP(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "회원가입에 실패했습니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "존재하지 않는 유저입니다."),
  FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED.value(), 401, "유저아이디 혹은 비밀번호가 맞지 않습니다."),

  ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), 401, "액세스 토큰이 만료되었습니다."),
  INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), 401, "유효하지 않은 토큰입니다."),
  REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), 401, "리프레시 토큰이 없습니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), 401, "유효하지 않은 리프레시 토큰입니다."),

  CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "존재하지 않는 카테고리입니다."),

  COUNTRY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "존재하지 않는 국가입니다."),
  CITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "존재하지 않는 도시입니다."),
  DISTRICT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "존재하지 않는 지역입니다."),
  LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "존재하지 않는 장소입니다."),

  POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "존재하지 않는 게시글입니다."),

  IMAGE_FILES_MOVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "이미지 파일 이동 중 오류 발생"),
  IMAGE_TEMP_FILE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "임시 이미지 파일을 찾을 수 없습니다."),
  FAILD_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), 401, "인증이 필요합니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN.value(), 403, "접근이 금지되었습니다."),
  IMAGE_PROCESSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "이미지 처리에 실패했습니다."),
  FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "파일 업로드에 실패했습니다."),
  NO_FILES_UPLOADED(HttpStatus.BAD_REQUEST.value(), 400, "업로드할 파일이 없습니다."),
  ;

  private final Integer httpStatusCode;
  private final Integer code;
  private final String message;
}
