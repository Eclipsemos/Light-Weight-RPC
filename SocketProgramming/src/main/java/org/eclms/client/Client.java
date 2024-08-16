package org.eclms.client;

import org.eclms.MyObject;

import java.io.*;
import java.net.Socket;

public class Client {
    static String hostName = "localhost";
    static int info_port = 12345;

    public static void main(String[] args) throws IOException {
        //run();
        //runWithStickyPackage();
        //runWithStickyPackageSolved();
        //runWithHalfPackage();
        runWithObject();
    }

    private static void run() throws IOException {
        Socket socket = new Socket(hostName, info_port);
        System.out.println("Connection created at server " + hostName + ":" + info_port);

        OutputStream outToServer = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outToServer, true);
        String messageToSend = "Hello, server yxy";
        out.println(messageToSend);
        socket.close();
        System.out.println("Sending finished");
    }

    //TCP Sticky Package problem
    public static void runWithStickyPackage() throws IOException {
        Socket socket = new Socket(hostName, info_port);
        System.out.println("Connection created at server " + hostName + ":" + info_port);

        OutputStream outToServer = socket.getOutputStream();

        //String messageToSend = "Hello, server yxy";
        String[] messages = {"Hello,", "my", "name", "is", "yxy"};
        for (String message : messages) {
            outToServer.write(message.getBytes());
            outToServer.flush();// Sending right now
        }
        socket.close();
        System.out.println("Sending finished");
    }
    //Solution
    public static void runWithStickyPackageSolved() throws IOException {
        Socket socket = new Socket(hostName, info_port);
        System.out.println("Connection created at server " + hostName + ":" + info_port);

        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

        String[] messages = {"Hello,", "my", "name", "is", "yxy"};
        for(String message: messages) {
            outToServer.writeInt(message.length());
            outToServer.writeBytes(message);
            outToServer.flush();
        }
        socket.close();
        System.out.println("Sending finished");
    }

    //Half Package problem
    public static void runWithHalfPackage() throws IOException {
        Socket socket = new Socket(hostName, info_port);
        System.out.println("Connected to server at " + hostName + ":" + info_port);

        OutputStream outToServer = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outToServer, true);

        // 发送一个长消息，模拟半包问题
        String longMessage = "This is a very long message that will be split into two parts to simulate a half package issue.";
        out.println(longMessage);

        socket.close();
    }

    //Sending Objects using ObjectOutputStream
    public static void runWithObject() throws IOException {
        Socket socket = new Socket(hostName, info_port);
        System.out.println("Connected to server at " + hostName + ":" + info_port);

        // 创建要发送的对象
        MyObject myObject = new MyObject("Hello", "World");

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(myObject);

        out.close();
        socket.close();
    }
}
