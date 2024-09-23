node() {

	stage('Code Checkout') {
		checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'a6b8c7e3-0073-4cbe-9064-62ff6e76861a', url: 'git@github.com:vrusalovskaya/jenkins.git']])
	}

	stage('Build Automation') {
		sh "ls -lart"
		sh "/opt/apache-maven-3.9.9/bin/mvn clean package -Dmaven.test.skip"
		sh "ls -lart target"
	}

	stage('Deploy') {
		deploy adapters: [tomcat9(credentialsId: 'e473591d-767f-46fe-a100-d427342c6f38', path: '', url: 'http://13.60.83.249:8085/')], contextPath: 'journalApp', onFailure: false, war: '**/*.war'
	}
}
