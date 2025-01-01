package machine.command

class TraitTesting implements Serializable, LoggingTrait {

    TraitTesting(steps) {
        this.steps = steps
    }

    String getName() {
        return "My name is...."
    }

}