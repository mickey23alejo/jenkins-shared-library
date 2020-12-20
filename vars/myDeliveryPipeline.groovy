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
                //sh '/usr/bin/docker build -f Dockerfile -t ${config.name}-image:v1.0.$BUILD_NUMBER .'
                /*sh '''
                cd $WORKSPACE
                cd "/tmp/${config.name}"
                '''*/
                sh 'echo ${config.name}'
            }        
          }
          stage('Conexion ssh') {
            steps{
              sh 'ssh root@192.3.50.101 "kubectl apply -f http://192.3.50.170:8081/repository/maven-releases/pryqa/cadcargamasiva/v1/cadcargamasiva-v1-kubernetes.yaml && kubectl set image deployment.v1.apps/cadcargamasiva-deploy cadcargamasiva=192.3.50.170:8083/qa-cadcargamasiva-image:v1.0.$BUILD_NUMBER -n kube-prynep01  --record=true"'
            }
          }      
        }    
    }
}