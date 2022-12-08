package kz.kairulla.lab8_kre17;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Activity1 extends AppCompatActivity {

    private Button button;
    private ViewGroup linearLayout;

    private static final String BASE_URL = "https://www.wildberries.ru"; // Адрес с котировками
//    private static final String BASE_URL = "https://kazfin.info/kazakhstan"; // Адрес с котировками

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
/*
        17) Найти названия разделов на сайте интернет-магазина "Wildberries": https://www.wildberries.ru
 */

        // Разрешаем запуск в общем потоке выполнеия длительных задач (например, чтение с сети)
        // ЭТО ТОЛЬКО ДЛЯ ПРИМЕРА, ПО-НОРМАЛЬНОМУ НАДО ВСЕ В ОТДЕЛЬНЫХ ПОТОКАХ
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        linearLayout = (ViewGroup) findViewById(R.id.linearLayout);
/*--------------------------------------------------------------------------------------------------*/
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Document doc = Jsoup.connect(BASE_URL).userAgent("Mozilla").timeout(5000).get(); // Создание документа JSOUP из html
                    Elements e = doc.select("ul.menu-burger__main-list"); // Ищем в документе "<div class="exchange"> с данными о валютах
                    int count = 0;
                    for(Element li : e.select("li")) {
                        Elements a = li.select("a.menu-burger__main-list-link");
                        String ssilka = a.attr("href");
                        Button bt = new Button(Activity1.this);
                        bt.setText(count + 1 + ") " + li.text());
                        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Activity1.this, Activity2.class);
                                intent.putExtra("myLink", ssilka);
                                startActivity(intent);
                            }
                        });
                        linearLayout.addView(bt);
                        count++;
                    }
                } catch (Exception e) {
                }
            }
        };
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
        onClickListener.onClick(null); // Нажмем на кнопку "Обновить"
    }
}