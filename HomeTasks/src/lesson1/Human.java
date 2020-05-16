package lesson1;

public class Human implements Participant {
    private String name;
    private int runLimit;
    private int jumpLimit;
    private boolean takePart;

    public Human(String name, int runLimit, int jumpLimit) {
        this.name = name;
        this.runLimit = runLimit;
        this.jumpLimit = jumpLimit;
        this.takePart = true;
    }

    public String getName() {
        return name;
    }

    public int getRunLimit() {
        return runLimit;
    }

    public int getJumpLimit() {
        return jumpLimit;
    }

    @Override
    public void runInfo() {
        System.out.printf("Human %s can run, but with limit of %d meters.%n", getName(), getRunLimit());
    }

    @Override
    public void jumpInfo() {
        System.out.printf("Human %s can jump, but with limit of %d meters.%n", getName(), getJumpLimit());
    }

    @Override
    public void running(Barrier barriers) {
        if (barriers.trackLength() <= runLimit)
            System.out.printf("Human %s successfully ran the distance - %d meters. %n", getName(), barriers.trackLength());
        else {
            System.out.printf("Human %s cannot run the distance %d meters and loses. %n", getName(), barriers.trackLength());
            takePart = false;

        }
    }

    @Override
    public void jumping(Barrier barriers) {
        if (barriers.wallHeight() <= jumpLimit)
            System.out.printf("Human %s was able to jump %d meters. %n", getName(), barriers.wallHeight());
        else {
            System.out.printf("Human %s couldn`t jump %d meters. %n", getName(), barriers.wallHeight());
            takePart = false;
        }
    }

    @Override
    public boolean isTakePart() {
        return takePart;
    }

}

