package traits

import com.cloudbees.groovy.cps.NonCPS

@NonCPS
trait DateTimeTrait {
    String getDateTime() {
        return "hello"
    }
}