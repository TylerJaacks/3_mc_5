FROM ubuntu:20.04

# Image Details
LABEL maintainer="tjaacks@iastate.edu"
LABEL version="1.0"
LABEL description="A Docker image for COM S 309 project."

# Force the package manager to be quier when installing packages.
ARG DEBIAN_FRONTEND=noninteractive

# Run a package update.
RUN apt-get update

# Install the OpenJDK 11.
RUN apt-get install -y openjdk-11-jdk wget unzip git

# Export important paths to the environment.
ARG JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ARG ANDROID_SDK_ROOT=/opt/android-sdk
ARG PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/bin/

# Install the Android SDK.
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-7583922_latest.zip
RUN mkdir $ANDROID_SDK_ROOT
RUN unzip commandlinetools-linux-7583922_latest.zip -d $ANDROID_SDK_ROOT/

# Accept Android SDK licenses.
ADD license_accepter.sh /opt/
RUN chmod +x /opt/license_accepter.sh && /opt/license_accepter.sh $ANDROID_SDK_ROOT

# Install the Platform, Platform Tools, and Build Tools.
RUN $ANDROID_SDK_ROOT/cmdline-tools/bin/sdkmanager --sdk_root=$ANDROID_SDK_ROOT --update
RUN $ANDROID_SDK_ROOT/cmdline-tools/bin/sdkmanager --sdk_root=$ANDROID_SDK_ROOT --install "platform-tools" "platforms;android-31" "build-tools;30.0.2"

# Expose the ADB port.
EXPOSE 5037

# Add SSH Key to Docker container.
RUN mkdir /root/.ssh
RUN ssh-keyscan -H git.linux.iastate.edu >> /root/.ssh/known_hosts
RUN git config --global core.sshCommand "ssh -i /root/.ssh/id_rsa -F /dev/null"
ADD id_rsa /root/.ssh/
ADD id_rsa.pub /root/.ssh/
RUN chmod 600 /root/.ssh/id_rsa

# Clone the repository.
RUN git clone git@git.linux.iastate.edu:cs309/fall2021/3_mc_5.git

# Set the working directory to the repository.
WORKDIR /3_mc_5

# ---------------------------------------FRONTEND---------------------------------------
# RUN cd Frontend/
# WORKDIR /3_mc_5/Frontend/GoalFriends

# # Run the Gradle assemble task.
# RUN chmod +x gradlew
# RUN ./gradlew assemble

# # TODO: Upload Artifacts to GitLab.

# # Run the Gradle test task.
# RUN ./gradlew test

# RUN cd ..
# --------------------------------------------------------------------------------------

# ---------------------------------------BACKEND----------------------------------------
RUN cd Backend/
WORKDIR /3_mc_5/Backend/
RUN chmod +x gradlew

RUN ls

# Run the Gradle assemble task.
RUN ./gradlew assemble

# TODO: Move the artifacts to a deploy directory.

# TODO: Upload artifacts to GitLab.

# Run the Gradle test task.
RUN ./gradlew test

# TODO: Deploy the application.

RUN pwd

RUN cd ../deployment

EXPOSE 8080

RUN java -jar backend.jar

#---------------------------------------------------------------------------------------