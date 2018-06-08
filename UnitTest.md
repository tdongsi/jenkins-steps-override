

## Background

PipelineUnitTests 


## Static utility methods

## Wrappers in `vars` folder

```groovy buildWrapper.groovy
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
```

In Jenkinsfile, you can call it as follows to use default parameters:

```groovy
buildWrapper {
}
```

or you can set the parameters in the wrapper's body:

```groovy
buildWrapper {
  settings = "dummy.xml"
}
```

You can test that using PipelineUnitTests.

Need to mock some function and variables

You need to printCallStack().

Create a text file. The file name matches the class name.

`testNonRegression("default")` to run regression tests. 

Show diff in IntelliJ.

## Jenkinsfile

```groovy Jenkinsfile
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
```

You can test that using PipelineUnitTests.

You need to printCallStack().

Create a text file.

`testNonRegression("default")` to run regression tests.
