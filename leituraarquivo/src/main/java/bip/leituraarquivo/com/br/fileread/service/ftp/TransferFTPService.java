package bip.leituraarquivo.com.br.fileread.service.ftp;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bip.leituraarquivo.com.br.fileread.dto.FileTransfer;
import bip.leituraarquivo.com.br.fileread.message.Message;
import bip.leituraarquivo.com.br.fileread.service.bucket.UploadS3Service;
import lombok.extern.log4j.Log4j2;



@Service
@Log4j2
public class TransferFTPService {
	@Value("${ftp.server}")
	private String server;
	@Value("${ftp.port}")
	private String port;
	@Value("${ftp.user}")
	private String user;
	@Value("${ftp.password}")
	private String password;
	@Value("path.file.save")
	private String pathFileSave;
	@Value("${ftp.s3.bucket}")
	private String ftpS3Bucket;
	
	@Value("${target.folder}")
	private String targetFolder;
	
	@Autowired
	public UploadS3Service uploadS3Service;
	
	
	public List<FileTransfer> execute() {
		//retorna lista de arquivo
		List<FileTransfer> returnList = new ArrayList<>();
		//Criar cliente FTP
		FTPClient ftpClient = new FTPClient();
		try {
            File fileTarget = new File(targetFolder);
            if (!fileTarget.exists()) {
            	fileTarget.mkdirs();
            }
            
            // configuração do ftp client
            ftpClient.addProtocolCommandListener(new PrintCommandListener(
            		new PrintWriter(System.out)));
            //Configurar o endereço e a porta do servidor de FTP.
            ftpClient.connect(server, Integer.parseInt(port));
            //Autenticação do usuário de serviço
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //Listar os arquivos
            String[] files = ftpClient.listNames();
            
            if (!files.toString().equals("")) {
	            for (String itemFile : files) {
	            	String remoteFile1 = itemFile;
	                File tmpDownload = new File(itemFile);
	                // baixa o arquivo
	                OutputStream outputStream1 =  new BufferedOutputStream(new FileOutputStream(tmpDownload));
	                boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
	                outputStream1.close();
	                if (success) {
	                    if (ftpS3Bucket.toLowerCase().equals("active")) {
	                    	log.info( Message.SEND_FILE_S3);
	                    	FileTransfer fileTransfer = uploadS3Service.execute(itemFile, tmpDownload);
	                    	fileTransfer.setPathLocal(tmpDownload);
	                    	returnList.add(fileTransfer);
	                        log.info(Message.SENT_FILE_S3);
	                    }
	                    ftpClient.deleteFile(itemFile);
	                    log.info(Message.DELETED_SERVER_FILE);
	                }
	                

	            }
		    } else log.info(Message.EXIST_FILE);
	    } catch (Exception ex) {
	    	log.error("Error: " + ex.getMessage());
	        ex.printStackTrace();
	    } finally {
	        try {
	            if (ftpClient.isConnected()) {
	                ftpClient.logout();
	                ftpClient.disconnect();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
		return returnList;
	}
	
}
