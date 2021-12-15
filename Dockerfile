FROM gradle:7.3.1-jdk11
ADD . /server
WORKDIR /server
RUN gradle --no-daemon server:installDist
CMD ./server/build/install/server/bin/server