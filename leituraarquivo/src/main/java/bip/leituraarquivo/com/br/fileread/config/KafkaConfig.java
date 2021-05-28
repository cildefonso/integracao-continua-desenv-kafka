package bip.leituraarquivo.com.br.fileread.config;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import lombok.extern.log4j.Log4j2;


@Configuration
@EnableKafka
@Log4j2
public class KafkaConfig {

	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String servers;
	@Value("${spring.kafka.producer.retries}")
	private int retries;
	@Value("${spring.kafka.producer.batch.size}")
	private int batchSize;
	@Value("${spring.kafka.producer.linger}")
	private int linger;
	@Value("${spring.kafka.producer.buffer.memory}")
	private int bufferMemory;

	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		props.put(ProducerConfig.RETRIES_CONFIG, retries);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
//		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "cn.ztuo.bitrade.kafka.kafkaPartitioner");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<String, String>(producerFactory());
	}
	
//    @Value("${kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${kafka.consumergroup}")
//    private String consumerGroup;
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//    	try {
//    		Map<String, Object> props = new HashMap<>();
//    		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    		return props;
//    	}
//    	catch(Exception e) {
//    		 log.error(e);
//    		 return null;
//    	}
//     }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//    	try {
//    		Map<String, Object> props = new HashMap<>();
//    		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    		props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
//    		return props;
//    	}
//    	catch(Exception e) {
//    		 log.error(e);
//    		 return null;
//    	}
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//    	try {
//    		return new DefaultKafkaProducerFactory<>(producerConfigs());
//    	}
//    	catch(Exception e) {
//    		 log.error(e);
//    		 return null;
//    	}
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//    	try {
//    		return new KafkaTemplate<>(producerFactory());
//    	}
//    	catch(Exception e) {
//    		 log.error(e);
//    		 return null;
//    	}
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//    	try {
//    		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new StringDeserializer());
//    	}
//    	catch(Exception e) {
//    		 log.error(e);
//    		 return null;
//    	}
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
//        try{
//        	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//            factory.setConsumerFactory(consumerFactory());
//            factory.setReplyTemplate(kafkaTemplate());
//            return factory;
//    	}
//    	catch(Exception e) {
//    		 log.error(e);
//    		 return null;
//    	}
//    }

}