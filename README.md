# jenkins-steps-override
Override Jenkins Pipeline steps for local development

### Context

This repo is just one part of [a larger project](https://github.com/tdongsi/jenkins-config) for local development of Jenkins Pipeline libraries.
Basic Pipeline steps such as `mail` or `sendSlack`, which require complex setup or communication to external systems, can be mocked in this library.
 
### Configure local Jenkins

The idea is that since Jenkins Global Libraries allow creating our own global variables, we can configure a Library which overrides some built-in steps.
To make our custom global variables/overridden steps active over built-in steps, configure a "Global Pipeline Library" in  "Manage Jenkins > Configure System" as follows:

![Screeshot](images/config.png "Configure")

Note that the option "Legacy SCM" > "File System" is shown as an example, in the context of local development of a Jenkins plugin/library.
It should not stop you from overriding Pipeline steps (e.g., to override `sh` steps to add `timeout`) in a production Jenkins instance with actual, actively maintained library repositories, using "Modern SCM" > "Github" for example.
