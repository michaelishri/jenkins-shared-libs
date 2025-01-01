import java.text.SimpleDateFormat
import com.cloudbees.groovy.cps.NonCPS

trait DateTime {
    @NonCPS
    String getDateTime() {
        def date = new Date()
        def dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateTime.format(date)
    }
}