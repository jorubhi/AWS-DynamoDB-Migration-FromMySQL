package com.migratekarenge.dynamo;
import java.util.ArrayList;
/*
 * Copyright 2012-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.migratekarenge.model.Insaan;
import com.migratekarenge.mysql.MysqlDBRead;

/**
 * This sample demonstrates how to perform a few simple operations with the
 * Amazon DynamoDB service.
 */
public class DynamoDBPersist {

    /*
     * Before running the code:
     *      Fill in your AWS access credentials in the provided credentials
     *      file template, and be sure to move the file to the default location
     *      (C:\\Users\\Lenovo\\.aws\\credentials) where the sample code will load the
     *      credentials from.
  */
    static AmazonDynamoDB dynamoDB;

    /**
     * The only information needed to create a client are security credentials
     * consisting of the AWS Access Key ID and Secret Access Key. All other
     * configuration, such as the service endpoints, are performed
     * automatically. Client parameters, such as proxies, can be specified in an
     * optional ClientConfiguration object when constructing a client.
     *
     * @see com.amazonaws.auth.BasicAWSCredentials
     * @see com.amazonaws.auth.ProfilesConfigFile
     * @see com.amazonaws.ClientConfiguration
     */
    private static void init() throws Exception {
    	
        /*
         * The ProfileCredentialsProvider will return your [jorubhi]
         * credential profile by reading from the credentials file located at
         * (C:\\Users\\Lenovo\\.aws\\credentials).
         */
    	
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("jorubhi");
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\Lenovo\\.aws\\credentials), and is in valid format.",
                    e);
        }
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion("us-west-2")
            .build();
    }

    public static void asliKaam() throws Exception {
    	System.out.println("Loading...");
    	for(int i=0;i<2;i++)System.out.println(".");
        init();

        try {
            String tableName = "pareshanInsaan";

            //Preparing to Create a table with a primary hash key named 'insaanKaNaam', which holds a string
            CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                .withKeySchema(new KeySchemaElement().withAttributeName("insaanKaNaam").withKeyType(KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition().withAttributeName("insaanKaNaam").withAttributeType(ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

            TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
            TableUtils.waitUntilActive(dynamoDB, tableName);

           
            // Bringing the data from MySql\
            MysqlDBRead sqlReadObject = new MysqlDBRead();
            List<Insaan> migratingRecords = sqlReadObject.sqlKaSamaan();
            
            //Persisting them in DynamoDB
            Map<String, AttributeValue> item  ;
            for(Insaan ek : migratingRecords) {
            	item = newItem(ek.getInsaanKaNaam(), ek.getInsaanKiUmar(), ek.getPareshanHai().toString());
                PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
                PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
                System.out.println("Result: " + putItemResult);
            }

            //Querying Dynamo DB to check whether migration happened or not\
            System.out.println(">>>>>>    DynamoDB se samaan ara hai!!!     <<<<<<<");
            for(int i=0;i<2;i++)System.out.println(".");
            List<String> attributesToFetch = new ArrayList<String>();
            attributesToFetch.add("insaanKaNaam");
            attributesToFetch.add("insaamKiUmar");
            attributesToFetch.add("pareshanHai");
            ScanResult scanResult = dynamoDB.scan("pareshanInsaan",attributesToFetch);
            System.out.println("Result: " + scanResult);
            for(int i=0;i<2;i++)System.out.println(".");
            System.out.println(">>>>>>    MIGRATION OF DATA SUCCESSFULL!    <<<<<<<");
            
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    private static Map<String, AttributeValue> newItem(String name, int age, String pareshanHai) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("insaanKaNaam", new AttributeValue(name));
        item.put("insaanKiUmar", new AttributeValue().withN(Integer.toString(age)));
        item.put("pareshanHai", new AttributeValue(pareshanHai));
        return item;
    }

}
