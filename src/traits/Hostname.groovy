package traits

trait Hostname implements Serializable {
    private String hostname

    String getHostname() {
        if (hostname == null) {
            hostname = java.net.InetAddress.getLocalHost().getHostName()
        }
        return hostname
    }

    def setHostname(String computername) {
        hostname = computername
    }
}