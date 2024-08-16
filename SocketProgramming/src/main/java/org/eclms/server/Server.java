package org.eclms.server;


import org.eclms.MyObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Server {
    static int info_port = 12345;

    public static void main(String[] args) throws IOException {
        //run();
        //runWithStickyPackageSolved();
        //runWithHalfPackage();
        runWithObject();
    }

    /**
     * @throws IOException
     */
    public static void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(info_port);
        System.out.println("Now Listening on port: " + info_port);

        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection created from " + clientSocket.getInetAddress().getHostAddress());

            InputStream in = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (true) {
                String message = reader.readLine();
                if (message == null) break;
                System.out.println("Received message from client: " + message);
            }
            clientSocket.close();
        } finally {
            serverSocket.close();
        }
    }

    public static void runWithStickyPackageSolved() throws IOException {
        ServerSocket serverSocket = new ServerSocket(info_port);
        System.out.println("Now Listening on port: " + info_port);

        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection created from " + clientSocket.getInetAddress().getHostAddress());

            InputStream in = clientSocket.getInputStream();
            DataInputStream dis = new DataInputStream(in);

            while (true) {
                int length = dis.readInt();
                byte[] buffer = new byte[length];
                dis.readFully(buffer);
                String message = new String(buffer);
                System.out.println("Received message from client: " + message);
            }
        } catch (EOFException e) {
            System.out.println("End of stream reached.");
        } finally {
            serverSocket.close();
        }
    }
    

    // Mocking half-package problem
    public static void runWithHalfPackage() throws IOException {
        ServerSocket serverSocket = new ServerSocket(info_port);
        System.out.println("Server is listening on port " + info_port);

        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

            InputStream in = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // 模拟半包问题：只读取消息的一部分
            char[] buffer = new char[20]; // 定义一个小的缓冲区来模拟半包
            int charsRead = reader.read(buffer, 0, buffer.length);
            while (charsRead != -1) {
                // 将读取的部分打印出来
                String partialMessage = new String(buffer, 0, charsRead);
                System.out.println("Received partial message: " + partialMessage);

                // 再次读取剩余的消息部分
                charsRead = reader.read(buffer, 0, buffer.length);
            }

            clientSocket.close();
        } finally {
            serverSocket.close();
        }
    }

    public static void runWithObject() throws IOException {
        ServerSocket serverSocket = new ServerSocket(info_port);
        System.out.println("Server is listening on port " + info_port);

        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostAddress());

            // 使用对象输入流接收对象
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            // 接收客户端发送的对象
            MyObject myObject = (MyObject) in.readObject();
            System.out.println("Received object from client: " + myObject);

            in.close();
            clientSocket.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            serverSocket.close();
        }
    }
}
