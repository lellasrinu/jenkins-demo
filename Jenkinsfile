pipeline {
    agent any

    tools {
        // Must match the name defined in Jenkins -> Tools -> JDK / Maven
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    environment {
        APP_NAME = 'springboot-app'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile & Lint') {
            steps {
                echo 'Compiling the application...'
                // Use 'bat' for Windows instead of 'sh'
                bat 'mvnw.cmd clean compile'
            }
        }

        stage('Unit & Integration Tests') {
            steps {
                echo 'Running unit tests...'
                bat 'mvnw.cmd test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                echo "Packaging build #${env.BUILD_NUMBER}..."
                bat "mvnw.cmd package -DskipTests -Dbuild.number=${env.BUILD_NUMBER}"
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded for commit: ${env.GIT_COMMIT}"
        }
        failure {
            echo "Pipeline failed for commit: ${env.GIT_COMMIT}"
        }
    }
}