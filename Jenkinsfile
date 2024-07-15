pipeline {
  agent { label 'ansible'}
  
  tools {
    maven 'maven'
    jdk 'JAVA_HOME'
  }
  
  stages {
    stage('Build Artifact') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Deploy to server using Ansible') {
      steps {
        ansiblePlaybook credentialsId: 'ansadminssh', installation: 'ansible', inventory: 'dev.inv', playbook: 'playbook.yml'
      }
    }
  }
}
//
