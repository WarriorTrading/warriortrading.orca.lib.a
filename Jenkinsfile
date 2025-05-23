@Library("jenkins-library") _
podTemplate(
    label: 'dind-pod',
    yaml: """
apiVersion: v1
kind: Pod
spec:
  containers:
    - name: docker
      image: warriortrading/mvn_jdk11_compiler:IMAGE-5
      tty: true
      command:
        - dockerd
        - --host=unix:///var/run/docker.sock
        - --host=tcp://0.0.0.0:2375
        - --storage-driver=overlay
      securityContext:
        privileged: true
      volumeMounts:
        - mountPath: /var/lib/docker
          name: volume-0
        - mountPath: /home/jenkins/agent
          name: workspace-volume
  volumes:
    - name: volume-0
      emptyDir: {}
    - name: workspace-volume
      emptyDir: {}
"""
) {
    node('dind-pod') {
        properties([disableConcurrentBuilds()])

        ///////////////////////////////////////////////////////////////////////////////
        def repo_name = 'warriortrading.orca.lib.a'
        def image_name = "liba"
        def aws_credentialsId = 'chiron_s3_credential'
        def aws_region = 'us-east-2'
       
        ///////////////////////////////////////////////////////////////////////////////

        stage('0. get project version') {
            container('docker') {
                script {
                    def version = readFile('version.txt').trim()
                    env.PROJECT_VERSION = version
                    echo "当前版本号: ${env.PROJECT_VERSION}"
                }
            }
        }

        def build_config = repoHelper.getBuildImageConfig(repo_name, image_name)

        stage('1. checkout code, build & push image and tag repo') {
            container('docker') {
                echo "start ===> 1. checkout code, build image, push image and tag repo"

                withCredentials([
                    usernamePassword(credentialsId: aws_credentialsId, passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')
                    ]) {
                    build_args="AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID};AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY};AWS_REGION=${aws_region}"
                    repoHelper.buildDockerImageAndTagRepo(build_config, true, build_args)
                }

                echo "finish ===> 1. checkout code, build image, push image and tag repo"
            }
        }
    }
}
