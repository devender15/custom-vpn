import java.net.*;
import  java.io.*;

class RequestProcessor extends Thread {
    private Socket socket;
    RequestProcessor(Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            OutputStream outputStream;
            OutputStreamWriter outputStreamWriter;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            StringBuffer stringBuffer;
            String response;
            String request;

            int x;
            int temp1, temp2;
            String part1, part2, part3;
            int rollNumber;
            String name;
            String gender;

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);

            stringBuffer = new StringBuffer();

            while (true) {
                x = inputStreamReader.read();
                if(x=='#' || x==-1) break;
                stringBuffer.append((char)(x));
            }

            request = stringBuffer.toString();
            System.out.println("Request: " + request);

            // parsing and extracting request data
            temp1 = request.indexOf(",");
            temp2 = request.indexOf(",", temp1+1);
            part1 = request.substring(0, temp1);
            part2 = request.substring(temp1+1, temp2);
            part3 = request.substring(temp2+1);

            rollNumber = Integer.parseInt(part1);
            name = part2;
            gender = part3;

            System.out.println("Roll number: " + rollNumber);
            System.out.println("Name: " + name);
            System.out.println("Gender: " + gender);

            response = "Data saved#";

            outputStream = socket.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(response);
            outputStreamWriter.flush();
            System.out.println("Response sent!");

            socket.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}

public class Server {
    private ServerSocket serverSocket;
    private int portNumber;

    Server(int portNumber) {
        this.portNumber = portNumber;

        try {
            serverSocket = new ServerSocket(this.portNumber);
            startListening();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void startListening() {
        try {
            Socket socket;
            while (true) {
                System.out.println("Server is listening on port: " + this.portNumber);
                socket = serverSocket.accept();
                System.out.println("Request arrived...");

                new RequestProcessor(socket);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);
        Server server = new Server(portNumber);
    }
}
