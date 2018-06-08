package vars

import com.lesfurets.jenkins.unit.BaseRegressionTest
import org.junit.Before
import org.junit.Test

/**
 * Created by tdongsi on 6/3/18.
 */
class BuildWrapperTest extends BaseRegressionTest {

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
  public void configured() throws Exception {
    /*
    Represent the call:
    buildWrapper {
      settings = "dummy.xml"
    }
    */
    def script = loadScript('vars/buildWrapper.groovy')
    script.call({settings = "dummy.xml"})

    // printCallStack()
    testNonRegression("configured")
  }

  @Test
  public void default_value() throws Exception {
    /*
    Represent the call:
    buildWrapper {
    }
    */
    def script = loadScript('vars/buildWrapper.groovy')
    script.call({})

    // printCallStack()
    testNonRegression("default")
  }
}
