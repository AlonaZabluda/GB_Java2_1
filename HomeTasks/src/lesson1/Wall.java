package lesson1;

public class Wall implements Barrier{
    private int wallHeight;

    public Wall(int wallHeight) {

        this.wallHeight = wallHeight;
    }

    public int getWallHeight() {

        return wallHeight;
    }

    public void setWallHeight(int wallHeight) {

        this.wallHeight = wallHeight;
    }

    @Override
    public int trackLength() {
        return 0;
    }

    @Override
    public int wallHeight() {
        return wallHeight;
    }

    @Override
    public void doIt(Participant participant) {
        participant.jumping(new Wall(wallHeight));
    }
}

