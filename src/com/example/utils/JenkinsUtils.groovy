package com.example.utils

class JenkinsUtils implements Serializable {

  /**
   * Check if current Jenkins run is triggered by timer.
   *
   * Example usage in Jenkinsfile:
   * TODO
   *
   * @param script
   *
   */
  static def isTimerTrigger(script) {
    return false
  }

  /**
   * Return the user ID that triggered the current Jenkins run.
   *
   * Example usage in Jenkinsfile:
   * TODO
   *
   * @param script
   * @return
   */
  static def getBuildUser(script) {
    return 'timer'
  }



}
