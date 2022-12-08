package kz.kairulla.lab8_kre17;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Activity2 extends AppCompatActivity {

    private Button button2;
    private ViewGroup linearLayout2;

//    private static final String BASE_URL = "https://www.wildberries.ru"; // Адрес с котировками
//    private static final String BASE_URL = "https://kazfin.info/kazakhstan"; // Адрес с котировками

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        /*
        17) Найти названия разделов на сайте интернет-магазина "Wildberries": https://www.wildberries.ru
 */

        // Разрешаем запуск в общем потоке выполнеия длительных задач (например, чтение с сети)
        // ЭТО ТОЛЬКО ДЛЯ ПРИМЕРА, ПО-НОРМАЛЬНОМУ НАДО ВСЕ В ОТДЕЛЬНЫХ ПОТОКАХ
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        linearLayout2 = (ViewGroup) findViewById(R.id.linearLayout2);
        /*--------------------------------------------------------------------------------------------------*/
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String BASE_URL = getIntent().getStringExtra("myLink");
                    Document doc = Jsoup.connect(BASE_URL).timeout(5000).post(); // Создание документа JSOUP из html
                    Elements e = doc.select("ul.menu-catalog__list-2"); // Ищем в документе "<div class="exchange"> с данными о валютах
//                    Elements li = e.select("li");
//                    System.out.println(li.text());
                    int count = 1;
                    for(Element li : e.select("li")) {
                        Button bt = new Button(Activity2.this);
                        bt.setText(count + ") " + li.text());
                        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Intent intent = new Intent(Activity2.this, Activity3.class);
//                                startActivity(intent);
                            }
                        });
                        linearLayout2.addView(bt);
                        count++;
                    }
                } catch (Exception e) {
                }
            }
        };
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(onClickListener);
        onClickListener.onClick(null); // Нажмем на кнопку "Обновить"
    }
}
