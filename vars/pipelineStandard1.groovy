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
                    sh "cd $WORKSPACE"
                }
            }
            stage('Test: Alejandro') {
                steps {
                     echo 'Bienvenido ${config.name}'
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
