pipeline {
    agent any
    
    tools {
        maven 'maven'  // Make sure the Maven tool is properly configured in Jenkins for Windows
    }

    stages {
        stage('Checkout Source Code') {
            steps {
                // Cloning the main project repository
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
            }
        }
        
        stage('Build') {
            steps {
                // Running the Maven build on Windows
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    // Publishing JUnit test results
                    junit '**\\target\\surefire-reports\\TEST-*.xml'
                    // Archiving the build artifacts (JAR)
                    archiveArtifacts 'target\\*.jar'
                }
                failure {
                    echo "Build failed. Check logs."
                }
            }
        }

        stage("Deploy to QA") {
            steps {
                echo "Deploying to QA environment"
                // Add Windows-specific deployment commands/scripts here
            }
        }
        
        stage('Regression UI Automation Tests') {
            steps {
                // Error handling to avoid pipeline failure on test failures
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    // Checking out the UI test project
                    git 'https://github.com/NoobSage07/Open_Cart_Java.git'
                    // Running the Maven regression test suite on Windows
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src\\test\\resources\\testrunners\\testng_regression.xml"
                }
            }
            post {
                always {
                    echo "Regression test stage completed"
                }
                failure {
                    echo "Regression tests failed."
                }
            }
        }
        
        stage('Publish Allure Reports') {
            steps {
                script {
                    // Publishing Allure reports
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
            post {
                always {
                    echo "Allure reports generated"
                }
            }
        }
        
        stage('Publish Regression Extent Report') {
            steps {
                // Publishing Extent Report for Regression
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Regression Extent Report',
                    reportTitles: 'Regression Test Execution Report'
                ])
            }
        }
        
        stage("Deploy to Stage") {
            steps {
                echo "Deploying to Staging environment"
                // Add Windows-specific deployment commands/scripts here
            }
        }

        stage('Sanity Automation Test') {
            steps {
                // Error handling to avoid pipeline failure on test failures
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    // Checking out the sanity test project
                    git 'https://github.com/naveenanimation20/May2024POMSeries.git'
                    // Running the Maven sanity test suite in stage environment on Windows
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src\\test\\resources\\testrunners\\testng_sanity.xml -Denv=stage"
                }
            }
            post {
                always {
                    echo "Sanity test stage completed"
                }
                failure {
                    echo "Sanity tests failed."
                }
            }
        }
        
        stage('Publish Sanity Extent Report') {
            steps {
                // Publishing Extent Report for Sanity
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Sanity Extent Report',
                    reportTitles: 'Sanity Test Execution Report'
                ])
            }
        }
        
        stage("Deploy to PROD") {
            steps {
                echo "Deploying to Production environment"
                // Add Windows-specific deployment commands/scripts here
            }
        }
    }

    post {
        // Final stage to always provide feedback after the pipeline completes
        always {
            echo "Pipeline execution completed."
        }
    }
}
