package com.books.librarymanagementsystem.util;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.books.librarymanagementsystem.bo.GenericResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LibraryUtil {

  public static final String SUCESS = "Operation Performed Successfully.";

  public static final String FAILED = "Error while performing the operation.";

  /**
   * Format log message.
   *
   * @param message the message
   * @return the string
   */
  public static String logformat(final String message) {

    return logformat(message, null);
  }

  /**
   * Format logs.
   * 
   * @param message
   * @param tenantId
   * @return
   */
  public static String logformat(final String message, final String tenantId) {

    return String.format("[ TENANT_ID:%s ]    %s", tenantId, message);
  }

  /**
   * 
   * @param message
   * @param argumentArray
   * @return
   */
  public static String logFormat(String message, Object[] argumentArray) {

    return (Objects.isNull(argumentArray) ? message
        : MessageFormatter.arrayFormat(message, argumentArray)
            .getMessage());
  }

  /**
   * Build Response for response message and data.
   * 
   * @param message
   * @param data
   * @return
   */
  public static ResponseEntity<GenericResponse> buildSuccessResponse(String message,
      final Object data) {

    message = (StringUtils.isBlank(message) ? SUCESS : message);
    return new ResponseEntity<>(new GenericResponse(HttpStatus.OK.value(), message, data),
        HttpStatus.OK);
  }

  /**
   * Build Response for response code, data and status code.
   * 
   * @param message
   * @param data
   * @param httpStatus
   * @return
   */
  public static ResponseEntity<GenericResponse> buildSuccessResponse(String message, Object data,
      final HttpStatus httpStatus) {

    message = (StringUtils.isBlank(message) ? SUCESS : message);
    return new ResponseEntity<>(new GenericResponse(httpStatus.value(), message, data), httpStatus);
  }

  /**
   * Build Response for response data and status code.
   * 
   * @param data
   * @param httpStatus
   * @return
   */
  public static ResponseEntity<GenericResponse> buildSuccessResponse(Object data,
      final HttpStatus httpStatus) {

    return new ResponseEntity<>(new GenericResponse(httpStatus.value(), SUCESS, data), httpStatus);
  }

  /**
   * Build Response for success message and status code.
   * 
   * @param message
   * @param httpStatus
   * @return
   */
  public static ResponseEntity<GenericResponse> buildSuccessResponse(String message,
      final HttpStatus httpStatus) {

    message = (StringUtils.isBlank(message) ? SUCESS : message);
    return new ResponseEntity<>(new GenericResponse(httpStatus.value(), message), httpStatus);
  }

  /**
   * Build response for failure response.
   * 
   * @param message
   * @return
   */
  public static ResponseEntity<GenericResponse> buildFailureResponse(String message) {

    message = (StringUtils.isBlank(message) ? FAILED : message);
    return new ResponseEntity<>(
        new GenericResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * To convert object to json.
   * 
   * @param object
   * @return
   * @throws JsonProcessingException
   */
  public static String convertObjectToJson(Object object) throws JsonProcessingException {

    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}
