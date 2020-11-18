pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'Starting Build'
        sh 'mvn clean install'
        echo 'Finished Build'
      }
    }

    stage('test') {
      parallel {
        stage('test') {
          steps {
            echo 'Testing now on some server'
          }
        }

        stage('sonar') {
          steps {
            echo 'Sonar Now'
          }
        }

      }
    }

    stage('deploy') {
      steps {
        echo 'Now Deploy'
      }
    }

  }
}