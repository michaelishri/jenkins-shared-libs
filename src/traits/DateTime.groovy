package traits

trait DateTime implements Serializable {
    @NonCPS
    String getDateTime() {
        return new Date().toString()
    }
}