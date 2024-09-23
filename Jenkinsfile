node() {

	stage('Code Checkout') {
		checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '<specify git credentials>', url: 'git@github.com:saddenly/project.git']])
	}

	stage('Build Automation') {
		sh "ls -lart"
		sh "/opt/apache-maven-3.9.9/bin/mvn clean package -Dmaven.test.skip"
		sh "ls -lart target"
	}

	stage('Deploy') {
		deploy adapters: [tomcat9(credentialsId: '<specify tomcat credentials>', path: '', url: '<tomcat url>')], contextPath: 'journalApp', onFailure: false, war: '**/*.war'
	}
}