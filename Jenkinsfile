pipeline {
    environment {
        registry = "sukhotin/project_flask_http"
        registryCredential = "dockerhub"
        deployment = "project-flask.yml"
        dockerImage = ""
        get_dns_name_script = "aws elb describe-load-balancers --region us-east-1 --query 'LoadBalancerDescriptions[*].DNSName' --output text"      
        dns_name = ""
    }
    agent any 
    stages {
        stage("Build a docker image") {
            steps {
                script {
                    dockerImage = docker.build(registry)
                }
            }            
        }
        stage("Test the image") { 
            steps {
                script {
                    docker.image(registry).withRun('-p 8080:5000') {c ->
                        sh "curl -i http://localhost:8080/"
                    }
                }
            }
        }
        stage("Push the image to DockerHub") { 
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockerhub', url: ''){
                        dockerImage.push()
                    }
                }
            }
        }
        stage("Deploy the app image to K8s") { 
            steps {
                script {
                    sh "kubectl apply -f ${deployment}"
                }
            }
        }
        stage("Wait for load balanser"){
            steps {
                script {
                   dns_name = sh(returnStdout: true, script: "${ get_dns_name_script }" )
                   sh("while ! nc -zv ${dns_name} 80; do sleep 10; done") 
                }
            }
        }
    }
}