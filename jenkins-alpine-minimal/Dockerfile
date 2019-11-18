# Referenced from, https://raw.githubusercontent.com/jenkinsci/docker/master/Dockerfile-alpine
FROM jenkins/jenkins:2.176.3

LABEL MAINTAINER "Ramanathan.M"

ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000

ARG http_port=8080

ARG JENKINS_HOME=/var/jenkins_home
ARG REF=/usr/share/jenkins/ref

ENV JENKINS_HOME $JENKINS_HOME
ENV REF $REF

# Customize creds for the admin, as needed.
# Used by the admin user creation Groovy code.
ENV JENKINS_ADMIN_USER admin
ENV JENKINS_ADMIN_PASS admin

# Use https with cert for secure setup
ENV JENKINS_OPTS --httpPort=8080

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Jenkins is run with user `jenkins`, uid = 1000
# If you bind mount a volume from the host or a data container,
# ensure you use the same uid
USER root

RUN mkdir -p $JENKINS_HOME \
  && chown ${uid}:${gid} $JENKINS_HOME \
  && adduser -h "$JENKINS_HOME" -u ${uid} -G ${group} -s /bin/bash -D ${user}

# Jenkins home directory is a volume, so configuration and build history
# can be persist, and survive image upgrades
VOLUME $JENKINS_HOME

# $REF (defaults to `/usr/share/jenkins/ref/`) contains all reference configuration we want
# to set on a fresh new installation. Use it to bundle additional plugins
# or config file with your custom jenkins Docker image.
RUN mkdir -p ${REF}/init.groovy.d

RUN chown -R ${user} "$JENKINS_HOME" "$REF"

# for main web interface:
EXPOSE ${http_port}

USER ${user}

# skip initial setup wizard
COPY init.groovy.d/skip-2.0-setup-wizard.groovy ${REF}/init.groovy.d/skip-2.0-setup-wizard.groovy

# do not prompt to create admin user
COPY init.groovy.d/create_admin_user.groovy ${REF}/init.groovy.d/create_admin_user.groovy

# Not to be used in PRODUCTION - master should _not_ run build jobs
COPY init.groovy.d/executors.groovy ${REF}/init.groovy.d/executors.groovy

# Desired list of plugins and their version can be pinned
COPY plugins.txt ${REF}/plugins.txt
RUN /usr/local/bin/install-plugins.sh < ${REF}/plugins.txt
