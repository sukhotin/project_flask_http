def dockerImage = ""
pipeline {
    environment {
        registry = "sukhotin/project_flask_http"
        registryCredential = "dockerhub"

    }
    agent any 
    stages {
        stage('Build') {
            steps {
                dockerImage = docker.build(registry)
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