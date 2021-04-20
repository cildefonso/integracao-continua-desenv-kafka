package bip.leituraarquivo.com.br.fileread.service.kafka;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bip.leituraarquivo.com.br.fileread.dto.FileTransfer;
import bip.leituraarquivo.com.br.fileread.gateway.json.FileUUIDJson;

@Service
public class SendFileKafkaService {

	public static final String SISTEMA_1 = "[SISTEMA1]";
	public static final String SISTEMA_2 = "[SISTEMA2]";
	
	
    @Value("${kafka.topictopics3}")
    private String topictopics3;

    @Value("${kafka.topictopics4}")
    private String topictopics4;
    
    @Value("${kafka.partition0}")
    private Integer partition0;
	
	@Autowired
	private KafkaRequestSender kafkaRequestSender;
	
	public void execute(FileTransfer fileTransfer) throws FileNotFoundException {
		
		//Lê a primeira linha do arquivo
		Scanner in = new Scanner(fileTransfer.getPathLocal());
		String fisrtLine = null;
		while (in.hasNextLine()) {
			fisrtLine = in.nextLine();
			break;
		}
		in.close();
		String topic = null;
		
		switch (fisrtLine){
        	case SISTEMA_1:
        		topic = topictopics3;
        		break;
        	case SISTEMA_2:
        		topic = topictopics4;
        		break;

		}
	

		kafkaRequestSender.sendMessage(topic, partition0, 
					FileUUIDJson
					.builder()
					.uuid(fileTransfer.getUuid())
					.name("Parametro do registro")
					.build()
				  );
	}
}
