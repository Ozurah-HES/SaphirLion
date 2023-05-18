package ch.hearc.SaphirLion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.Media;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@SpringBootApplication
public class SaphirLionApplication {

	@Autowired
	ObjectMapper mapper;
	
	public static void main(String[] args) {
		SpringApplication.run(SaphirLionApplication.class, args);
	}

	/**
	 * Listener jms avec conversion json
	 * @param jsonMessage
	 * @throws JMSException
	 */
	//@JmsListener(destination = "${spring.activemq.json-queue}")
    public void readInprogressJsonMessage(final Message jsonMessage) throws JMSException {
        
		String messageData = null;
		
        System.out.println("Received message " + jsonMessage);
        
        if(!(jsonMessage instanceof TextMessage)) {
			return;
		}

		Media media = null;
		
		TextMessage textMessage = (TextMessage)jsonMessage;
		messageData = textMessage.getText();
		
		try {
			media = mapper.readValue(messageData, Media.class);
			
			System.out.println(media);
			
		} catch (Exception e) {
			System.out.println("Error while parsing JSON message");
		}
		System.out.println("messageData:"+messageData);
    }
}
