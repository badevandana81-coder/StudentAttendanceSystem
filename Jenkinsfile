pipeline {
 agent any
 environment {
    PATH = "/usr/local/bin:${env.PATH}"
}
tools {
    maven 'Maven'
}
 stages {
  stage('Checkout') { steps { checkout scm } }
  stage('Build & Test') { steps { sh 'mvn -B clean verify' } }
  stage('Docker Build') { steps { sh 'docker build -t attendance-system:latest .' } }
 }
 post { always { archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true } }
}
