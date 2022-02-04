# TileNFT API
* [View the whitepaper](https://docs.google.com/document/d/1dUbI74EY_JYr42cpUB3k2hbykZwzWkBXsK0cf8wo1zM/edit?usp=sharing) for a detailed explanation of this project 
* View [TileNFT.io](http://tilenft.io/)
* This project is based off of the OpenSea tutorial for ["opensea-creatures"](https://github.com/ProjectOpenSea/opensea-creatures)
* There's a metadata NFT server project which goes along with this
    * TileNFT API: https://github.com/tyler-j-f/tile-api
* This whole project is a work in progress (W.I.P)
    * For this repo:
      * Additional comments should be added (in a lot of places)
      * Code should be cleaned up
      * Checkstyle warnings should be resolved
    * For the TileNFT smart contract repo:
        * Additional unit tests should be added
        * Code should be cleaned up.
        * Additional comments should be added in some places

## Building The Application
### Using a local SQL DB instance
* Follow [this guide](https://docs.oracle.com/cd/E19078-01/mysql/mysql-workbench/wb-getting-started-tutorial.html) to set up mysql-workbench locally
Run (from project root)
```bash
mvn clean install -P local
```
### Using a remote SQL DB instance (Google Cloud SQL)
* Follow [this guide](https://cloud.google.com/sql/docs/mysql/quickstart) to set up Google Cloud Sql
Run (from project root)
```bash
mvn clean install -P remote
```

## Running the app
Run (from project root)
```bash
java -jar target/demo_api-0.0.1-SNAPSHOT.jar
```
### Running the app and resetting all SQL tables
* When testing this application, we sometimes need to reset it completely
  * This can be done to test the app
  * This will have to be done if you deploy a new version of the smart contracts. This will clear out metadata from the older deploy of the smart contracts. 
Run (from project root)
```bash
java -jar target/demo_api-0.0.1-SNAPSHOT.jar --shouldResetSqlTables=true
```
### Running the app and resetting all SQL tables. Then terminating after resetting SQL tables.
* This is useful if you want to reset the SQL tables, but not actually run the metadata API server.
```bash
java -jar target/demo_api-0.0.1-SNAPSHOT.jar --shouldResetSqlTablesAndTerminateScheduler=true
```
## Building The Application Container Image
* To deploy this application via kubernetes we will need to create a container image
* First make sure
  * docker is running on your local machine
  * kubectl is running on your local machine and can access the cluster defined in `tile-api-app.yaml`
* Use the image name defined in `tile-api-app.yaml`
Run (from project root)
```bash
docker build -t us-east4-docker.pkg.dev/dev-eth-api/tile-api-repo/tile-api-app:v1.0.26 .
```
## Pushing The Application Container Image To Docker Registry
Run (from project root)
* Use the image name defined in `tile-api-app.yaml`
  * You will need to increment this version number everytime you want to push a new version
```bash
docker build -t us-east4-docker.pkg.dev/dev-eth-api/tile-api-repo/tile-api-app:v1.0.26 .
```
