package g313.mirenkov.lab13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt_x, txt_y, txt_out;
    final String url = "https://sbmobapi.shuttleapp.rs/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_x = findViewById(R.id.txt_x);
        txt_y = findViewById(R.id.txt_y);
        txt_out = findViewById(R.id.txt_out);
    }

    public void button_send_request(View v) {
        String x = txt_x.getText().toString();
        String y = txt_y.getText().toString();
        String request_url = url;
        HttpRequest r = new HttpRequest(this) {
            @Override
            public void on_request_complete(String response) {
                Log.e("RESULT", response);
                txt_out.setText(response);
            }
        };
        String button_id = getResources().getResourceName(v.getId());
        switch(button_id) {
            case "btn_add":
                request_url += String.format("add?first=%d&second=%d", x, y);
                break;
        }
        r.make_request("");
    }

}