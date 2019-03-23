package me.aekrylov.technaxis_test.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Component;

import java.io.InputStream;
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
    public String upload(String filename, InputStream data, long size) {
        String objectName = UUID.randomUUID().toString() + "_" + filename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(size);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, data, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3client.putObject(putObjectRequest);

        return s3client.getUrl(bucketName, objectName).toString();
    }
}
