import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

public class TcpEchoClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        while (true) {
            try {
                out.println("Подключаемся к серверу");
                final InetAddress LOCALHOST = InetAddress.getLocalHost();
                final int PORT = parseInt(args[0]);
                Socket socket = new Socket(LOCALHOST, PORT);
                out.println("Подключились к серверу, отправляем данные");

                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                String stringToSend = "Hello world!";
                dataOutputStream.writeUTF(stringToSend);
                out.println("Отправлено: " + stringToSend);

                out.println("Получаем данные от сервера");
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                String acceptedInfo = dataInputStream.readUTF();
                out.println("Принято: " + acceptedInfo);

                inputStream.close();
                outputStream.close();
                socket.close();
                out.println("Клиент закончил свою работу");
                return;
            } catch (ConnectException ignored) {
                sleep(5000);
            }
        }
    }
}