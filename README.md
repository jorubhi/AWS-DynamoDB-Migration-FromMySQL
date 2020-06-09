# MysqlToDynamoDB
Reading data from a SQL database and storing it in a NO SQL database and then verifying it. Simple read write operation on DynamoDB.


Install Mysql server and run it. Create a schema:-

CREATE SCHEMA `pareshan` ;
CREATE TABLE `pareshan`.`insaan` (
  `idinsaan` INT NOT NULL,
  `insaanKaNaam` VARCHAR(45) NULL,
  `insaanKiUmar` VARCHAR(45) NOT NULL,
  `pareshani` TINYINT NOT NULL,
  PRIMARY KEY (`idinsaan`));
ALTER TABLE `pareshan`.`insaan` 
CHANGE COLUMN `insaanKiUmar` `insaanKiUmar` INT NOT NULL ;

Add some records here in the table.

*******

Install Dynamo locally. HOw?
Download this:
Extract it and open the folder where DynamoDBLocal.jar is situated.
Now open powershell and -> java -D"java.library.path=./DynamoDBLocal_lib" -jar DynamoDBLocal.jar

It will start the DynamoDB server instance. To use Dynamo from command line, you'll need AWS CLI
Link:https://s3.amazonaws.com/aws-cli/AWSCLI64PY3.msi
After installed, use any DynamoDB cli command like 
    >aws dynamodb list-tables
to view all the tables 
                        or

    >aws dynamodb scan --table-name pareshanInsaan
to view all the records in the table pareshanInsaan

*************
->Download and install AWS SDK for Eclipse (http://aws.amazon.com/sdkforjava/)
->Direct download :-(http://ds60ft5bv5jal.cloudfront.net/latest/aws-java-sdk.zip)
->And add this in eclipse and restart it.


->Create a new AWS Java project/ Import this project as AWS Java Project, and while creating you'll have to configure.

->CHange the jdbc url username and password accordingly

->Enter access key Id and Secret Acess key by clicking on configurations.

Access key Id
88888888888888888

Secret access key  
8888888888888888

OR

*************
credentials file in C:\Users\Lenovo\.aws
content of the file :

[jorubhi]
aws_access_key_id=888888888888
aws_secret_access_key=8888888888888


*************
Add a config file as well in same directory.
content of the file :

[default]
region = us-west-2


->The project will build with Maven and install all the dependencies.
  And then go to
  com.migratekarenge.showtime;

  and run MigrationHojayeShuru as java application.

