package traits


trait DateTime implements Serializable {
    String getDateTime() {
        def date = new Date()
        def dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateTime.format(date)
    }
}