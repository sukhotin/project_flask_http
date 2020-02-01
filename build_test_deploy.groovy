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
                    docker.image(registry).withRun('-p 8080:80') {c ->
                        sh "curl -i http://${hostIp(c)}:8080/"
                    }
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