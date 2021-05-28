package bip.leituraarquivo.com.br.fileread.service.bucket;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

import bip.leituraarquivo.com.br.fileread.dto.FileTransfer;

@Service
public class UploadS3Service {

    @Value("${s3.access.key}")
    private String accessKey;

    @Value("${s3.secret.key}")
    private String secretKey;

    @Value("${s3.host}")
    private String s3Host;

    @Value("${s3.bucket}")
    private String s3Bucket;
    
    @Value("${target.folder}")
    private String targetFolder;

    public FileTransfer execute(String nameFile, File file) throws IOException {
    	String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        AmazonS3Client newClient = new AmazonS3Client(credentials,
                new ClientConfiguration().withSignerOverride("S3SignerType"));

        newClient.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
        newClient.setEndpoint(s3Host);

        String newNameFile = createName(nameFile);

        newClient.putObject(s3Bucket, newNameFile, file);

        return FileTransfer
               .builder()
               .path(targetFolder)
               .timestamp(newNameFile.replace("-"+nameFile, ""))
               .namefile(nameFile)
               .newnamefile(newNameFile)
               .build();
    }

    /**
     * 
     * @param nameFile
     * @return
     */
    private String createName(String nameFile) {
        String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        date.concat("-").concat(nameFile);
        return date.concat("-").concat(nameFile);
    }
}