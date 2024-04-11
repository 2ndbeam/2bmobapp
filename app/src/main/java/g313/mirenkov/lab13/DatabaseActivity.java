package g313.mirenkov.lab13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DatabaseActivity extends AppCompatActivity {

    ArrayAdapter<String> adp;
    ListView lst_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        adp = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1);
        lst_history = findViewById(R.id.lst_history);
        lst_history.setAdapter(adp);
        String history;
        int rows_count = g.db.get_rows_count();
        for (int i = rows_count; i > 0; i--) {
            history = String.format("%d.\t%s\n", i, g.db.select(String.valueOf(i)));
            adp.add(history);
        }
        adp.notifyDataSetChanged();
    }

    public void on_exit(View v) {
        finish();
    }
}