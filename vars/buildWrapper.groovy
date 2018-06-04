/**
 * Created by tdongsi on 6/3/18.
 */

def call(Closure body) {
  def config = [:]

  if (body != null) {
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
  }

  def settings = config.settings ?: "settings.xml"

  node('java-agent') {
    stage('Checkout') {
      checkout scm
    }

    stage('Main') {
      // Test Python setup
      sh(script: 'python -c "import requests"', returnStatus: true)
      // Test Docker setup
      sh 'docker version'
    }

    stage('Post') {
      // Print info of standard tools
      sh 'ls -al'
      sh 'java -version'
      sh "mvn -s $settings -version"
      sh 'python -V'
    }
  }
}