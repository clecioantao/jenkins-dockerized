FROM jenkins/jenkins:2.176.3

LABEL MAINTAINER "Ramanathan.M"

ENV JENKINS_ADMIN_USER admin
ENV JENKINS_ADMIN_PASS admin

USER jenkins

# JENKINS_HOME
VOLUME /var/lib/jenkins

ENV JENKINS_OPTS --httpPort=8080

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

COPY init.groovy.d/skip-2.0-setup-wizard.groovy /usr/share/jenkins/ref/init.groovy.d/skip-2.0-setup-wizard.groovy

COPY init.groovy.d/create_admin_user.groovy /usr/share/jenkins/ref/init.groovy.d/create_admin_user.groovy

COPY init.groovy.d/executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy

RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

EXPOSE 8080
