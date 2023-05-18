package ch.hearc.SaphirLion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.service.impl.MediaService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@SpringBootApplication
public class SaphirLionApplication {

	@Value("${server.port}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(SaphirLionApplication.class, args);
	}

	/**
	 * Listener jms avec conversion json
	 * @param jsonMessage
	 * @throws JMSException
	 */
	@JmsListener(destination = "${spring.activemq.media-json-queue}")
    public void readInprogressJsonMessage(final Message jsonMessage) throws JMSException {
        
        System.out.println("Received message " + jsonMessage);
        
        if(!(jsonMessage instanceof TextMessage)) {
			return;
		}

		TextMessage textMessage = (TextMessage)jsonMessage;
		String jsonString = textMessage.getText();

		System.out.println("--------------------");
		System.out.println("Received: " + jsonString);
		System.out.println("--------------------");

        // URL to send request to
        String url = "http://localhost:" + port + "/api/media";

        // Prepare headers (application/json)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

		// Prepare request body (JSON)
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
		HttpStatusCode statusCode = null;
		String responseBody = null;

		// Prepare RestTemplate
        RestTemplate restTemplate = new RestTemplate();
		
		// Send request and get response as String (JSON) and status code
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			statusCode = response.getStatusCode();
        	responseBody = response.getBody();
		} catch (HttpClientErrorException ex) {
			statusCode = ex.getStatusCode();
			responseBody = ex.getResponseBodyAsString();
		}

        // Print status code and response body
		System.out.println("--------------------");
		System.out.println("<<< CODE: " + statusCode + " >>>");
        System.out.println(responseBody);
		System.out.println("--------------------");
    }
}
