package machine.command

trait LoggingTrait {
    void log(String message) {
        echo "[LOG] ${message}"
    }
}