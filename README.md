# Docker and Spring boot seed project

### To pull this docker image use  

```
docker pull javatechy/dockboot
```

### Run this image:

Assuming your logs directory /var/log/casa
and your spring boot active profile is DEV

```
docker run -it -v /var/log/casa:/var/log/casa -p 9000:8000 -e ENV_NAME=dev javatechy/dockboot:1.0
```


