def call(body) {
    def config= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    def label_name = config.agent?: 'mvn3.6.0-jdk8'
    pipeline{
        agent { 
            label "${label_name}"
        }
        stages{
            stage('Build') {
                steps {
                    sh "mvn clean install -B -f ${config.pom}"
                }
            }
            stage("Test: SonarQube") {
                steps {
                    sh "mvn sonar:sonar -B -f ${config.pom}"
                }
            }
            stage('Publish Artifactory') {
                steps {
                    publishArtifactory(".*.${config.artifact}")
                }
            }
        }
        post{
            always{
                messageEmail("")
            }
        }
    }
}
