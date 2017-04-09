package com.byteshaft.ftp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.byteshaft.ftp4j.FTPAbortedException;
import com.byteshaft.ftp4j.FTPClient;
import com.byteshaft.ftp4j.FTPDataTransferException;
import com.byteshaft.ftp4j.FTPDataTransferListener;
import com.byteshaft.ftp4j.FTPException;
import com.byteshaft.ftp4j.FTPIllegalReplyException;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void uploadFile(String host, String username, int port, String password) {
        File folderPath = new File(Environment.getExternalStorageDirectory(),
                File.separator + "Android/data" + File.separator + getPackageName() + File.separator);
        File[] folderCount = folderPath.listFiles();
        for (File folder : folderCount) {
            for (File files : folder.listFiles()) {

            }
        }

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.setType(FTPClient.TYPE_BINARY);
            File[] allFolders = folderPath.listFiles();
            for (File folder : allFolders) {
                ftpClient.changeDirectory("/");
                try {
                    ftpClient.changeDirectory(folder.getName());
                } catch (FTPException ignore) {
                    ftpClient.createDirectory(folder.getName());
                    ftpClient.changeDirectory(folder.getName());
                }
                for (File files: folder.listFiles()) {
                    ftpClient.upload(files, new MyTransferListener());

                }


            }
        } catch (UnknownHostException ignore) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPAbortedException e) {
            e.printStackTrace();
        }
    }

    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {

        }

        public void transferred(int length) {
           // update progress dialog
        }

        public void completed() {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            getApplicationContext(),
                            "Upload Completed ...",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void aborted() {
        }

        public void failed() {
        }
    }
}
