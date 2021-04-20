package bip.leituraarquivo.com.br.fileread.service.createfile;

import org.springframework.stereotype.Service;

import bip.leituraarquivo.com.br.fileread.model.FileSave;

@Service
public interface CreateFileSaveService{

	public String execute(FileSave fileSave);


}
