package me.aekrylov.technaxis_test.storage;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/23/19 10:35 PM
 */
@Component
public class S3StorageService implements StorageService {

    private final String bucketName;
    private final AmazonS3 s3client;

    public S3StorageService(S3Properties s3Properties) {
        this.bucketName = s3Properties.getBucketName();
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        s3Properties.getAccessKey(), s3Properties.getSecretKey()
                )))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        s3Properties.getEndpoint(), s3Properties.getRegion()
                ))
                .build();
    }


    @Override
    public String upload(MultipartFile file) throws IOException, FileUploadException {
        String objectName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        try {
            s3client.putObject(putObjectRequest);
        } catch (SdkClientException e) {
            throw new FileUploadException(e);
        }
        return s3client.getUrl(bucketName, objectName).toString();
    }
}
