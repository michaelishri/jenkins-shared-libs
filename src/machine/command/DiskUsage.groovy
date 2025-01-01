package machine.command

class DiskUsage implements Serializable {
    private final def steps
    private command = 'df -a'

    DiskUsage(steps) {
        this.steps = steps
    }

    String getHostname() {
        return executeCommand('cat /etc/hostname')
    }

    String getDateTime() {
        return executeCommand('date +"%Y-%m-%dT%H:%M:%S"')
    }

    public parse() {
        def raw = executeCommand(command)
        return raw
    }


    String executeCommand(String command) {
        return steps.sh(
            script: command,
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