# Docker and Spring boot seed project


##Assumptions:

1.  You have basic knowledge of spring profiling.
2.  Docker is installed on your system.
3.  for Amazon's ECS: 
    1. aws cli is installed on your system
    2. You have amazon secret key and access key with you, if generate your credentials.
    3. you have ECR access for your user.

### To pull this docker image use  

```
docker pull javatechy/dockboot
```

### Dockerizing your spring boot application

 * To Dockerize a spring boot application create a folder `docker` inside `src/main/` 
 * Copy `Dockerfile` and `wrapper.sh` from `src/main/docker` of this project into your project's `src/main/docker`
 * Add this repository path in your pom properties(in your project or in your pom/ if your are using parent pom add it there).

 If you are using Amazon ECR add your registry like this:
 
 ```
 	<docker.registry>XXXXXXXX.dkr.ecr.ap-south-1.amazonaws.com</docker.registry>
 ```
 
 If you want to push the image in your public docker hub account add your username like this
 
 ```
 	<docker.registry>javatechy</docker.registry>
 ```
 
 
 * Add this plugin in plugins section of your pom.xml
 
 ```
   <plugin>
				<groupId>io.fabric8</groupId>
				<artifactId>docker-maven-plugin</artifactId>
				<configuration>
					<verbose>true</verbose>
					<registry>${docker.registry}</registry>
					<dockerFileDir>src/main/docker</dockerFileDir>
					<images>
						<image>
							<name>${docker.registry}/dockboot:${project.version}</name>
							<build>
								<dockerFileDir>${project.basedir}/src/main/docker</dockerFileDir>
								<tags>
									<tag>${project.version}</tag>
									<tag>latest</tag>
								</tags>

								<assembly>
									<inline>
										<dependencySets>
											<dependencySet>
												<includes>
													<include>${project.groupId}:${project.artifactId}</include>
												</includes>
												<outputFileNameMapping>app.jar</outputFileNameMapping>
											</dependencySet>
										</dependencySets>
									</inline>
								</assembly>
							</build>
						</image>
					</images>
				</configuration>
				<executions>
					<execution>
						<id>build-docker-image</id>
						<phase>package</phase>
						<goals>
							<goal>build</goal>
						</goals>
					</execution>
					<execution>
						<id>push-docker-image</id>
						<phase>deploy</phase>
						<goals>
							<goal>push</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
 ```

Note:  replace `dockboot` in above code inside images->image->name with your application name.


## Build your docker image:

##### Run this command and your will find your docker image in `docker images`

```
mvn clean install
```

##### to Skip docker build

```
mvn clean install -Ddocker.skip
```

##### to push image through mvn

Go inside your project directory. (If using mutlimodule project go inside your working project directory or sub project) and execute this command

```
mvn docker:push
```

## Push An Image to ECR:

* Configure your aws cli using this command `aws configure` and add your credentials and region.
* get Login credentials from ECS by using this

```
aws ecr get-login --no-include-email --region ap-south-1
```
* If your project's repository is not created yet execute this command `aws ecr create-repository --repository-name PROJECT_NAME`
* Copy the above command output and run it.
* check your image after `mvn clean install` using `docker images`

* To Push an image ,Go inside your project directory. (If using mutlimodule project go inside your working project directory or sub project) and execute this command

```
mvn docker:push
```

### Run this image:

Assuming your logs directory /var/log/casa
and your spring boot active profile is DEV provided by `ENV_NAME`

```
docker run -it -v /var/log/casa:/var/log/casa -p 9000:8000 -e ENV_NAME=dev javatechy/dockboot:1.0
```

###  Run in detached Mode

```
docker run -it -v /var/log/casa:/var/log/casa -p 9000:8000 -d -e ENV_NAME=dev javatechy/dockboot:1.0
```

