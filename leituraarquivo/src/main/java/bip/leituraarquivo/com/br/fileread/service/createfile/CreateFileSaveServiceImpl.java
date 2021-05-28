package bip.leituraarquivo.com.br.fileread.service.createfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bip.leituraarquivo.com.br.fileread.enums.FileStatusEnum;
import bip.leituraarquivo.com.br.fileread.gateway.repository.FileSaveRepository;
import bip.leituraarquivo.com.br.fileread.model.FileSave;
import bip.leituraarquivo.com.br.fileread.service.filesystem.TransferFileSystem;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CreateFileSaveServiceImpl implements CreateFileSaveService {
	
	@Autowired
	private FileSaveRepository fileSaveRepository;
	
	@Transactional
	public String execute(FileSave fileSave) {
		try {
			fileSave.setStatus(FileStatusEnum.RECEBIDO.toString());
			fileSaveRepository.save(fileSave);
			
			return fileSave.getId().toString();
		}
		catch (Exception ex) {
			log.error(ex);
		}
		return null;
	}

}
