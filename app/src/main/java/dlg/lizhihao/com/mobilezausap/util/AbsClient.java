package dlg.lizhihao.com.mobilezausap.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class AbsClient {

    public static void main(String[] args) {
        request("registered");// login
    }

    public static String request(String params) {
        Socket socket = null;
        String object = null;
        try {
            // 1.连接服务器
            String ip = InetAddress.getLocalHost().getHostAddress();// 服务器Ip地址
            System.out.println("AbsClient*****连接服务器");
            socket = new Socket("127.0.0.1", 8485);
            System.out.println("AbsClient*****已经建立连接");
            // 2.发送请求数据
            System.out.println("AbsClient*****发送请求");
            sendRequest(socket.getOutputStream(), params);
            socket.isInputShutdown();
            System.out.println("AbsClient*****请求发送成功*****接收响应");
            // 3.接收并解析响应数据
            object = receiveResponse(socket.getInputStream());
            System.out.println("AbsClient*****响应接收完成*****服务器返回结果");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    // 4.断开连接
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 5.返回接收的数据
        return object;
    }

    /******** 生成并发送请求数据 ***************************************************/
    public static void sendRequest(OutputStream out, String params) throws Exception {
        out.write(params.getBytes());
        out.flush();
        System.out.println("AbsClient我发送到服务端去》》》" + params);
    }
    /*********** 接收并解析响应数据 ***********************************/
    public static String receiveResponse(InputStream in) throws Exception {
        String data = IOUtils.toString(in);// 保存从服务器响应的结果
        System.out.println("AbsClient我是收到服务端的》》》" + data);
        return data;
    }

}
