package gitlet;

import java.util.Objects;

import static gitlet.MyUtils.exit;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Kingpin
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        //If a user doesn’t input any arguments
        if (args.length == 0) {
            exit("Please enter a command.");
        }

        String firstArg = args[0];
        switch(firstArg) {

            case "init":
                validateNumArgs(args, 1);
                Repository.initRepo();
                break;

            case "add":
                Repository.checkWorkingDir();
                validateNumArgs(args, 2);
                Repository.addFile(args[1]);
                break;

            case "commit":
                Repository.checkWorkingDir();
                validateNumArgs(args, 2);
                if (Objects.equals(args[1], "")) {
                    exit("Please enter a commit message.");
                }
                Repository.commitFile(args[1]);
                break;

            case "rm":
                Repository.checkWorkingDir();
                validateNumArgs(args, 2);
                Repository.removeFile(args[1]);
                break;

            case "log":
                Repository.checkWorkingDir();
                validateNumArgs(args, 1);
                Repository.printLogMessage();
                break;

            default:
                // If a user inputs a command that doesn’t exist
                exit("No command with that name exists.");
        }
    }

    /**
     * Checks the number of arguments versus the expected number,
     * print the error message and exit if it does not match.
     *
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    private static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            exit("Incorrect operands.");
        }
    }

}
