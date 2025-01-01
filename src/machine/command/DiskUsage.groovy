package machine.command

class DiskUsage implements Serializable {
    private final def steps
    private String hostname
    private String datetime

    DiskUsage(steps) {
        this.steps = steps
        // this.hostname = getHostname()
    }

    String getHostname() {
        return steps.sh(
            script: 'cat /etc/hostname',
            returnStdout: true
        ).trim()
    }

    String executeAndParse(String command) {
        // Use the sh step via the steps object
        def output = steps.sh(
            script: command,
            returnStdout: true
        ).trim()
        
        // Add your parsing logic here
        return output
    }
}