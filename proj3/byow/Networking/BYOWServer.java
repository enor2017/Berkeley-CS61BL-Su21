package byow.Networking;

import byow.Core.WorldGenerator;
import byow.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

/**
 * Created by Arjun Sahai and Boren Tsai.
 */

public class BYOWServer {

    static private final File CWD = new File(System.getProperty("user.dir"));
    static private final String CANVAS_FILE = ".server_canvas.png";

    private ServerSocket serverSocket;
    private Socket clientStringSocket;
    private Socket clientWriteSocket;

    private BufferedReader in;
    private BufferedWriter out;
    private DataOutputStream dos;

    public BYOWServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started. Waiting for client to connect... ");
        clientWriteSocket = serverSocket.accept(); // block until client connects
        clientStringSocket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientStringSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientStringSocket.getOutputStream()));
        dos = new DataOutputStream(clientWriteSocket.getOutputStream());
        System.out.println("Client connected!");
    }

    public void initialize(int width, int height) {
        sendCanvas(width, height, true);
    }

    public void sendCanvas() {
        sendCanvas(0, 0, false);
    }

    /*
    This method will check to see if the client is sending a key press to the server
    This will not block your code
     */
    public boolean clientHasKeyTyped() {
        try {
            return in.ready();
        } catch (IOException e) {
            stopConnection();
            return false;
        }
    }

    /*
    Gives the next character sent from the client.
    If the client has not sent anything yet, then this method will block
     */
    public char clientNextKeyTyped() {
        try {
            return (char) in.read();
        } catch (IOException e) {
            System.out.println("IO EXCEPTION CAUGHT");
            stopConnection();
            return 'q';
        }
    }

    /*
    Closes all input/output streams and sockets.
     */
    public void stopConnection() {
        try {
            out.write("QUIT");
            out.flush();
            in.close();
            dos.close();
            clientStringSocket.close();
            clientWriteSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            return;
        }
    }

    /*
    Sends world bitmap and, optionally, StdDraw canvas configuration metadata.
     */
    private void sendCanvas(int width, int height, boolean sendConfig) {
        try {
            dos.writeBoolean(sendConfig);

            if (sendConfig) {
                dos.writeInt(width);
                dos.writeInt(height);
                dos.flush();
            } else {
                dos.flush();
            }


            // Save the canvas to a PNG file
            StdDraw.save(CANVAS_FILE);
            File canvas = join(CWD, CANVAS_FILE);

            // Send file size and unblock client from waiting
            dos.writeLong(canvas.length());
            dos.flush();

            FileInputStream fis = new FileInputStream(canvas);

            // break file into chunks and send to client
            int bytes;
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytes);
                dos.flush();
            }
            fis.close();
        } catch (IOException e) {
            stopConnection();
        }
    }

    private static File join(File first, String... others) {
        return Paths.get(first.getPath(), others).toFile();
    }

    /* For test */
    public static void main(String[] args) {
        // start server on port 61
        BYOWServer server;
        try {
            server = new BYOWServer(61);
        } catch (Exception e) {
            System.out.println("Error while creating server on port 61.");
            return;
        }

        // create a map
        WorldGenerator gen = new WorldGenerator(49, 25, "just4fun");
        TERenderer ter = new TERenderer();
        ter.initialize(49, 25);
        ter.renderFrame(gen.convertToTile());

        server.initialize(50, 30);
    }
}
