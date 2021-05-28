package bip.leituraarquivo.com.br.fileread.service.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import bip.leituraarquivo.com.br.fileread.dto.FileTransfer;
import bip.leituraarquivo.com.br.fileread.message.Message;
import bip.leituraarquivo.com.br.fileread.service.bucket.UploadS3Service;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TransferFileSystem {


	@Value("${ftp.s3.bucket}")
	private String ftpS3Bucket;
	
	@Value("${source.folder}")
	private String sourceFolder;
	@Value("${target.folder}")
	private String targetFolder;
	
	@Autowired
	UploadS3Service uploadS3Service;
	
	/**
	 * Objetivo: Abrir, excluir, ler e transferiri o arquivo processado.
	 * @return
	 */
	public List<FileTransfer> execute() {
		List<FileTransfer> returnList = new ArrayList<>();
        
        try {
           //nome = JOptionPane.showInputDialog(null,"Entre com o nome do arquivo");
     	    String currentFolder = System.getProperty("user.dir");
           	File sFile = new File(currentFolder + sourceFolder);
           	//File targetFile = new File(targetFolder);
         	if (sFile.exists()) {
	    	    File[] sourceFiles = sFile.listFiles();
	    	    for (File fSource : sourceFiles) {
	    	        File fTarget = new File(currentFolder +  targetFolder);//new File(new File(targetFolder), fSource.getName());
	    	        //copyFileUsingStream(fSource, fTarget);
	                if (ftpS3Bucket.toLowerCase().equals("active")) {
	                	log.info(Message.SEND_FILE_S3);
		                //FileTransfer fileTransfer = uploadS3Service.execute(fTarget.getName().toString(), sFile);
	                	FileTransfer fileTransfer = uploadS3Service.execute(fSource.getName().toString(), fSource);
		                fileTransfer.setPathLocal(fTarget);
		                returnList.add(fileTransfer);
	                    log.info(Message.SENT_FILE_S3);
	                }
	                if (copyFile(sFile,fTarget )) {
	    	          //deleteFiles(fSource);
	    	          log.info(Message.DELETE_FILE);
	                }
	    	    }
	    
        	} else log.info(Message.EXIST_FILE);
    	    
        } catch (Exception ex) {
	    	log.error("Error: " + ex.getMessage());
	        ex.printStackTrace();
	    } 
		return returnList;
	}
	
	/**
	 * Objetivo: Excluir todos os arquivos do diretório de origem.
	 * @param fSource
	 */
	private static void deleteFiles(File fSource) {
        if(fSource.exists()) {
            try {
                FileUtils.forceDelete(fSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	/**
	 * Objetivo: Copiar todos os arquivo para outro diretório
	 * @param source
	 * @param dest
	 */
	private static void copyFileUsingStream(File source, File dest) {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } catch (Exception ex) {
	        log.error(Message.UNABLE_COPY_FILE + ex.getMessage());
	    } finally {
	        try {
	            is.close();
	            os.close();
	        } catch (Exception ex) {
	        }
	    }
	}

	/**
	 * Objetivo: Leitura de arquivos
	 * @param sourceFile
	 * @throws FileNotFoundException
	 */
	private static void readFile(String sourceFile) throws FileNotFoundException {
		
	
        BufferedReader br = new BufferedReader(new FileReader(sourceFile));
        try {
			while(br.ready()){ 
				String linha = br.readLine(); 
				log.info( Message.READ_FILE+linha);
			}
			  br.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Objetivo: Copiar arquivo do diretório de origem para outro destino.
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public boolean copyFileList( File srcDir, File dstDir ){
	    
        try{
        	if( !srcDir.exists() ){
        		srcDir.mkdir();
            }
            if( srcDir.isDirectory() ){
                if( !dstDir.exists() ){
                    dstDir.mkdir();
                }

                String[] children = srcDir.list();

                for (int i=0; i<children.length; i++){

                	copyFile( new File( srcDir, children[i] ), new File( dstDir, children[i] ) );
                }
            } 
            else{

                InputStream in = new FileInputStream( srcDir );
                OutputStream out = new FileOutputStream( dstDir );

                byte[] buf = new byte[1024];
                int len;

                while( (len = in.read( buf ) ) > 0 ) {

                    out.write( buf, 0, len );
                }

                in.close();
                out.close();
            }
        }
        catch( IOException ioex ){

            ioex.printStackTrace();
            return false;
        }

        return true;
    }
	
	/**
	 * Objetivo: Copiar arquivo do diretório de origem para outro destino.
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public boolean copyFile( File srcDir, File dstDir ){
	    
        try{

            if( srcDir.isDirectory() ){
              String[] children = srcDir.list();
              copyFile( new File( srcDir, children[0] ), new File( dstDir, children[0] ) );
            } 
            else{
              InputStream in = new FileInputStream( srcDir );
              OutputStream out = new FileOutputStream(dstDir);
              byte[] buf = new byte[1024];
                int len;
                while( (len = in.read( buf ) ) > 0 ) {
                    out.write( buf, 0, len );
                }
              in.close();
              out.close();
              File file = new File(srcDir.toPath().toString()); 
              file.delete();
            }
        }
        catch( IOException ioex ){

            ioex.printStackTrace();
            return false;
        }

        return true;
    }
	

}
