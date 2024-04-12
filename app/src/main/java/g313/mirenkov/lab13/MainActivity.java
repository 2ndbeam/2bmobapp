package g313.mirenkov.lab13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txt_x, txt_y, txt_out, txt_hist;
    Switch swc_rad;
    boolean rad = false;
    final String url = "https://sbmobapi.shuttleapp.rs/";
    public String operation = "";
    database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        g.db = new database(this, "log.db", null, 2);
        txt_x = findViewById(R.id.txt_x);
        txt_y = findViewById(R.id.txt_y);
        txt_out = findViewById(R.id.txt_out);
        txt_hist = findViewById(R.id.txt_hist);
        swc_rad = findViewById(R.id.swc_rad);
        upd_hist();
    }

    public void button_send_request(View v) {
        String x = txt_x.getText().toString();
        String y = txt_y.getText().toString();
        String request_url = url;
        Toast tst_one_arg = Toast.makeText(this, "Error: x is not set.", Toast.LENGTH_LONG);
        Toast tst_two_args = Toast.makeText(this, "Error: x or y are not set.", Toast.LENGTH_LONG);
        Toast tst_integer = Toast.makeText(this, "Error: x or y are not integer", Toast.LENGTH_LONG);
        Toast tst_sign_arg = Toast.makeText(this, "Error: x or y contains only \"-\"", Toast.LENGTH_LONG);
        Toast tst_sign_arg_one = Toast.makeText(this, "Error: x contains only \"-\"", Toast.LENGTH_LONG);
        boolean is_two_arg = true;
        String trigo = "deg";
        if (rad) trigo = "rad";
        HttpRequest r = new HttpRequest(this) {
            @Override
            public void on_request_complete(String response) {
                Log.e("RESULT", response);
                try {
                    double res = Double.parseDouble(response);
                    String sres = String.format("%.4f", res);
                    txt_out.setText(sres);
                } catch(NumberFormatException e) {
                    txt_out.setText(response);
                }
                db_log(txt_out.getText().toString());
            }
        };
        String button_id = getResources().getResourceName(v.getId()).replace("g313.mirenkov.lab13:id/", "");
        switch(button_id) {
            case "btn_add":
                request_url += String.format("add?first=%s&second=%s", x, y);
                operation = String.format("%s + %s", x, y);
                break;
            case "btn_sub":
                request_url += String.format("sub?first=%s&second=%s", x, y);
                operation = String.format("%s - %s", x, y);
                break;
            case "btn_mul":
                request_url += String.format("mul?first=%s&second=%s", x, y);
                operation = String.format("%s * %s", x, y);
                break;
            case "btn_div":
                request_url += String.format("div?first=%s&second=%s", x, y);
                operation = String.format("%s / %s", x, y);
                break;
            case "btn_div2":
                if (x.contains(".") || y.contains(".")) { tst_integer.show(); return; }
                request_url += String.format("div2?first=%s&second=%s", x, y);
                operation = String.format("%s // %s", x, y);
                break;
            case "btn_rem":
                request_url += String.format("rem?first=%s&second=%s", x, y);
                operation = String.format("%s rem %s", x, y);
                break;
            case "btn_sqr":
                is_two_arg = false;
                request_url += String.format("sqr?arg=%s", x);
                operation = String.format("%s^2", x);
                break;
            case "btn_sqrt":
                is_two_arg = false;
                request_url += String.format("sqrt?arg=%s", x);
                operation = String.format("sqrt(%s)", x);
                break;
            case "btn_sin":
                is_two_arg = false;
                request_url += String.format("sin/%s?arg=%s", trigo, x);
                operation = String.format("sin(%s %s)", x, trigo);
                break;
            case "btn_cos":
                is_two_arg = false;
                request_url += String.format("cos/%s?arg=%s", trigo, x);
                operation = String.format("cos(%s %s)", x, trigo);
                break;
            case "btn_tan":
                is_two_arg = false;
                request_url += String.format("tan/%s?arg=%s", trigo, x);
                operation = String.format("tan(%s %s)", x, trigo);
                break;
        }
        if (is_two_arg) {
            if (x.equals("") || y.equals("")) { tst_two_args.show(); return; }
            if (x.equals("-") || y.equals("-")) { tst_sign_arg.show(); return; }
        } else {
            if (x.equals("")) { tst_one_arg.show(); return; }
            if (x.equals("-")) { tst_sign_arg_one.show(); return; }
        }
        r.make_request(request_url);
    }

    public void switch_radians(View v) {
        rad = swc_rad.isChecked();
    }

    public void db_log(String response) {
        String text = String.format("%s = %s", operation, response);
        g.db.insert(text);
        upd_hist();
    }

    public void upd_hist() {
        String history = getString(R.string.oper_hist);
        int limit = 10;
        int rows_count = g.db.get_rows_count();
        if (rows_count < 10) limit = rows_count;
        for (int i = rows_count; i > rows_count - limit; i--) {
            history += String.format("%d.\t%s\n", i, g.db.select(String.valueOf(i)));
        }
        txt_hist.setText(history);
    }

    public void show_hist(View v) {
        Intent i = new Intent(this, DatabaseActivity.class);
        startActivity(i);
    }
}