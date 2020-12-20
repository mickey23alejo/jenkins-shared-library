def call(int buildNumber) {
  //def call(Map stageParams){
  //if (stageParams.buildNumber % 2 == 0) {
  if (buildNumber  % 2 == 0) {
    pipeline {
      agent { label 'first_slave' }
      stages {
        stage('Even Stage') {
          steps {
            echo "The build number is even"
          }
        }
      }
    }
  } else {
    pipeline {
      agent any
      stages {
        stage('Odd Stage') {
          steps {
            echo "The build number is odd"
          }
        }
      }
    }
  }
}