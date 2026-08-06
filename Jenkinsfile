pipeline {
    agent any

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'qa'],
            description: 'Select the target environment profile to build'
        )
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '15'))
    }

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
                echo "Packaging build #${env.BUILD_NUMBER}..."
                // Pass the unique Jenkins build number into the Maven build
                sh "./mvnw package -DskipTests -Dspring.profiles.active=${params.ENVIRONMENT} -Dbuild.number=${env.BUILD_NUMBER}"
            }
        }

        stage('Archive Artifacts') {
            steps {
                // Archives the resulting JAR file so it can be deployed or downloaded
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
