package bip.leituraarquivo.com.br.fileread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeituraArquivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeituraArquivoApplication.class, args);
	}

}
