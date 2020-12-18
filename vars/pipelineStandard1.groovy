def call(body) {
    def config= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    pipeline{
        agent any
        environment {
            CRED_USUARIO = credentials('USUARIO_ALEJO')
            SECRET_TEXT = credentials('USUARIO_ALEJO')
        }
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
            stage('Variables y texto ocualto') {
                steps {
                    sh '''
                    echo "El usuario es $CRED_USUARIO_USR
                    echo "La contraseña es $CRED_USUARIO_PSW"'
                    echo "La contrasenia es $SECRET_TEXT"
                    '''
                }
            }
        }
    }
}
