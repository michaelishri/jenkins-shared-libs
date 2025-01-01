package org.example

// import groovy.transform.Field

// @groovy.transform.Field
class CommandParser implements Serializable {
    private final def steps

    CommandParser(steps) {
        this.steps = steps
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