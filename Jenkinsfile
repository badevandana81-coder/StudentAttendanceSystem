pipeline {
 agent any
 stages {
  stage('Checkout') { steps { checkout scm } }
  stage('Build & Test') { steps { sh 'mvn -B clean verify' } }
  stage('Docker Build') { steps { sh 'docker build -t attendance-system:latest .' } }
 }
 tools {
    maven 'Maven'
}
 post { always { archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true } }
}
