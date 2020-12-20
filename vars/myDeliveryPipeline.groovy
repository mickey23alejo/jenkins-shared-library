def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    pipeline {
        agent { label 'first_slave' }
        stages {
          stage('Docker build') {
            steps {
                sh 'cd $WORKSPACE'
                sh "/usr/bin/docker build -f Dockerfile -t qa-'${config.name}'-image:v1.0.$BUILD_NUMBER ."
                //sh "mkdir '${config.name}'"
                /*sh '''
                cd $WORKSPACE
                cd "/tmp/'${config.name}'"
                '''*/
                //sh "cd '${config.name}'"
                
            }        
          }
          stage('Push to Nexus'){
            steps{
              sh 'echo "$NEXUS_PASSWORD" | /usr/bin/docker login -u $NEXUS_USER --password-stdin 192.3.50.170:8082'
              sh 'echo "$NEXUS_PASSWORD" | /usr/bin/docker login -u $NEXUS_USER --password-stdin 192.3.50.170:8083'
              sh "/usr/bin/docker tag qa-'${config.name}'-image:v1.0.$BUILD_NUMBER 192.3.50.170:8083/qa-'${config.name}'-image:v1.0.$BUILD_NUMBER"
              sh "/usr/bin/docker push 192.3.50.170:8083/qa-'${config.name}'-image:v1.0.$BUILD_NUMBER"
              /*echo "$NEXUS_PASSWORD"
              sh "/usr/bin/docker login -u $NEXUS_USER --password-stdin 192.3.50.170:8082"
              echo "$NEXUS_PASSWORD" 
              sh "/usr/bin/docker login -u $NEXUS_USER --password-stdin 192.3.50.170:8083"
              sh "/usr/bin/docker tag qa-'${config.name}'-image:v1.0.$BUILD_NUMBER 192.3.50.170:8083/qa-cadcargamasiva-image:v1.0.$BUILD_NUMBER"
              sh "/usr/bin/docker push 192.3.50.170:8083/qa-'${config.name}'-image:v1.0.$BUILD_NUMBER"*/
            }
          }
          /*stage('Conexion ssh') {
            steps{
              //sh 'ssh root@192.3.50.101 "kubectl apply -f http://192.3.50.170:8081/repository/maven-releases/pryqa/cadcargamasiva/v1/cadcargamasiva-v1-kubernetes.yaml && kubectl set image deployment.v1.apps/cadcargamasiva-deploy cadcargamasiva=192.3.50.170:8083/qa-cadcargamasiva-image:v1.0.$BUILD_NUMBER -n kube-prynep01  --record=true"'
              sh "ssh root@192.3.50.101 'kubectl apply -f http://192.3.50.170:8081/repository/maven-releases/pryqa/cadcargamasiva/v1/cadcargamasiva-v1-kubernetes.yaml && kubectl set image deployment.v1.apps/cadcargamasiva-deploy cadcargamasiva=192.3.50.170:8083/${config.name}-image:v1.0.$BUILD_NUMBER -n kube-prynep01  --record=true'"
            }
          } */
        }    
    }
}