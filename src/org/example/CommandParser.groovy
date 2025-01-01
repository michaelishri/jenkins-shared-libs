package org.example

import com.cloudbees.groovy.cps.NonCPS
import org.jenkinsci.plugins.workflow.cps.CpsScript

class CommandParser implements Serializable {
    // Remove the steps field and constructor
    
    def script = null
    
    // This method will be called automatically by Jenkins
    def void setScript(CpsScript script) {
        this.script = script
    }

    String executeAndParse(String command) {
        // Use sh step directly via script object
        def output = script.sh(
            script: command,
            returnStdout: true
        ).trim()
        
        // Add your parsing logic here
        return parseOutput(output)
    }
    
    @NonCPS
    private String parseOutput(String output) {
        // Add parsing logic here
        // @NonCPS annotation is needed for complex operations
        // that aren't CPS-transformed
        return output
    }
}
