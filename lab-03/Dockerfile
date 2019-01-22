# Build:
#   docker build --rm -t inventory-service:1.0.1 .
#   docker tag inventory-service:1.0.1 docker.io/rhtgptetraining/inventory-service:1.0.1

# Docker push (on F28):
#   Make sure the following is the *first* entry in the docker daemon (by updating /etc/sysconfig/docker):  --add-registry docker.io
#   docker login docker.io -u rhtgptetraining
#   docker push docker.io/rhtgptetraining/inventory-service:1.0.1

# Execution: OpenShift
#   oc run inventory-service --image=docker.io/rhtgptetraining/inventory-service:1.0.1

FROM fabric8/s2i-java:2.1
ENV AB_PROMETHEUS_OFF=true JAVA_APP_DIR=/deployments
ENV JAVA_APP_DIR=/deployments
EXPOSE 8080 8778 9779
COPY target/inventory-service-1.0.1-thorntail.jar /deployments/
