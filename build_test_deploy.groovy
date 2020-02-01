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
                    dockerImage = docker.build(registry)
                }
            }            
        }
        stage('Test image') { 
            steps {
                script {
                    docker.image(registry).withRun('-p 8080:5000') {c ->
                        sh "curl -i http://localhost:8080/"
                    }
                }
            }
        }
        stage('Push image to DockerHub') { 
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockerhub', url: ''){
                        dockerImage.push()
                    }
                }
            }
        }
    }
}