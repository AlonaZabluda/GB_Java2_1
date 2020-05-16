package lesson1;

public class RunningTrack implements Barrier{
    private int trackLength;

    public RunningTrack(int trackLength) {
        this.trackLength = trackLength;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }

    @Override
    public int trackLength() {
        return trackLength;
    }

    @Override
    public int wallHeight() {
        return 0;
    }

    @Override
    public void doIt(Participant participant) {
        participant.running(new RunningTrack(trackLength));
    }
}


