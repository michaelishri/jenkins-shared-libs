private String hostname

trait getHostname() {
    if(hostname == null) {
        hostname = java.net.InetAddress.getLocalHost().getHostName()
    }

    return hostname
}

trait setHostname(String computername) {
    hostname = computername
}