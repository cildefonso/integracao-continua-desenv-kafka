package bip.leituraarquivo.com.br.fileread.model;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builderpublic class SendMessage implements Message<String> {

	private String topic;
	private Integer partition;
	private Long timestamp;
	private String key;
	private String data;
	@Override
	public String getPayload() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MessageHeaders getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}


}
