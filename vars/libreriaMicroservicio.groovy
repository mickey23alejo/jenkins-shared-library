def call(body) {
    def config= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    pipeline{
        agent any
        stages{
            stage('Checkout'){
                checkout([$class: 'TeamFoundationServerScm', projectPath: '$/Microservicios GD/Certification/CERT_INTEGRACION_CDE/Fuentes/API Integracion WMS/GDifare.ZeusComercial.DatosMaestros.API', serverUrl: 'http://192.3.50.160:8080/tfs/Microservicios', useOverwrite: true, useUpdate: true, userName: 'GRUPODIFARE\tfssetup', password: hudson.util.Secret.fromString('PASSWORD'), workspaceName: 'Hudson-${JOB_NAME}-${NODE_NAME}'])
            }
            stage('Build alejandro') {
                steps {
                    sh "cd $WORKSPACE"
                }
            }
        }
    }
}
