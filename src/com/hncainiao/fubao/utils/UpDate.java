package com.hncainiao.fubao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class UpDate {
	public File downLoadFile(String httpUrl,Context mc) {
        // TODO Auto-generated method stub
        final String fileName = "Fubaojiankang.apk";
        File tmpFile = new File("/sdcard/update");
        if (!tmpFile.exists()) {
                tmpFile.mkdir();
        }
        final File file = new File("/sdcard/update/" + fileName);
        try {
                URL url = new URL(httpUrl);
                try {
                        HttpURLConnection conn = (HttpURLConnection) url .openConnection();
                        InputStream is = conn.getInputStream();
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] buf = new byte[256];
                        conn.connect();
                        double count = 0;
                        if (conn.getResponseCode() >= 400) {
                             Toast.makeText(mc, "网络超时！", 1).show();
                        } else {
                                while (count <= 100) {
                                        if (is != null) {
                                                int numRead = is.read(buf);
                                                if (numRead <= 0) {
                                                        break;
                                                } else {
                                                        fos.write(buf, 0, numRead);
                                                }

                                        } else {
                                                break;
                                        }

                                }
                        }

                        conn.disconnect();
                        fos.close();
                        is.close();
                } catch (IOException e) {
                        // TODO Auto-generated catch block

                        e.printStackTrace();
                }
        } catch (MalformedURLException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
        }

        return file;
}

	/**
	 * 打开安装文件
	 * @param file
	 * @param activity
	 */
	public void openFile(File file,Context activity) {
	        // TODO Auto-generated method stub
	        Log.e("OpenFile", file.getName());
	        Intent intent = new Intent();
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.setAction(android.content.Intent.ACTION_VIEW);
	        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
	        activity.startActivity(intent);
	}
}
