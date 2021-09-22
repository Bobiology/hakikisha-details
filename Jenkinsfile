pipeline {
    environment {
    registry = "bobiologist/hakikisha-info"
    registryCredential = 'dockerhub'
    dockerImage = ''
    gitRegistry = "https://github.com/Bobiology/hakikisha-details.git"
    }
    /*agent {
        label 'docker'
    }*/
    agent any
    tools {
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
     }
    stages {
        stage('Initialize'){
            steps{
                echo "PATH = ${M2_HOME}/bin:${PATH}"
                echo "M2_HOME = /opt/maven"
            }
        }
       stage('Cloning Git') {
        steps {
            sh 'git clone $gitRegistry' 
           }
       }
       stage('Build Artifact') {
         steps {
           sh 'mvn -B -DskipTests clean package'
            }
       }
        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy Image') {
          steps{
            script {
                docker.withRegistry('', registryCredential ) {
                dockerImage.push()
             }
           }
         }
        }
      stage('Remove Unused docker image') {
          steps{
              sh "docker rmi $registry:$BUILD_NUMBER"
            }
         }
    }
}
