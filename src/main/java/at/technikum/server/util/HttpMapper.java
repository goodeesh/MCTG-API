package at.technikum.server.util;

import at.technikum.server.http.HttpMethod;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// THOUGHT: Maybe divide the HttpMatter into two classes (single responsibility)
// THOUGHT: Dont use static methods (non-static is better for testing)
public class HttpMapper {

  /* public static Request toRequestObject(String httpRequest) {
    Request request = new Request();
  
    if (httpRequest.indexOf("Authorization:") != -1) {
      request.setAuthorization(
        httpRequest.substring(
          httpRequest.indexOf("Authorization:") + 15,
          httpRequest.indexOf("mtcgToken") + 9
        )
      );
    }
    request.setMethod(getHttpMethod(httpRequest));
    request.setRoute(getRoute(httpRequest));
    request.setHost(getHttpHeader("Host", httpRequest));
  
    String contentLengthHeader = getHttpHeader("Content-Length", httpRequest);
    if (null == contentLengthHeader) {
      return request;
    }
  
    int contentLength = Integer.parseInt(contentLengthHeader);
    request.setContentLength(contentLength);
  
    if (0 == contentLength) {
      return request;
    }
  
    // Parse and convert keys in the body to lowercase
    String body = httpRequest.substring(httpRequest.length() - contentLength);
  
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode tree = objectMapper.readTree(body);
      ObjectNode newObject = objectMapper.createObjectNode();
      // Iterate through the keys and values of the original JSON
      tree
        .fields()
        .forEachRemaining(entry -> {
          // Convert the key to lowercase and add it to the new JSON object
          newObject.set(entry.getKey().toLowerCase(), entry.getValue());
        });
      String modifiedBody = objectMapper.writeValueAsString(newObject);
      request.setBody(modifiedBody);
    } catch (Exception e) {
      e.printStackTrace();
    }
  
    return request;
  } */
  public static Request toRequestObject(String httpRequest) {
    Request request = new Request();

    if (httpRequest.indexOf("Authorization:") != -1) {
      request.setAuthorization(
        httpRequest.substring(
          httpRequest.indexOf("Authorization:") + 15,
          httpRequest.indexOf("mtcgToken") + 9
        )
      );
    }
    request.setMethod(getHttpMethod(httpRequest));
    request.setRoute(getRoute(httpRequest));
    request.setHost(getHttpHeader("Host", httpRequest));

    String contentLengthHeader = getHttpHeader("Content-Length", httpRequest);
    if (null == contentLengthHeader) {
      return request;
    }

    int contentLength = Integer.parseInt(contentLengthHeader);
    request.setContentLength(contentLength);

    if (0 == contentLength) {
      return request;
    }

    String body = httpRequest.substring(httpRequest.length() - contentLength);

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode tree = objectMapper.readTree(body);

      if (tree.isArray()) {
        ArrayNode newArray = objectMapper.createArrayNode();

        for (JsonNode element : tree) {
          ObjectNode newObj = objectMapper.createObjectNode();
          element
            .fields()
            .forEachRemaining(entry -> {
              newObj.set(entry.getKey().toLowerCase(), entry.getValue());
            });
          newArray.add(newObj);
        }

        String modifiedBody = objectMapper.writeValueAsString(newArray);
        request.setBody(modifiedBody);
      } else {
        ObjectNode newObject = objectMapper.createObjectNode();
        tree
          .fields()
          .forEachRemaining(entry -> {
            newObject.set(entry.getKey().toLowerCase(), entry.getValue());
          });

        String modifiedBody = objectMapper.writeValueAsString(newObject);
        request.setBody(modifiedBody);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return request;
  }

  public static String toResponseString(Response response) {
    return (
      "HTTP/1.1 " +
      response.getStatusCode() +
      " " +
      response.getStatusMessage() +
      "\r\n" +
      "Content-Type: " +
      response.getContentType() +
      "\r\n" +
      "Content-Length: " +
      response.getBody().length() +
      "\r\n" +
      "\r\n" +
      response.getBody()
    );
  }

  // THOUGHT: Maybe some better place for this logic?
  private static HttpMethod getHttpMethod(String httpRequest) {
    String httpMethod = httpRequest.split(" ")[0];
    // THOUGHT: Use constants instead of hardcoded strings
    return switch (httpMethod) {
      case "GET" -> HttpMethod.GET;
      case "POST" -> HttpMethod.POST;
      case "PUT" -> HttpMethod.PUT;
      case "DELETE" -> HttpMethod.DELETE;
      default -> throw new RuntimeException("No HTTP Method");
    };
  }

  private static String getRoute(String httpRequest) {
    return httpRequest.split(" ")[1];
  }

  private static String getHttpHeader(String header, String httpRequest) {
    Pattern regex = Pattern.compile(
      "^" + header + ":\\s(.+)",
      Pattern.MULTILINE
    );
    Matcher matcher = regex.matcher(httpRequest);

    if (!matcher.find()) {
      return null;
    }

    return matcher.group(1);
  }
}
