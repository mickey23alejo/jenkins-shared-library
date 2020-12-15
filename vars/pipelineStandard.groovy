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
                    sh "echo ${config.nombre}"
                }
            }
            stage('Test: Alejandro') {
                steps {
                    sh "Estimado señores echo ${config.nombre} son las"
                }
            }
            stage('Sorbinos') {
                steps {
                    sh "echo ${config.nombre}"
                }
            }
        }
    }
}
