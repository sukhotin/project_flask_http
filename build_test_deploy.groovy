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