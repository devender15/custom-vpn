import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] data) {
        String ipAddress = data[0];
        int portNumber = Integer.parseInt(data[1]);

        int rollNumber = Integer.parseInt(data[2]);
        String name = data[3];
        String gender = data[4];

        String request = rollNumber + ", " + name + ", " + gender + "#";

        try {
            Socket socket = new Socket(ipAddress, portNumber);

            OutputStream outputStream;
            OutputStreamWriter outputStreamWriter;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            StringBuffer stringBuffer;
            String response;
            int x;

            outputStream = socket.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(request);
            outputStreamWriter.flush();

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            stringBuffer = new StringBuffer();

            while(true) {
                x = inputStreamReader.read();
                if(x=='#' || x==-1) break;
                stringBuffer.append((char)(x));
            }

            response = stringBuffer.toString();
            System.out.println(response);

            socket.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}