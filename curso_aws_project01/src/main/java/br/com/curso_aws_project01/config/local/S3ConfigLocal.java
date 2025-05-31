package br.com.curso_aws_project01.config.local;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketNotificationConfiguration;
import com.amazonaws.services.s3.model.S3Event;
import com.amazonaws.services.s3.model.TopicConfiguration;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Configuration
public class S3ConfigLocal {

    private static final String BUCKET = "pcs-invoice";

    private AmazonS3 amazonS3;

    public S3ConfigLocal() {
        this.amazonS3 = getAmazonS3();

        createBucket();

        AmazonSNS amazonSNS = getAmazonSNS();

        String s3InvoiceEventsTopicArn = createTopic(amazonSNS);

        AmazonSQS amazonSQS = getAmazonSQS();

        createQueue(amazonSNS, s3InvoiceEventsTopicArn, amazonSQS);

        configureBucket(s3InvoiceEventsTopicArn);
    }

    private void configureBucket(String s3InvoiceEventsTopicArn) {
        TopicConfiguration topicConfiguration = new TopicConfiguration();
        topicConfiguration.setTopicARN(s3InvoiceEventsTopicArn);
        topicConfiguration.addEvent(S3Event.ObjectCreatedByPut);

        amazonS3.setBucketNotificationConfiguration(BUCKET, new BucketNotificationConfiguration()
                .addConfiguration("putObject", topicConfiguration));
    }

    private void createQueue(AmazonSNS amazonSNS, String s3InvoiceEventsTopicArn, AmazonSQS amazonSQS) {
        String productEventsQueueUrl = amazonSQS.createQueue(new CreateQueueRequest("s3-invoice-events")).getQueueUrl();
        Topics.subscribeQueue(amazonSNS, amazonSQS, s3InvoiceEventsTopicArn, productEventsQueueUrl);
    }

    private String createTopic(AmazonSNS amazonSNS) {
        CreateTopicRequest createTopicRequest = new CreateTopicRequest("s3-invoice-events");
        return amazonSNS.createTopic(createTopicRequest).getTopicArn();
    }

    private AmazonSQS getAmazonSQS() {
        return AmazonSQSClient.builder()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", Regions.US_EAST_1.getName()))
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    private AmazonSNS getAmazonSNS() {
        return AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", Regions.US_EAST_1.getName()))
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    public AmazonS3 getAmazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials("test", "test");

        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", Regions.US_EAST_1.getName()))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .enablePathStyleAccess()
                .build();

        return this.amazonS3;
    }

    private void createBucket() {
        this.amazonS3.createBucket(BUCKET);
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        return this.amazonS3;
    }

}
