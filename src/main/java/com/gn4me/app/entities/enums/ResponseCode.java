package com.gn4me.app.entities.enums;

/**
*  Define HTTP Status Codes In 100:149, 200:249, 300:349, 400:449, 500:549
*  Define Customs Status Codes In 150:199, 250:299, 350:399, 450:499, 550:599
*/
public enum ResponseCode {
 	
 /** Success **/	
 SUCCESS(200, "Success"),
 CREATED(201, "Created"),

 // Define all custom success response, Start from 250 To 299
 ALREADY_EXIST(250, "Is Already Exist"),
 NOT_EXIST(251, "Is Not Exist"),
 VALID(252, "Is Valid"),
 NOT_VALID(253, "Is Not Valid"),
 TRUSTED(254, "Is Trusted"),
 NOT_TRUSTED(255, "Is Not Trusted"),
 EMPTY_LIST(256,"No Result Found!"),
 
 /** Client errors **/
 BAD_REQUEST(400, "Bad Request"),
 UNAUTHORIZED(401, "Unauthorized"),
 PAYMENT_REQUIRED(402, "Payment Required"),
 FORBIDDEN(403, "Forbidden"),
 NOT_FOUND(404, "Not Found"),
 METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
 REQUEST_TIMEOUT(408, "Request Timeout"),
 UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

 
 // Define all custom User errors request, Start from 450 To 499
 INVALID_AUTH(450, "Invalid username/password supplied"),
 INVALID_TOKEN(451, "Expired or Invalid Token"),
 DUPRICATED_TOKEN(452, "Dupricated token you have to login"),
 UNSUPPORTED_FILE_TYPE(453, "Unsupported File Type"),
 EXCEED_MAX_SIZE(454, "Exceed max size"),
 
 /** Server errors **/
 INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
 NOT_IMPLEMENTED(501, "Not Implemented"),
 
 
 // Define More Detailed Failure Response, Start from 550 To 599
 NO_DATA_SAVED(501, "No Data Saved"),
 GENERAL_FAILURE(555, "General Failure");
 
	
 private int code;
 private String message;

 ResponseCode(int code, String message) {
     this.code = code;
     this.message = message;
 }

 /**
  * Gets the HTTP status code
  * @return the status code number
  */
 public int getCode() {
     return code;
 }

 /**
  * Get the description
  * @return the description of the status code
  */
 public String getMessage() {
     return message;
 }
 
 /**
  * Get the description
  * @return the description of the status code
  */
 public String getMessageWithPrefix(String prefix) {
     return (prefix != null ? " " + prefix : "") + message;
 }

 /**
  * Get the description
  * @return the description of the status code
  */
 public String getMessageWithPostfix(String postfix) {
     return  message + (postfix != null ? " " + postfix : "");
 }
 
}
