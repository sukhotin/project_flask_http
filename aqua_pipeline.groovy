node () {
    def app
    def docker_registry = "sukhotin/project_flask_http"
    def registry_credential = "dockerhub"

    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */
 
        checkout scm
    }
 
    stage('Build image') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
 
        app = docker.build(docker_registry)
    }
 
    stage('Aqua CSP Scanner') {
        /* This step scans the image for high vulnerabilities and
         * FAILS if any are found */
 
        aqua customFlags: '--layer-vulnerabilities', hideBase: false, hostedImage: '', localImage: docker_registry, locationType: 'local', notCompliesCmd: '', onDisallowed: 'fail', policies: '', register: true, registry: 'Docker Hub', showNegligible: true
    }
 
    stage('Test image') {
        /* Ideally, we would run a test framework against our image.
         * Just an example */
        /*
        docker.image("dtr.andreas.dtcntr.net/docker-cemea/my-test-app").inside {
            sh 'curl --fail http://localhost:5000 || exit 1'
        }*/
        sh 'echo "Test successful and passed"'
    }
 
    // stage('Push image') {
    //     /* Finally, we'll push the image with two tags:
    //      * First, the incremental build number from Jenkins
    //      * Second, the 'latest' tag.
    //      * Pushing multiple tags is cheap, as all the layers are reused. */
    //      docker.withRegistry('', registry_credential) {
    //         app.push("acsp")
            
    //     } 
    //     sh 'echo "Image successfully pushed to the registry"'
    // }  
}
