package bip.leituraarquivo.com.br.fileread.gateway.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import bip.leituraarquivo.com.br.fileread.model.FileSave;

public interface FileSaveRepository extends CrudRepository<FileSave, UUID> {

}
