package at.technikum.server.http;

public class Response {

  private int statusCode;

  private String statusMessage;

  private String contentType;

  private String body;

  public Response(HttpStatus httpStatus, String taskJson) {
    switch (httpStatus) {
      case OK:
        this.statusCode = 200;
        this.statusMessage = "OK";
        this.contentType = (HttpContentType.APPLICATION_JSON).getMimeType();
        this.body = taskJson;
        break;
      case CREATED:
        this.statusCode = 201;
        this.statusMessage = "Created";
        this.contentType = (HttpContentType.APPLICATION_JSON).getMimeType();
        this.body = taskJson;
        break;
      case BAD_REQUEST:
        this.statusCode = 400;
        this.statusMessage = "Bad Request";
        this.contentType = (HttpContentType.TEXT_PLAIN).getMimeType();
        this.body = taskJson;
        break;
      case METHOD_NOT_ALLOWED:
        this.statusCode = 405;
        this.statusMessage = "Method Not Allowed";
        this.contentType = (HttpContentType.TEXT_PLAIN).getMimeType();
        this.body = taskJson;
        break;
      case UNAUTHORIZED:
        this.statusCode = 401;
        this.statusMessage = "Unauthorized";
        this.contentType = (HttpContentType.TEXT_PLAIN).getMimeType();
        this.body = taskJson;
        break;
      case NOT_FOUND:
        this.statusCode = 404;
        this.statusMessage = "Not Found";
        this.contentType = (HttpContentType.TEXT_PLAIN).getMimeType();
        this.body = taskJson;
        break;
      case INTERNAL_SERVER_ERROR:
        this.statusCode = 500;
        this.statusMessage = "Internal Server Error";
        this.contentType = (HttpContentType.TEXT_PLAIN).getMimeType();
        this.body = taskJson;
        break;
      case CONFLICT:
        this.statusCode = 409;
        this.statusMessage = "Conflict";
        this.contentType = (HttpContentType.TEXT_PLAIN).getMimeType();
        this.body = taskJson;
        break;
      case FORBIDDEN:
        this.statusCode = 403;
        this.statusMessage = "Forbidden";
        this.contentType = (HttpContentType.TEXT_PLAIN).getMimeType();
        this.body = taskJson;
        break;
      default:
        break;
    }
  }

  public Response() {}

  public void setStatus(HttpStatus httpStatus) {
    this.statusCode = httpStatus.getCode();
    this.statusMessage = httpStatus.getMessage();
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(HttpContentType httpContentType) {
    this.contentType = httpContentType.getMimeType();
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
