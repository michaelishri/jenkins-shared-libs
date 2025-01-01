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

    public String parse(String os_release = "") {
        if(os_release.isEmpty()) {
            def raw = executeCommand(command).trim()
        }
        
        def osReleaseMap = [:]
        def lines = raw.split('\n')

        lines.each{ line ->
            // Skip comments
            if(line && !line.startsWith('#')) {
                // Split on first equals sign
                def parts = line.split('=', 2)
                if (parts.size() == 2) {
                    def key = parts[0].trim()
                    def value = parts[1].trim()
                    
                    // Remove surrounding quotes if present
                    if ((value.startsWith('"') && value.endsWith('"')) ||
                        (value.startsWith("'") && value.endsWith("'"))) {
                        value = value[1..-2]
                    }
                    
                    // Convert common boolean strings
                    if (value.toLowerCase() in ['true', 'false']) {
                        value = value.toBoolean()
                    }
                    // Try to convert to number if possible
                    else if (value.isNumber()) {
                        value = value.toBigDecimal()
                    }
                    
                    osReleaseMap[key] = value
                }
            }
        }

        return osReleaseMap
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