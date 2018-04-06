package com.myrules.melez.resms3;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.myrules.melez.resms3.ServiceReceiver;


public class MainActivity extends AppCompatActivity {

    String lastCalledNumber;
    public void obslugaKlikniecia(View v){

        lastCalledNumber = CallLog.Calls.getLastOutgoingCall(this);
        Toast.makeText(getApplicationContext(),lastCalledNumber,Toast.LENGTH_SHORT).show();
        sendSMS(lastCalledNumber, "Hi You got a message!");
        // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
        //          + lastCalledNumber)));
    }

    public void zapisz_zmiany(View v)
    {
        EditText et = (EditText)findViewById(R.id.editText);
        String val = et.getText().toString();
        DbManagement zb = new DbManagement(this);
        zb.zmien_ustwienia("wizytowka",val);
        Toast.makeText(getApplicationContext(),"Zapisano",Toast.LENGTH_SHORT).show();
    }

    private void sendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button) findViewById(R.id.button12);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView tv = (TextView)findViewById(R.id.textView2);
        EditText et = (EditText)findViewById(R.id.editText);
        DbManagement zb = new DbManagement(this);
        // zb.wpisz("Jan","Kowalski");
        // zb.wpisz("Krzysiu","Z Klanu");
        //  zb.wpisz("Dziadek","Mróz");
        Cursor k = zb.odczytaj();
        while(k.moveToNext()){
            //int nr=k.getInt(0);
            String numer=k.getString(1);
            String data=k.getString(2);
            tv.setText(tv.getText()+"\n"+numer+" "+data);
        }
        k.close();

        Cursor u = zb.odczytaj_ustwienia();
        while (u.moveToNext()){
            String wizytowka = u.getString(1);
            et.setText(wizytowka);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.history) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




   /* public class PhoneState extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);

            switch (tm.getCallState()) {

                case TelephonyManager.CALL_STATE_RINGING:
                    String phoneNr= intent.getStringExtra("incoming_number");
                    Toast.makeText(context, phoneNr,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }*/
}
