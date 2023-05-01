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
        dir('frontend') {
          sh 'docker build -t donkeeper-fe .'
        }
      }
    }
    stage('Run Containers') {
      steps {
        sh 'docker run -d -p 8080:8080 --name backend-container donkeeper-be'
        sh 'docker run -d -p 3000:3000 --name frontend-container donkeeper-fe'
      }
    }
  }
}
