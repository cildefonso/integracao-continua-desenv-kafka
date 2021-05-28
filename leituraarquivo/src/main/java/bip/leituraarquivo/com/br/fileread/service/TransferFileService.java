package bip.leituraarquivo.com.br.fileread.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bip.leituraarquivo.com.br.fileread.dto.FileTransfer;
import bip.leituraarquivo.com.br.fileread.gateway.json.FileUUIDJson;
import bip.leituraarquivo.com.br.fileread.message.Message;
import bip.leituraarquivo.com.br.fileread.model.FileSave;
import bip.leituraarquivo.com.br.fileread.service.createfile.CreateFileSaveService;
import bip.leituraarquivo.com.br.fileread.service.filesystem.TransferFileSystem;
import bip.leituraarquivo.com.br.fileread.service.ftp.TransferFTPService;
import bip.leituraarquivo.com.br.fileread.service.kafka.SendFileKafkaService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TransferFileService {
	
	@Value("${ftp.ativo}")
	private String ftpAtivo;
	@Value("${diretorio.ativo}")
	private String diretorioAtivo;
	@Value("${save.data}")
	private String saveData;
	
	@Autowired
	TransferFTPService transferFTPService;
	
	@Autowired
	CreateFileSaveService createFileSaveService;
	
	@Autowired
	TransferFileSystem transferFileSystem;
	
	@Autowired
	SendFileKafkaService sendFileKafkaService;
	
	@Scheduled(fixedRate = 100*60)
	public void execute() throws FileNotFoundException {


		log.info(Message.FTP_ACTIVE);	
	   List<FileTransfer> ftpFiles = transferFTPService.execute();
	   for (FileTransfer fileTransfer : ftpFiles) {
			if (saveData.toLowerCase().equals("active")) {
				String uuid = createFileSaveService.execute(FileSave
						.builder()
						.timestamp(fileTransfer.getTimestamp())
						.namefile(fileTransfer.getNamefile())
						.newnamefile(fileTransfer.getNewnamefile())
						.path(fileTransfer.getPath())
						.build()
						);
				fileTransfer.setUuid(uuid);
			}
			sendFileKafkaService.execute(fileTransfer, FileUUIDJson
														.builder()
														.uuid(fileTransfer.getUuid())
														.name("Parametro do registro")
														.build());
		}
	    log.info(Message.FOLDER_ACTIVE);
	    List<FileTransfer> systemFiles = transferFileSystem.execute();
		for (FileTransfer fileTransfer : systemFiles) {
			if (saveData.toLowerCase().equals("active")) {
				String uuid = createFileSaveService.execute(FileSave
						.builder()
						.timestamp(fileTransfer.getTimestamp())
						.namefile(fileTransfer.getNamefile())
						.newnamefile(fileTransfer.getNewnamefile())
						.path(fileTransfer.getPath())
						.build()
						);
				fileTransfer.setUuid(uuid);
			}
			sendFileKafkaService.execute(fileTransfer, FileUUIDJson
					.builder()
					.uuid(fileTransfer.getUuid())
					.name("Parametro do registro")
					.build());
		}
	}
	


}
