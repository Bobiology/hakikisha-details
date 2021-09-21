pipeline {
    environment {
    registry = "bobiologist/hakikisha-info"
    registryCredential = 'dockerhub'
    dockerImage = ''
    gitRegistry = "https://github.com/Bobiology/hakikisha-info"
    }
    agent {
        label 'docker'
    }
    stages {
        stage ('Add tools') {
            steps {
                tools {
                    maven 'Maven 3.3.8'
                    jdk 'jdk8'
                }
            }
        }
        stage('Initialize'){
            steps{
                echo "PATH = ${M2_HOME}/bin:${PATH}"
                echo "M2_HOME = /opt/maven"
            }
        }
       stage('Cloning Git') {
        steps {
            git gitRegistry
           }
       }
       stage('Build Artifact') {
         steps {
           sh 'mvn clean install'
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
