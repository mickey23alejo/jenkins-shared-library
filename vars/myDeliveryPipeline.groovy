def call(body) {
    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent any
            stage('Primer paso') {
                steps {
                    echo 'Bienvenido ${config.name}'
                }
            }
    }