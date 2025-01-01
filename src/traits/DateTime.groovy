package traits

trait DateTimeTrait implements Serializable {
    @NonCPS
    String getDateTime() {
        return new Date().toString()
    }
}