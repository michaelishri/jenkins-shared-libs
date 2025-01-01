package machine.command

import groovy.json.JsonOutput
import java.text.SimpleDateFormat

class LinuxDistro implements Serializable {
    private final def steps
    private String command = 'cat /etc/os-release'
    private String classification = 'machine.command.linux-distro'
    private String hostname

    LinuxDistro(steps) {
        this.steps = steps
    }

    String getHostname() {
        if(hostname == null) {
            hostname = java.net.InetAddress.getLocalHost().getHostName()
        }

        return hostname
    }

    static setHostname(String computername) {
        hostname = computername
    }

    String getDateTime() {
        def date = new Date()
        def dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateTime.format(date)
    }

    LinkedHashMap get() {
        return [
            class: this.classification,
            execution: [
                command: this.command,
                hostname: this.getHostname(),
                time: this.getDateTime()
            ],
            data: parse()
        ]
    }

    private String parse() {
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

    private String executeCommand(String command) {
        return steps.sh(
            script: command,
            returnStdout: true
        ).trim()
    }

    String toJson(boolean pretty = false) {
        if(pretty) {
            return JsonOutput.prettyPrint(JsonOutput.toJson(get()))    
        }

        return JsonOutput.toJson(get())
    }
}