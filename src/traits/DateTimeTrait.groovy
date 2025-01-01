package traits

import com.cloudbees.groovy.cps.NonCPS

trait DateTimeTrait {
    @NonCPS
    String getDateTime() {
        return "hello"
    }
}