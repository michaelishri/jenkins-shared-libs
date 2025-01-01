package machine.command

import groovy.json.JsonOutput

class DiskUsage implements Serializable {
    private final def steps
    private command = 'df -a'
    private classification = 'machine.command.df'

    DiskUsage(steps) {
        this.steps = steps
    }

    String getHostname() {
        return executeCommand('cat /etc/hostname')
    }

    String getDateTime() {
        return executeCommand('date +"%Y-%m-%dT%H:%M:%S"')
    }

    def get() {
        def ingest = [
            class: this.classification,
            execution: [
                command: this.command,
                hostname: this.getHostname(),
                time: this.getDateTime()
            ],
            data: parse()
        ]

        return ingest

        // return toJson(ingest)
    }

    def String parse() {
        def raw = executeCommand(command)
        def lines = raw.split('\n')
        def headers = lines[0].split(/\s+/) // Extract the headers from the first line
        def dataLines = lines[1..-1] // Extract the rest of the lines (the data)
        
        def result = dataLines.collect { line ->
            def values = line.split(/\s+/)
            [headers, values].transpose().collectEntries() // Map headers to values
        }

        return result
    }

    def toJson(data) {
        return JsonOutput.prettyPrint(JsonOutput.toJson(data))
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