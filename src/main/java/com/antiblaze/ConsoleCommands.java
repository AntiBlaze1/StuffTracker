package com.antiblaze;

import static com.antiblaze.Main.stopServer;

public class ConsoleCommands {
    public static boolean doCommand(String command) {
        command=command.toLowerCase().trim();
        switch (command) {
            case "help":
                System.out.println("todo");
                break;
            case "exit":
            case "stop":
                stopServer();
                return false;

            case "":
                return true;
            default:
                System.out.println("Unrecognized command "+command);
                return true;
        }
        System.out.println("Executing command "+command);
        return true;
    }
}
