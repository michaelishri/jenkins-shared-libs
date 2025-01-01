package traits

import com.cloudbees.groovy.cps.NonCPS

trait DateTime implements Serializable {
    @NonCPS
    String getDateTime() {
        // Using a simpler date formatting approach
        def now = new Date()
        return now.format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('UTC'))
    }
}