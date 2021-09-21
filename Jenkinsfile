pipeline {
    agent {
        label 'docker'
    }
    //  parameters here provide the shared values used with each of the hakikisha pipeline steps.
   /* 
   parameters {
        // The space ID that we will be working with. The default space is typically Spaces-1.
        string(defaultValue: 'Spaces-1', description: '', name: 'SpaceId', trim: true)
        // The hakikisha project we will be deploying.
        string(defaultValue: 'Petclinic', description: '', name: 'ProjectName', trim: true)
        // The environment we will be deploying to.
        string(defaultValue: 'Dev', description: '', name: 'EnvironmentName', trim: true)
        // The name of the hakikisha instance in Jenkins that we will be working with. This is set in:
        // Manage Jenkins -> Configure System -> hakikisha Deploy Plugin
        string(defaultValue: 'hakikisha', description: '', name: 'ServerId', trim: true)
    }
    */
    stages {
        /*
        stage ('Add tools') {
            steps {
                tool('OctoCLI')
            }
        }
        */
        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build "bobiologist/hakikisha-info:$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy our image') {
            steps {
                script {
                    // Assume the Docker Hub registry by passing an empty string as the first parameter
                    /*docker.withRegistry('' , 'dockerhub') {
                        dockerImage.push()
                    }*/
                    docker { 
                    image 'bobiologist/hakikisha-info:$BUILD_NUMBER'
                    registryUrl 'https://hub.docker.com/'
                    //label "test-env"
                    }
                }
                
            }
        }
        /*
        stage('deploy') {
            steps {                                
                octopusCreateRelease deployThisRelease: true, environment: "${EnvironmentName}", project: "${ProjectName}", releaseVersion: "1.0.${BUILD_NUMBER}", serverId: "${ServerId}", spaceId: "${SpaceId}", toolId: 'Default', waitForDeployment: true                
            }
        }*/
    }
}
