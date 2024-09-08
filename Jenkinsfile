node() {
	stage('Code Checkout'){
	checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'a6b8c7e3-0073-4cbe-9064-62ff6e76861a', url: 'git@github.com:vrusalovskaya/jenkins.git']])
	}
}