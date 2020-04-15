# Coding workshop search developer
## Setup

Requirements:
- Java8 (most recent, Update 65+)
- Maven3

Project setup:
1. Download the `codeworkshop-es.zip` Elasticsearch from the following link and unzip it: 
- Mac/Linux: https://drive.google.com/file/d/14kFnYunRmDuFc619u_iUCf9Rv_0xmj24/view?usp=sharing
- Windows: https://drive.google.com/file/d/1xfyVCJvdst-RAbHL-CUX-4it4RghHvat/view?usp=sharing

Running Elasticsearch:

- Run `bin/elasticsearch` (or `bin\elasticsearch.bat` on Windows) within the codeworkshop-es folder

Running the code:

- Open the code within the codeworkshop folder with your IDE
- Run the following maven command:
```bash
mvn clean install
```
- Run the Main.java class to start up the application (For correct start up you need to provide the 
following argument `server local/server.yml` specifying application configuration)

## Instructions

This application enables you to search inside of a simple movie database (see `example/moviedata_example_doc.json` 
for the internal data structure). The data is store inside of an Elasticsearch. The application uses Dropwizard to build
a REST service, the Elasticsearch java API to communicate with Elasticsearch and Immutables library for simpler and immutable entity creation.

1. First have a look at the code in `core` and get a general understanding of what it is about
2. Look into the `local` folder to see the config of the application
3. Work on the following tasks

### Task 1
The application config in the `local` folder currently only has 2 config parameters a boolean and the host of an Elasticsearch
the application is using. We also want to make the port configurable. So try adding the port dynamically via config to your application.

### Task 2
Currently when a client of our API wants to sort the results he can sort them with any field inside of the `FieldNames.java`
even then ones that currently do not support sorting because how our elasticsearch mapping is constructed. Also theoretically a client
could sort by a field that dose not exist.
Adjust the application so sorting is only possible on `vote_average,vote_count,runtime,popularity,id` and deliver an appropriate error
message in the MovieSearchResponse to make the client aware about his mistake.

Request example for application:
```bash
curl -X POST \
  http://localhost:8080/search \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 111' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8080' \
  -H 'Postman-Token: 64b48156-0a45-451b-be96-4e06639e4f3b,23514348-4418-4b0e-acea-46370fa052bb' \
  -H 'User-Agent: PostmanRuntime/7.19.0' \
  -H 'cache-control: no-cache' \
  -d '{
	"query": "batman",
	"from": 0,
	"size": 30,
	"sort": {
		"fieldName": "vote_average",
		"order": "desc"
	}
}'
```

### Task 3
The application also contains an endpoint called `measureSearchQuality` this endpoint returns for a given SearchRequest the current order of movies
Elasticsearch returned and the ideal order of movies which would be the perfect order of movies for this request through some magical algorithm.
Your task now is to create some algorithm to measure the quality of SearchResponses based on these two inputs and make your result
visible in the `SearchQualityResponse`. Hint: For example you could measure the difference between current position of a movie and ideal position
and write it into the `SearchQualityResponse` for every position. But you can also go for a more sophisticate implementation if any good ideas
come to your mind.




