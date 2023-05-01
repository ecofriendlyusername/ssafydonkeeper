pipeline {
  agent any
  stages {
    stage('Build Back Image') {
      steps {
        dir('moneykeeperbackend') {
          sh 'docker build -t donkeeper-be .'
        }
      }
    }
    stage('Build Front Image') {
      steps {
        dir('front') {
          sh 'docker build -t donkeeper-fe .'
        }
      }
    }
    stage('Run Containers') {
      steps {
        sh 'docker run -d --name backend-container donkeeper-be'
        sh 'docker run -d --name frontend-container donkeeper-fe'
      }
    }
  }
}
