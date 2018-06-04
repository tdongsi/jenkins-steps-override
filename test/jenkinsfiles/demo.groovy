/**
 * Created by tdongsi on 6/3/18.
 * This is a real Jenkinsfile under test.
 */

node() {
  stage('Checkout') {
    checkout scm
    sh 'git clean -xdf'
  }

  stage('Build and test') {
    sh './gradlew build'
    junit 'build/test-results/test/*.xml'
  }
}