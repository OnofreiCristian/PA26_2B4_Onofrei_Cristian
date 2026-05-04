package org.example;

import actors.Bunny;
import actors.Robot;
import maze_logic.Cell;
import maze_logic.Maze;
import memory.SharedMemory;
import movement.CommandListener;
import manager.TimeManager;
import window.GameWindow;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Maze maze = new Maze(10, 10);
        SharedMemory memory = new SharedMemory();

        Bunny bunny = new Bunny("Bunny", maze);
        List<Robot> robots = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            robots.add(new Robot("Robot-" + i, maze, memory));
        }

        Cell bunnyStart = maze.getRandomEmptyCell();
        maze.placeEntity(bunny, bunnyStart);

        for (Robot robot : robots) {
            Cell robotStart = maze.getRandomEmptyCell();
            maze.placeEntity(robot, robotStart);
        }

        GameWindow window = new GameWindow(maze);

        CommandListener listener = new CommandListener(maze, bunny, robots);
        Thread listenerThread = new Thread(listener);
        listenerThread.setDaemon(true);
        listenerThread.start();

        TimeManager manager = new TimeManager(maze, 60, window);
        Thread managerThread = new Thread(manager);
        managerThread.setDaemon(true);
        managerThread.start();

        Thread bunnyThread = new Thread(bunny);
        bunnyThread.start();

        for (Robot robot : robots) {
            new Thread(robot).start();
        }

        while (!maze.isGameOver()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        window.refreshVisuals();

        System.out.println("Simulation Ended.");
        String status = memory.getInformation("BUNNY_STATUS");
        if (status != null) {
            System.out.println("Result: " + status);
        } else {
            System.out.println("Result: The Bunny escaped!");
        }
    }
}