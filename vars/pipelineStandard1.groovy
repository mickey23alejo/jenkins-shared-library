def call(body) {
    def config= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    pipeline{
        agent any
        stages{
            stage('Build alejandro') {
                steps {
                     script {
                        def browsers = ['chrome', 'firefox']
                        for (int i = 0; i < browsers.size(); ++i) {
                            echo "probando el browser ${browsers[i]}."
                        }
                     }
                }
            }
            stage('Test: Alejandro') {
                steps {
                     echo 'Buiding alejandro'
                }
            }
            stage('Sorbinos') {
                steps {
                    echo 'Buiding sobrinos'
                }
            }
        }
    }
}
