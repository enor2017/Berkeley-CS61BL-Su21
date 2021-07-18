package gitlet;

import static gitlet.Utils.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author enor2017
 */
public class Main {

    // current folder's repo
    private static Repository repo = new Repository();

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // if args is empty
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                repo.init(args);
                break;
            case "add":
                getRepoFromFile();
                repo.add(args);
                break;
            case "commit":
                getRepoFromFile();
                repo.commit(args);
                break;
            case "log":
                getRepoFromFile();
                repo.log(args);
                break;
            default:
                System.out.println("No command with that name exists.");
                return;
        }
    }

    private static void getRepoFromFile () {
        // if repo doesn't exist, print error message, exit with code 0
        if(!Repository.GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        } else {
            // read repo from file
            repo =  readObject(Repository.REPO_INFO, Repository.class);
        }
    }
}
