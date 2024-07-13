pipeline {
  agent any
  
  tools {
    maven 'maven'
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
        ansiblePlaybook credentialsId: 'ansadminssh', disableHostKeyChecking: true, installation: 'ansible', inventory: 'dev.inv', playbook: 'playbook.yml'
      }
    }
  }
}
//
