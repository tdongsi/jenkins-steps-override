package vars

import com.lesfurets.jenkins.unit.BaseRegressionTest
import org.junit.Before
import org.junit.Test

/**
 * Created by tdongsi on 6/3/18.
 */
class JenkinsfileTest extends BaseRegressionTest {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();

    callStackPath = "test/vars/callstacks/"

    /*
    // A more proper variable mocking
    binding.setVariable('scm', [
        $class: 'GitSCM',
        branches: [[name: 'master']],
        doGenerateSubmoduleConfigruations: false,
        extensions: [],
        submoduleCfg: [],
        userRemoteConfigs: [[url: "/var/git-repo"]]
    ])
    */
    binding.setVariable('scm', [:])

    helper.registerAllowedMethod('library', [String.class], null)
  }

  @Test
  public void should_run() throws Exception {
    runScript('test/jenkinsfiles/demo.groovy')
    // printCallStack()
    testNonRegression()
  }
}