package servers;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;



public class WorkerRunnable implements Runnable{
    protected Socket clientSocket = null;
    protected String serverText   = null;


    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        
    }

    public void run() {
        try {


            InputStream input  = clientSocket.getInputStream();
            InputStreamReader isReader = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isReader);
            String header = reader.readLine();
            header = header.replaceAll("\\/","");
            String fileName = header.split(" ")[1];
            String filePath = "Desktop/"+fileName+".txt";

            OutputStream output = clientSocket.getOutputStream();
            sendFile(filePath, output);           
            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void sendFile(String fileName , OutputStream output) {
        try {
            File myFile = new File(fileName);
            byte[] byteArray = new byte[(int) myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(byteArray, 0, byteArray.length);
            DataOutputStream dos = new DataOutputStream(output);
            dos.write(byteArray, 0, byteArray.length);
            dos.flush();
            dis.close();
        } catch (Exception e) {
            System.err.println("File does not exist!");
        } 
    }
}
