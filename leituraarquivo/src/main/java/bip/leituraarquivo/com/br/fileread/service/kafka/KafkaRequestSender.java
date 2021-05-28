package bip.leituraarquivo.com.br.fileread.service.kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import bip.leituraarquivo.com.br.fileread.model.SendMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Component
@AllArgsConstructor
@EnableScheduling
@Log4j2
public class KafkaRequestSender {
	
	private final KafkaTemplate<String, String> KafkaTemplate = null;
	
	@Transactional
	public void sendMessage(String topicRequest, Integer partition, Object request) {
//		final String mensageKey = UUID.randomUUID().toString();
//		Message<String> message = SendMessage
//								.builder()
//								.topic(topicRequest)
//								.partition(partition)
//								.key(mensageKey)
//								.timestamp(Long.parseLong(date))
//								.data(new Gson().toJson(request))
//								.build();
		Message<String> message = MessageBuilder
				.withPayload(new Gson().toJson(request))
				.setHeader(KafkaHeaders.TOPIC, topicRequest)
				.build();
		 log.info(message);
		// this.KafkaTemplate.send(topicRequest, mensageKey, new Gson().toJson(request));
		 this.KafkaTemplate.send(message);
	}

}
