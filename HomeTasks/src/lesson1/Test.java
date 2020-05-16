package lesson1;

public class Test {
    public static void main(String[] args) {

        Participant[] participants = {
                new Cat("Tom", 250, 3),
                new Human("Andrey", 300, 1),
                new Robot("R2D-4", 52, 18)
        };

        participants[0].runInfo();
        participants[2].jumpInfo();

        Barrier[] barriers = {
                new RunningTrack(200),
                new Wall(1),
                new RunningTrack(300),
                new Wall(5)
        };


        for (Participant participant : participants) {
            for (Barrier barrier : barriers) {
                barrier.doIt(participant);
                if (!participant.isTakePart())
                    break;

            }
        }

    }
}
