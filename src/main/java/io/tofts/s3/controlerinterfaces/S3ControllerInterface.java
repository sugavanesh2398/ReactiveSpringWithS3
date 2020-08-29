package io.tofts.s3.controlerinterfaces;

import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.http.ResponseEntity;


public interface S3ControllerInterface {

    Mono<CreateBucketResponse> CreateBucket(String bucketName);

    Mono<DeleteBucketResponse> DeleteBucket(String bucketName);

    Mono<ResponseEntity> fileDownload (String fileName, String buckeName);


	Mono<ResponseEntity> uploadHandler(String fileName, String bucketName);

}
