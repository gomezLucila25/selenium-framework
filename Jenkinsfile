pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER',      choices: ['chrome-headless', 'chrome', 'firefox', 'edge'], description: 'Browser to run tests')
        choice(name: 'ENV',          choices: ['qa', 'dev'],                                     description: 'Target environment')
        choice(name: 'SUITE',        choices: ['smoke', 'regression'],                           description: 'Test suite to execute')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                sh """
                    mvn clean test \\
                        -Dbrowser=${params.BROWSER} \\
                        -Denv=${params.ENV} \\
                        -Dsuite=${params.SUITE}
                """
            }
        }
    }

    post {
        always {
            // Requirement 9: test results on job graphics
            junit 'target/surefire-reports/**/*.xml'

            // Requirement 9: screenshots archived as artifacts
            archiveArtifacts artifacts: 'target/screenshots/**/*.png',
                             allowEmptyArchive: true,
                             fingerprint: true

            // Archive logs too
            archiveArtifacts artifacts: 'target/logs/**/*.log',
                             allowEmptyArchive: true
        }
        failure {
            echo 'Pipeline failed — check archived screenshots in artifacts'
        }
    }
}
