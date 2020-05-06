# CoffeePlanner

Would like to investigate some new frameworks used in 'OR' company:
* Helidon
* MicroProfile (Config, Health, Metrics, Fault Tolerance)
* EclipseLink (JTA, JPA)
* JWT Auth with Helidon security context 
* Docker multi-stage builds and Kubernetes cluster

### Task 

Create a REST API for coffee machines in office. 
Office building has several floors and there are several kitchens on each floor. 
Some (most) of those kitchens are equipped with coffee machines. 
User should be able to request a cup of coffee (cappuccino, latte, espresso) and get status of his order.

* Create REST API in Java
* UI is not required
* Use technologies of your choice
* Ideally send your solution as link to github
* Extra points (optional) for deploying solution to some cloud provider (OCI, AWS, Azure, etc...)

### Endpoints

##### OpenAPI

```
curl -s -X GET http://localhost:8080/openapi
```

##### Health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
.....
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
.....
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":"gc_g1_young_generation_count",...
```


### Build and run

```bash
mvn package
java -jar target/coffee.jar
```

### Build the Docker image and start the application

```
docker build -t coffee .                                    # Build
docker run --rm -p --name coffee 8080:8080 coffee:latest    # Run
docker stop coffee                                          # Stop
```

### Deploy the application to Kubernetes

```
kubectl cluster-info            # Verify which cluster
kubectl get pods                # Verify connectivity to cluster
kubectl create -f app.yaml      # Deploy application
kubectl get service coffee      # Verify deployed service, get IP address of deployed sevice
kubectl delete -f app.yaml      # Delete application
```

### GraalVM: build a native image

GraalVM allows you to compile your programs ahead-of-time into a native
 executable. See https://www.graalvm.org/docs/reference-manual/aot-compilation/
 for more information.

You can build a native executable in 2 different ways:

#### Local build

Download Graal VM at https://www.graalvm.org/downloads, the version
 currently supported for Helidon is `20.0`.

```
# Setup the environment
export GRAALVM_HOME=/path
# build the native executable
mvn package -Pnative-image
```

You can also put the Graal VM `bin` directory in your PATH, or pass
 `-DgraalVMHome=/path` to the Maven command.

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-native-image
 for more information.

Start the application:

```
./target/coffee
```

#### Multi-stage Docker build

Build the "native" Docker Image

```
docker build -t coffee-native -f Dockerfile.native .
```

Start the application:

```
docker run --rm -p 8080:8080 coffee-native:latest
```


### Java Runtime Image : build image using jlink

You can build a custom Java Runtime Image (JRI) containing the application jars and the JDK modules 
on which they depend. This image also:

* Enables Class Data Sharing by default to reduce startup time. 
* Contains a customized `start` script to simplify CDS usage and support debug and test modes. 
 
You can build a custom JRI in two different ways:

#### Local build

```
# build the JRI
mvn package -Pjlink-image
```

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-jlink-image
 for more information.

Start the application:

```
./target/coffee/bin/start
```

#### Multi-stage Docker build

Build the "jlink" Docker Image

```
docker build -t coffee-jlink -f Dockerfile.jlink .
```

Start the application:

```
docker run --rm -p 8080:8080 coffee-jlink:latest
```

See the start script help:

```
docker run --rm coffee-jlink:latest --help
```