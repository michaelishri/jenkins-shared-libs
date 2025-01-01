import java.text.SimpleDateFormat

trait GetDateTime() {
    def date = new Date()
    def dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateTime.format(date)
}