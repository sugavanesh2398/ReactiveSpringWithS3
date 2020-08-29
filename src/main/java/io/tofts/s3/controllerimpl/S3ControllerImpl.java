package io.tofts.s3.controllerimpl;

import io.tofts.s3.controlerinterfaces.S3ControllerInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@RestController
public class S3ControllerImpl implements S3ControllerInterface {

    S3AsyncClient s3AsyncClient = S3AsyncClient.builder().region(Region.US_EAST_1).build();

    @Override
    @PostMapping("/createbucket/{bucketName}")
    public Mono<CreateBucketResponse> CreateBucket(@PathVariable String bucketName) {

        return Mono.fromFuture((CompletableFuture) s3AsyncClient.createBucket(CreateBucketRequest.builder().bucket(bucketName).build()))
                .map((response) -> {
                    return CreateBucketResponse.builder().location(bucketName);
                }).log("Bucket Created");


    }

    @Override
    @PostMapping("/deletebucket/{bucketName}")
    public Mono<DeleteBucketResponse> DeleteBucket(@PathVariable String bucketName) {

        return Mono.fromFuture(s3AsyncClient.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName).build()))
                .map(resp -> {
                    return resp;
                }).log("Bucket Deleted").ignoreElement();

    }

    @Override
    @PostMapping("/download/{fileName}/{bucketName}")
    public Mono<ResponseEntity> fileDownload(@PathVariable String fileName, @PathVariable String bucketName) {

        return Mono.fromFuture((CompletableFuture) s3AsyncClient.getObject(GetObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                AsyncResponseTransformer.toFile(Paths.get("D:\\" + fileName)))).log()
                .map((response) -> {
                    return ResponseEntity.ok("Completed");
                });
    }

    @Override
    @PostMapping("/uploadfile/{fileName}/{bucketName}")
    public Mono<ResponseEntity> uploadHandler(@PathVariable String fileName, @PathVariable String bucketName) {

        return Mono.fromFuture(s3AsyncClient.putObject(PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                AsyncRequestBody.fromFile(Paths.get("D:\\" + fileName)))).log()
                .map((response) -> {
                    return ResponseEntity.ok("Completed");
                });
    }

}

	 


