package com.example.utils

import hudson.model.Cause
import hudson.triggers.TimerTrigger

/**
 * Utility methods for various Jenkinsfile needs.
 *
 * @author tdongsi
 */
class JenkinsUtils implements Serializable {

  /**
   * Check if current Jenkins run is triggered by timer.
   *
   * Example usage in Jenkinsfile:
   *
   * <pre>
   * {@code
   * import static com.example.utils.JenkinsUtils.*
   * isTimerTrigger(this)
   * }
   * </pre>
   *
   * @param script: the current Jenkinsfile.
   * @return True if the current Jenkins run is triggered by timer.
   */
  static boolean isTimerTrigger(script) {

    def cause = script.currentBuild.rawBuild.getCause(TimerTrigger.TimerTriggerCause)

    if (cause) {
      return true
    }

    return false
  }

  /**
   * Return the user ID that triggered the current Jenkins run.
   * This utility method is an alternative to Jenkins plugin `build-user-vars-plugin` to identify who triggered the build.
   *
   * Example usage in Jenkinsfile:
   *
   * <pre>
   * {@code
   * import static com.example.utils.JenkinsUtils.*
   * getBuildUser(this)
   * }
   * </pre>
   *
   * @param script: the current Jenkinsfile.
   * @return User ID of the user that triggered the Jenkins run. 'timer' if triggered by timer. Otherwise, 'unknown'.
   */
  static String getBuildUser(script) {

    if ( isTimerTrigger(script) ) {
      return 'timer'
    }

    def cause = script.currentBuild.rawBuild.getCause(Cause.UserIdCause)

    return cause ? cause.getUserId() : 'unknown'
  }

}
