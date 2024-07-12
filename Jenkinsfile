pipeline {
  agent { label 'jenkins-slave'}

  environment {
    NEXUS_CREDENTIAL_ID = 'Nexus-Credential'
    VERSION = "${env.BUILD_ID}-${env.BUILD_TIMESTAMP}".replaceAll(/[\s:]/, "-")
  }

  tools {
    maven 'Maven-Test'
    jdk 'OpenJDK17'
  }
  
  stages {
    stage('Build Artifact') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Deploy to server using Ansible') {
      steps {
        ansiblePlaybook credentialsId: 'newuseransadmin', installation: 'Ansible', inventory: 'dev.inv', playbook: 'playbook.yml'
      }
    }
  }
}
//
