@Library("jenkins-library") _
properties([
  parameters([
    string(name: 'VERSION', defaultValue: '1.0.0', description: '示例参数'),
  ])
])
podTemplate(label: 'dind-pod', containers: getTemplates.getJenkinsAgentTemplate(),
volumes: [emptyDirVolume(memory: false, mountPath: '/var/lib/docker')]) {
    node('dind-pod') {
        properties([disableConcurrentBuilds()])

        ///////////////////////////////////////////////////////////////////////////////
        def repo_name = 'warriortrading.orca.lib.a'
        def image_name = "liba"
        def aws_credentialsId = 'chiron_s3_credential'
        def aws_region = 'us-east-2'
       
        ///////////////////////////////////////////////////////////////////////////////

        def build_config = repoHelper.getBuildImageConfig(repo_name, image_name)

        stage('1. checkout code, build & push image and tag repo') {
            container('docker') {
                echo "start ===> 1. checkout code, build image, push image and tag repo"

                // 验证参数是否正确传入
                echo "VERSION is: ${params.VERSION}"

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
