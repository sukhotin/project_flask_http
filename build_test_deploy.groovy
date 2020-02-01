pipeline {
    environment {
        registry = "sukhotin/project_flask_http"
        registryCredential = "dockerhub"
        dockerImage = ""
    }
    agent any 
    stages {
        stage('Build') {
            steps {
                script {
                    echo "NODE_NAME = ${env.NODE_NAME}"
                    dockerImage = docker.build(registry)
                }
            }            
        }
        stage('Test') { 
            steps {
                sh("echo test")
            }
        }
        stage('Deploy') { 
            steps {
                sh("echo deploy") 
            }
        }
    }
}