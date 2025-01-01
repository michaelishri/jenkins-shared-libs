package org.example

class CommandParser {
    def steps = Binding.getVariable("steps")

    String parseCommandOutput(String command) {
        def output = steps.sh(script: command, returnStdout: true).trim()
        return output
    }
}
