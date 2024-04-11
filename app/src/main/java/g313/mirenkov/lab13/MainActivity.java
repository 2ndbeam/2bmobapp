package g313.mirenkov.lab13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txt_x, txt_y, txt_out;
    Switch swc_rad;
    boolean rad = false;
    final String url = "https://sbmobapi.shuttleapp.rs/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_x = findViewById(R.id.txt_x);
        txt_y = findViewById(R.id.txt_y);
        txt_out = findViewById(R.id.txt_out);
        swc_rad = findViewById(R.id.swc_rad);
    }

    public void button_send_request(View v) {
        String x = txt_x.getText().toString();
        String y = txt_y.getText().toString();
        String request_url = url;
        Toast tst1 = Toast.makeText(this, "Error: x is not set.", Toast.LENGTH_LONG);
        Toast tst2 = Toast.makeText(this, "Error: x or y are not set.", Toast.LENGTH_LONG);
        HttpRequest r = new HttpRequest(this) {
            @Override
            public void on_request_complete(String response) {
                Log.e("RESULT", response);
                txt_out.setText(response);
            }
        };
        String button_id = getResources().getResourceName(v.getId()).replace("g313.mirenkov.lab13:id/", "");
        switch(button_id) {
            case "btn_add":
                if (x.equals("") || y.equals("")) {
                    tst1.show(); break;
                }
                request_url += String.format("add?first=%s&second=%s", x, y);
                break;
        }
        r.make_request(request_url);
    }

    public void switch_radians(View v) {
        rad = swc_rad.isChecked();
    }
}