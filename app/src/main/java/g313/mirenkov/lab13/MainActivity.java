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
    database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new database(this, "log.db", null, 1);
        txt_x = findViewById(R.id.txt_x);
        txt_y = findViewById(R.id.txt_y);
        txt_out = findViewById(R.id.txt_out);
        swc_rad = findViewById(R.id.swc_rad);
    }

    public void button_send_request(View v) {
        String x = txt_x.getText().toString();
        String y = txt_y.getText().toString();
        String request_url = url;
        String operation = "";
        String response = "";
        Toast tst_one_arg = Toast.makeText(this, "Error: x is not set.", Toast.LENGTH_LONG);
        Toast tst_two_args = Toast.makeText(this, "Error: x or y are not set.", Toast.LENGTH_LONG);
        Toast tst_integer = Toast.makeText(this, "Error: x or y are not integer", Toast.LENGTH_LONG);
        Toast tst_sign_arg = Toast.makeText(this, "Error: x or y contains only \"-\"", Toast.LENGTH_LONG);
        Toast tst_sign_arg_one = Toast.makeText(this, "Error: x contains only \"-\"", Toast.LENGTH_LONG);
        HttpRequest r = new HttpRequest(this) {
            @Override
            public void on_request_complete(String response) {
                Log.e("RESULT", response);
                try {
                    double res = Double.parseDouble(response);
                    txt_out.setText(String.format("%.4f", res));
                } catch(NumberFormatException e) {
                    txt_out.setText(response);
                }
            }
        };
        String button_id = getResources().getResourceName(v.getId()).replace("g313.mirenkov.lab13:id/", "");
        switch(button_id) {
            case "btn_add":
                if (x.equals("") || y.equals("")) {
                    tst_two_args.show();
                    return;
                }
                if (x.equals("-") || y.equals("-")) {
                    tst_sign_arg.show();
                    return;
                }
                request_url += String.format("add?first=%s&second=%s", x, y);
                break;
            case "btn_sub":
                if (x.equals("") || y.equals("")) {
                    tst_two_args.show();
                    return;
                }
                if (x.equals("-") || y.equals("-")) {
                    tst_sign_arg.show();
                    return;
                }
                request_url += String.format("sub?first=%s&second=%s", x, y);
                break;
            case "btn_mul":
                if (x.equals("") || y.equals("")) {
                    tst_two_args.show();
                    return;
                }
                if (x.equals("-") || y.equals("-")) {
                    tst_sign_arg.show();
                    return;
                }
                request_url += String.format("mul?first=%s&second=%s", x, y);
                break;
            case "btn_div":
                if (x.equals("") || y.equals("")) {
                    tst_two_args.show();
                    return;
                }
                if (x.equals("-") || y.equals("-")) {
                    tst_sign_arg.show();
                    return;
                }
                if (x.contains(".") || y.contains(".")) {
                    tst_integer.show();
                    return;
                }
                request_url += String.format("div?first=%s&second=%s", x, y);
                break;
            case "btn_div2":
                if (x.equals("") || y.equals("")) {
                    tst_two_args.show();
                    return;
                }
                if (x.equals("-") || y.equals("-")) {
                    tst_sign_arg.show();
                    return;
                }
                request_url += String.format("div2?first=%s&second=%s", x, y);
                break;
            case "btn_rem":
                if (x.equals("") || y.equals("")) {
                    tst_two_args.show();
                    return;
                }
                if (x.equals("-") || y.equals("-")) {
                    tst_sign_arg.show();
                    return;
                }
                request_url += String.format("rem?first=%s&second=%s", x, y);
                break;
            case "btn_sqr":
                if (x.equals("")) {
                    tst_one_arg.show();
                    return;
                }
                if (x.equals("-")) {
                    tst_sign_arg_one.show();
                    return;
                }
                request_url += String.format("sqr?arg=%s", x);
                break;
            case "btn_sqrt":
                if (x.equals("")) {
                    tst_one_arg.show();
                    return;
                }
                if (x.equals("-")) {
                    tst_sign_arg_one.show();
                    return;
                }
                request_url += String.format("sqrt?arg=%s", x);
                break;
            case "btn_sin":
                if (x.equals("")) {
                    tst_one_arg.show();
                    return;
                }
                if (x.equals("-")) {
                    tst_sign_arg_one.show();
                    return;
                }
                if (rad) {
                    request_url += String.format("sin/rad?arg=%s", x);
                } else {
                    request_url += String.format("sin/deg?arg=%s", x);
                }
                break;
            case "btn_cos":
                if (x.equals("")) {
                    tst_one_arg.show();
                    return;
                }
                if (x.equals("-")) {
                    tst_sign_arg_one.show();
                    return;
                }
                if (rad) {
                    request_url += String.format("cos/rad?arg=%s", x);
                } else {
                    request_url += String.format("cos/deg?arg=%s", x);
                }
                break;
            case "btn_tan":
                if (x.equals("")) {
                    tst_one_arg.show();
                    return;
                }
                if (x.equals("-")) {
                    tst_sign_arg_one.show();
                    return;
                }
                if (rad) {
                    request_url += String.format("tan/rad?arg=%s", x);
                } else {
                    request_url += String.format("tan/deg?arg=%s", x);
                }
                break;
        }
        r.make_request(request_url);
        db_log(operation, response);
    }

    public void switch_radians(View v) {
        rad = swc_rad.isChecked();
    }

    public void db_log(String operation, String response) {
        String text = String.format("%s = %s", operation, response);
        db.insert(text);
    }
}