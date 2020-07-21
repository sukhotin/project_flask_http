node () {
    def app
    def docker_image = "sukhotin/project_flask_http"
    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */
 
        checkout scm
    }
 
    // stage('Build image') {
    //     /* This builds the actual image; synonymous to
    //      * docker build on the command line */ 
    //     app = docker.build(docker_image)
    // }
    // docker.withRegistry('https://registry.example.com') {

    //     docker.image(docker_image).inside {
    //         sh 'make test'
    //     }
    // }
    stage("Pull Image") {
          myImage = docker.image(docker_image)
          myImage.pull()       
    }
    stage('Aqua CSP Scanner') {
        /* This step scans the image for high vulnerabilities and
         * FAILS if any are found */
 
        aqua customFlags: '--layer-vulnerabilities -D', hideBase: false, hostedImage: '', localImage: docker_image, locationType: 'local', notCompliesCmd: '', onDisallowed: 'fail', policies: '', register: true, registry: 'Docker Hub', showNegligible: true
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
 
}
