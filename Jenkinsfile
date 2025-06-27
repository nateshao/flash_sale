pipeline {
    agent any
    environment {
        REGISTRY = "your-docker-registry"
        IMAGE = "${REGISTRY}/seckill-service:${BUILD_NUMBER}"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'cd seckill-service && mvn clean package -DskipTests'
            }
        }
        stage('Unit Test') {
            steps {
                sh 'cd seckill-service && mvn test'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE seckill-service/'
            }
        }
        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin $REGISTRY'
                    sh 'docker push $IMAGE'
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'kubectl set image deployment/seckill-service seckill-service=$IMAGE --record'
            }
        }
    }
    post {
        failure {
            echo 'Build failed!'
            // 可加自动回滚逻辑
        }
        success {
            echo 'Build and deploy succeeded!'
        }
    }
} 