package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.view;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lizhihao on 2017/3/23.
 */

public class DownLoadThread extends Thread {
    private String downLoadUrl;
    private Context context;
    private FileOutputStream out = null;
    private File downLoadFile = null;
    private File sdCardFile = null;
    private InputStream in = null;
    // 要下载文件的大小
    private int fileSize ;
    public DownLoadThread(String downLoadUrl, Context context) {
        super();
        this.downLoadUrl = downLoadUrl;
        this.context = context;
    }
    @Override
    public void run() {
        InstallUtil.FLAG=1;
        try {
            URL httpUrl = new URL(downLoadUrl);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Referer", httpUrl.toString());
            conn.setRequestProperty("Charset", "UTF-8");
            //得到要下载文件的大小
            fileSize = conn.getContentLength() ;
       //     conn.setRequestProperty("Range", "bytes=" + fileSize + "-" );
            System.out.println("fileSize:"+fileSize);
            // URL 连接进行输出，则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。
            in = conn.getInputStream();
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(context, "SD卡不可用！", Toast.LENGTH_SHORT).show();
                return;
            }
            downLoadFile = Environment.getExternalStorageDirectory();
            sdCardFile = new File(downLoadFile, "download.apk");
            out = new FileOutputStream(sdCardFile);
            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
