package bip.leituraarquivo.com.br.fileread.dto;

import java.io.File;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class FileTransfer {

		private String uuid;
		private String timestamp;
		private String namefile;
		private String newnamefile;
		private String path;
		private File pathLocal;
		

		
}
