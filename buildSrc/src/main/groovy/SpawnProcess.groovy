import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SpawnProcess extends DefaultTask {
    List commandLine
    String ready
    Map environment
    String workingDir

    @TaskAction
    def exec() {
        ProcessBuilder builder = new ProcessBuilder(commandLine)
        builder.redirectErrorStream(true)
        builder.directory(new File(workingDir))

        Map env = builder.environment()
        environment.each { k,v -> env.put(k,v) }

        println "Executing command:\n${builder.command().join(' ')}"
        Process process = builder.start()
        InputStream stdout = process.getInputStream()
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout))
        def line
        while ((line = reader.readLine()) != null) {
            println line
            if (line.contains(ready)) {
                println "${builder.command().join(' ')} is ready"
                break;
            }
        }
    }

}