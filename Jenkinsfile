@Library("jenkins-library") _
podTemplate(label: 'dind-pod', containers: getTemplates.getJenkinsAgentTemplate(),
volumes: [emptyDirVolume(memory: false, mountPath: '/var/lib/docker')]) {
    node('dind-pod') {
        properties([disableConcurrentBuilds()])

        ///////////////////////////////////////////////////////////////////////////////
        def repo_name = 'warriortrading.orca.lib.a'
        def image_name = "liba"
        def aws_credentialsId = 'chiron_s3_credential'
        def aws_region = 'us-east-2'

        environment {
               C_VERSION="${params.CURRENT_VERSION}"
        }
       
        ///////////////////////////////////////////////////////////////////////////////
        def build_config = repoHelper.getBuildImageConfig(repo_name, image_name)

        stage('1. checkout code, build & push image and tag repo') {
            container('docker') {
                echo "start ===> 1. checkout code, build image, push image and tag repo"
                echo "branch_name = ${BRANCH_NAME}"
                echo "VERSION = ${env.C_VERSION}"

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
