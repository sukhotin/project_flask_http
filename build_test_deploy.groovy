pipeline {
    environment {
        registry = "sukhotin/project_flask_http"
        registryCredential = "dockerhub"
        dockerImage = ""
    }
    agent any 
    stages {
        stage('Build docker image') {
            steps {
                script {
                    dockerImage = docker.build("${registry}:${env.BUILD_ID}")
                }
            }            
        }
        stage('Test image') { 
            steps {
                script {
                    dockerImage.image(registry).withRun("")
                }
            }
        }
        stage('Deploy') { 
            steps {
                sh("echo deploy") 
            }
        }
    }
}