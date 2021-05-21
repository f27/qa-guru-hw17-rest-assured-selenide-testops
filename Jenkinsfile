def allureNotificationsFile = 'allure-notifications.jar'
def allureNotificationsUrl = 'https://github.com/qa-guru/allure-notifications/releases/download/fr/allure-notifications-2.2.3.jar'
def allureTestOpsProjectId = '164'

pipeline {
    agent any
    tools {
        gradle "Gradle 6.8.3"
    }
    parameters {
        string(name: 'SELENOID_URL', defaultValue: 'selenoid.autotests.cloud')
        choice(name: 'VIDEO_ENABLED',
                choices: ['true', 'false'])
        credentials(name: 'REMOTE_WEB_DRIVER_CRED_ID',
                description: 'Username and password for remote web driver',
                defaultValue: '1933267a-4824-4f65-9bfe-d8a47445ee39',
                credentialType: "usernamePassword",
                required: true)
        string(name: 'THREADS', defaultValue: '2')
        string(name: 'TELEGRAM_CHAT_ID', defaultValue: '-548005165')
        credentials(name: 'TELEGRAM_BOT_TOKEN_ID',
                description: 'Telegram bot token for sending notifications in telegram chat',
                defaultValue: 'c05-fattaft-telegram-token',
                credentialType: "jenkins_secret_text_credentials",
                required: true)
    }
    stages {
        stage('Test') {
            steps {
                withAllureUpload(name: '${JOB_NAME} - #${BUILD_NUMBER}', projectId: allureTestOpsProjectId, results: [[path: 'build/allure-results']], serverId: 'allure-server', tags: 'tags') {
                    withCredentials([
                            usernamePassword(credentialsId: '${REMOTE_WEB_DRIVER_CRED_ID}', usernameVariable: 'REMOTE_WEB_DRIVER_USERNAME', passwordVariable: 'REMOTE_WEB_DRIVER_PASSWORD')
                    ]) {
                        sh 'gradle clean test' +
                                ' -Dthreads="${THREADS}"' +
                                ' -Dweb.remote.driver.url="${SELENOID_URL}"' +
                                ' -Dvideo.enabled="${VIDEO_ENABLED}"' +
                                ' -Dweb.remote.driver.user="${REMOTE_WEB_DRIVER_USERNAME}"' +
                                ' -Dweb.remote.driver.password="${REMOTE_WEB_DRIVER_PASSWORD}"'
                    }
                }
            }
        }
    }

    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
            withCredentials([string(credentialsId: '${TELEGRAM_BOT_TOKEN_ID}', variable: 'TELEGRAM_BOT_TOKEN')]) {
                sh "if [ ! -f '${allureNotificationsFile}' ]; then wget -O '${allureNotificationsFile}' '${allureNotificationsUrl}'; fi"
                sh "java" +
                        " -Dmessenger='telegram'" +
                        " -Dchat.id='${TELEGRAM_CHAT_ID}'" +
                        " -Dbot.token='${TELEGRAM_BOT_TOKEN}'" +
                        " -Dbuild.launch.name='${JOB_NAME} - #${BUILD_NUMBER}'" +
                        " -Dbuild.env='${ENV_URL}'" +
                        " -Dbuild.report.link='${BUILD_URL}'" +
                        " -Dproject.name='${JOB_BASE_NAME}'" +
                        " -Dlang='ru'" +
                        " -Denable.chart='true'" +
                        " -Dallure.report.folder='./allure-report/'" +
                        " -jar ${allureNotificationsFile}"
            }
        }
    }
}