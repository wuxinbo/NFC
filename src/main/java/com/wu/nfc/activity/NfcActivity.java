package com.wu.nfc.activity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import com.wu.nfc.R;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by wuxinbo on 2015/10/17.
 */
public class NfcActivity extends Activity{
    private TextView textView ;
    private NfcAdapter nfcAdapter;
    private NdefMessage[] msgs ;
    private Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isNFc();
//        getMessageFromNFC();
        textView = (TextView) findViewById(R.id.NFCView);
        textView.setText("NFC test!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessageFromNFC();
//        for(NdefMessage message :msgs){
//            Log.i("NFc", message.toString());
//        }
    }
    public void isNFc(){
         nfcAdapter =NfcAdapter.getDefaultAdapter(this);
    }

    public void getMessageFromNFC(){
        if (nfcAdapter!=null)
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals((intent = getIntent()).getAction())) {
                Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NfcA isoDep = NfcA.get(tagFromIntent);
                try {
                    isoDep.connect(); //连接标签
                    byte[] payload = isoDep.getAtqa();
                    textView.setText(new String(payload, Charset.forName("utf-8")));
                } catch (IOException e) {
                    Log.e("NFC","读取失败！");
                    e.printStackTrace();
                }
            }
    }
}
