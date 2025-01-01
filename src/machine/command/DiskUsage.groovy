package machine.command

class DiskUsage implements Serializable {
    private final def steps

    DiskUsage(steps) {
        this.steps = steps
    }

    String getHostname() {
        return executeCommand('cat /etc/hostname')
    }

    String getDateTime() {
        return executeCommand('date +"%Y-%m-%dT%H:%M:%S"')
    }

    String command() {
        return executeCommand('df -a')
    }

    public parse() {
        def output = command()
        return output
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