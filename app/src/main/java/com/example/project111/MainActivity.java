package com.example.project111;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    final ProtocolSender test = new ProtocolSender("mail.smarttechnocenter.com" ,26);
    TextView textView;
     Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("mainActivity","start");
    }

    public void dooo(View view) {
        ThreadMethod threadMethod = new ThreadMethod();
        threadMethod.start();
        /*handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                textView = findViewById(R.id.textView);
                textView.setText(bundle.getString("email"));

                super.handleMessage(msg);
            }
        };*/

    }


    private class ThreadMethod extends Thread{
        @Override
        public void run() {
            SimpleDateFormat format11 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Log.d("mainactivity","start");

            Log.d("mainactivity", String.valueOf(test.ConnectToServer()) );
            Log.d("response", test.ReadResponse());
           // test.Login("test@smarttechnocenter.com","BbAa@@!!");
            test.Send(new Email("test@smarttechnocenter.com",
                    "nehlawi-raghad@outlook.com",
                    "",
                    "test",
                    "hello this is test",
                    new Date()));

            //test.ReadResponse();
            // test.FilledFolderName();
             //test.SelectFolderAndReadEmails("INBOX");
           // Bundle bundle = new Bundle();
            //Message message = Message.obtain();
            //bundle.putString("email" , email);
            //message.setData(bundle);
            //handler.sendMessage(message);


           // test.Logout();

        }
    }
}
