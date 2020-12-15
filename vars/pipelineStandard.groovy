def call(body) {
    def config= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    pipeline{
        agent {
            node {
                label 'my-defined-label'
            }
        }
        stages{
            stage('Build alejandro') {
                steps {
                    echo 'Bienvenido ${config.name}'
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
