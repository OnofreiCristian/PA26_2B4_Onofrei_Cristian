package movement;

import actors.Bunny;
import actors.Entity;
import actors.Robot;
import maze_logic.Maze;

import java.util.List;
import java.util.Scanner;

public class CommandListener implements Runnable {
    private final Maze maze;
    private final Bunny bunny;
    private final List<Robot> robots;

    public CommandListener(Maze maze, Bunny bunny, List<Robot> robots) {
        this.maze = maze;
        this.bunny = bunny;
        this.robots = robots;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!maze.isGameOver()) {
            if (scanner.hasNextLine()) {
                String command = scanner.nextLine().toLowerCase().trim();
                processCommand(command);
            }
        }
        scanner.close();
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 2) return; // Expecting commands like "action target"

        String action = parts[0];
        String target = parts[1];

        if (target.equals("all") || target.equals("bunny")) {
            applyAction(bunny, action);
        }

        for (Robot robot : robots) {
            if (target.equals("all") || target.equals(robot.getName().toLowerCase())) {
                applyAction(robot, action);
            }
        }
    }

    private void applyAction(Entity entity, String action) {
        switch (action) {
            case "stop": entity.setPaused(true); break;
            case "resume": entity.setPaused(false); break;
            case "fast": entity.setSpeedDelay(100); break; // 100ms
            case "slow": entity.setSpeedDelay(800); break; // 800ms
        }
    }
}