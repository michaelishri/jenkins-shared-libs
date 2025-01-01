package machine.command

import groovy.json.JsonOutput
// import java.text.SimpleDateFormat
// import DateTime
// import traits.DateTimeTrait

class EnvironmentVariables implements Serializable, LoggingTrait {
    private final def steps
    private String command = 'printenv | sort'
    private String classification = 'machine.command.printenv'
    private String hostname

    EnvironmentVariables(steps) {
        this.steps = steps
    }

    String getHostname() {
        if(hostname == null) {
            hostname = java.net.InetAddress.getLocalHost().getHostName()
        }

        return hostname
    }

    def setHostname(String computername) {
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

    private parse() {
        def raw = executeCommand(command).trim()
        
        def envs = [:]
        def lines = raw.split('\n')

        lines.each{ line ->
            // Split on first equals sign
            def parts = line.split('=', 2)
            if (parts.size() == 2) {
                def key = parts[0].trim()
                def value = parts[1].trim()
                
                // Convert common boolean strings
                if (value.toLowerCase() in ['true', 'false']) {
                    value = value.toBoolean()
                }
                // Try to convert to number if possible
                else if (value.isNumber()) {
                    value = value.toBigDecimal()
                }
                
                envs[key] = value
            }
        }

        return envs
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