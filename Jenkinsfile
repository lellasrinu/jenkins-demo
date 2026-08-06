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
                sh './mvnw clean compile' // or 'mvn clean compile' if wrapper isn't used
            }
        }

        stage('Unit & Integration Tests') {
            steps {
                echo 'Running unit tests...'
                sh './mvnw test'
            }
            post {
                always {
                    // Publishes JUnit test results in Jenkins UI
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                echo 'Packaging application executable...'
                sh './mvnw package -DskipTests'
            }
        }

        stage('Archive Artifacts') {
            steps {
                // Archives the resulting JAR file so it can be deployed or downloaded
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false
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