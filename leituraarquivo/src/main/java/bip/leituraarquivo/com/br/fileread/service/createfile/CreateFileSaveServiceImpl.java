package bip.leituraarquivo.com.br.fileread.service.createfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bip.leituraarquivo.com.br.fileread.enums.FileStatusEnum;
import bip.leituraarquivo.com.br.fileread.gateway.repository.FileSaveRepository;
import bip.leituraarquivo.com.br.fileread.model.FileSave;

@Service
public class CreateFileSaveServiceImpl implements CreateFileSaveService {
	
	@Autowired
	private FileSaveRepository fileSaveRepository;
	
	@Transactional
	public String execute(FileSave fileSave) {
		fileSave.setStatus(FileStatusEnum.RECEBIDO.toString());
		fileSaveRepository.save(fileSave);
		
		return fileSave.getId().toString();
	}

}
