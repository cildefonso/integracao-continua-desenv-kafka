package bip.leituraarquivo.com.br.fileread.service.kafka;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;

import bip.leituraarquivo.com.br.fileread.dto.FileTransfer;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SendFileKafkaService {

//	public static final String SISTEMA_1 = "[SISTEMA1]";
//	public static final String SISTEMA_2 = "[SISTEMA2]";
//	
//	
//    @Value("${kafka.topictopics3}")
//    private String topictopics3;
//
//    @Value("${kafka.topictopics4}")
//    private String topictopics4;
//    
//    @Value("${kafka.partition0}")
//    private Integer partition0;
    
    
    @Value("${order.topic}")
    private String orderTopic;
    @Value("${order.topic1}")
    private String orderTopic1;
    @Value("${order.topic2}")
    private String orderTopic2;
    @Value("${order.topic3}")
    private String orderTopic3;
    
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
	
	//@Autowired
	//private KafkaRequestSender kafkaRequestSender;
    
    public SendFileKafkaService(final KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    
    public void send(Object order) {

    	  log.info("Information in the class OrderProducer "+order);
    	  //IMap<Long, Long> order_map = hzInstance.getMap(HazelcastUtil.getOrderBookKey(cancel.getCoinTeam(), cancel.getIsBuy()));
    		Message<String> message = MessageBuilder
    		.withPayload(new Gson().toJson(order))
    		.setHeader(KafkaHeaders.TOPIC, orderTopic)
    		.build();
    		kafkaTemplate.send(message);
    }

    public void send(final @RequestBody String order) {
		  final String mensageKey = UUID.randomUUID().toString();
		  log.info("Information in the class OrderProducer "+order);
		  
		  kafkaTemplate.send(orderTopic, mensageKey,  order);
    }
    
    public void execute(FileTransfer fileTransfer, Object request) {
    //public void sendDataToKafka(final @RequestBody String order) {
    	final String mensageKey = UUID.randomUUID().toString();
       	Random random = new Random();
    	int numero = random.nextInt(9);
    	ListenableFuture<SendResult<String, String>> listenableFuture;
    	
    	log.info("number random "+numero+"");
     	switch (numero) {
    	case 1: case 2: case 3:
    		listenableFuture = kafkaTemplate.send(orderTopic, mensageKey, new Gson().toJson(request));
       		break;
       	case 4: case 5: case 6:
       		listenableFuture = kafkaTemplate.send(orderTopic1, mensageKey, new Gson().toJson(request));
    		break;
    	case 7: case 8: case 9:
    		listenableFuture = kafkaTemplate.send(orderTopic2, mensageKey, new Gson().toJson(request));
    		break;
    	default:
    		listenableFuture = kafkaTemplate.send(orderTopic3, mensageKey, new Gson().toJson(request));
            break;
    		
    	}
        
        
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

          @Override
          public void onSuccess(SendResult<String, String> result) {
            log.info(String.format("Published  successfuly of the data     = %s", result.getProducerRecord().value()));
          }

          @Override
          public void onFailure(Throwable ex) {
            log.error("Unable to send data to Kafka", ex);
          }
        });
      }
    
//	public void execute(FileTransfer fileTransfer) throws FileNotFoundException {
//		
//		//Lê a primeira linha do arquivo
//		Scanner in = new Scanner(fileTransfer.getPathLocal());
//		String fisrtLine = null;
//		while (in.hasNextLine()) {
//			fisrtLine = in.nextLine();
//			break;
//		}
//		in.close();
//		String topic = null;
//		
//		switch (fisrtLine){
//        	case SISTEMA_1:
//        		topic = topictopics3;
//        		break;
//        	case SISTEMA_2:
//        		topic = topictopics4;
//        		break;
//
//		}
//	
//
//		kafkaRequestSender.sendMessage(topic, partition0, 
//					FileUUIDJson
//					.builder()
//					.uuid(fileTransfer.getUuid())
//					.name("Parametro do registro")
//					.build()
//				  );
//	}
    
}
