def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    pipeline {
        agent any
        stages {
          stage('Primer paso') {
            steps {
                echo 'Bienvenido ${config.name}'
            }        
          }      
        }    
    }
}