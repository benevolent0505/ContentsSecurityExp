import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by benevolent0505 on 17/01/10.
 * Connector Class is sample implements.
 */
public class Connector extends Thread implements Runnable {
    String name;
    String partnersIP;
    Socket socket = new Socket();
    ServerSocket echoServer = null;
    DataOutputStream writer = null;
    BufferedReader reader = null;

    public Connector(String var1, String var2) {
        this.name = var1;
        this.partnersIP = var2;
        if(var1.equals("TYUUGAKU")) {
            try {
                System.out.print("予備校に接続します……");
                this.socket.connect(new InetSocketAddress(var2, 10000), 10000);
                this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.writer = new DataOutputStream(this.socket.getOutputStream());
            } catch (SocketTimeoutException var6) {
                System.err.println("\n接続がタイムアウトしました．");
                var6.printStackTrace();
                System.exit(1);
            } catch (Exception var7) {
                var7.printStackTrace();
            }

            System.out.println("接続しました．");
        } else if(var1.equals("YOBIKOU")) {
            System.out.print("中学側に接続します……");

            try {
                this.echoServer = new ServerSocket(10000);
                this.echoServer.setSoTimeout(10000);
                this.socket = this.echoServer.accept();
                this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.writer = new DataOutputStream(this.socket.getOutputStream());
            } catch (SocketTimeoutException var4) {
                System.err.println("\n接続がタイムアウトしました．");
                var4.printStackTrace();
                System.exit(1);
            } catch (IOException var5) {
                var5.printStackTrace();
            }

            System.out.println("接続しました．");
        } else {
            System.err.println("your \"NAME\"　is　wrong!\n");
            System.exit(0);
        }

    }

    public void sendTable(double[][] var1) throws IOException {
        this.writer.writeBytes(Integer.toString(var1.length) + "\n");
        this.writer.writeBytes(Integer.toString(var1[0].length) + "\n");

        for(int var2 = 0; var2 < var1.length; ++var2) {
            for(int var3 = 0; var3 < var1[0].length; ++var3) {
                this.writer.writeBytes(Double.toString(var1[var2][var3]) + "\n");
            }
        }

    }

    public double[][] getTable() throws IOException {
        int var1 = Integer.parseInt(this.reader.readLine());
        int var2 = Integer.parseInt(this.reader.readLine());
        double[][] var3 = new double[var1][var2];

        for(int var4 = 0; var4 < var3.length; ++var4) {
            for(int var5 = 0; var5 < var3[0].length; ++var5) {
                var3[var4][var5] = Double.parseDouble(this.reader.readLine());
            }
        }

        return var3;
    }

    public synchronized void endConnection() {
        try {
            this.reader.close();
            this.writer.close();
            this.socket.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}
