FROM java:8
ENV ARTIFACTID miracle-service-manage-user
ENV ARTIFACTVERSION 1.0-SNAPSHOT
ENV HOME_PATH /home
WORKDIR $HOME_PATH
ADD /$ARTIFACTID-$ARTIFACTVERSION.jar $HOME_PATH/$ARTIFACTID.jar
ENTRYPOINT ["java", "-jar", "miracle-service-manage-user.jar"]