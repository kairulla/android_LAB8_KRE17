package kz.kairulla.lab8_kre17;

import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView; // Иконка погоды
    private TextView textView; // Компонент для данных погоды
    private Button button;

    private static final String BASE_URL = "https://www.wildberries.ru"; // Адрес с котировками
//    private static final String BASE_URL = "https://kazfin.info/kazakhstan"; // Адрес с котировками

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        17) Найти названия разделов на сайте интернет-магазина "Wildberries": https://www.wildberries.ru
 */

        // Разрешаем запуск в общем потоке выполнеия длительных задач (например, чтение с сети)
        // ЭТО ТОЛЬКО ДЛЯ ПРИМЕРА, ПО-НОРМАЛЬНОМУ НАДО ВСЕ В ОТДЕЛЬНЫХ ПОТОКАХ
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
/*--------------------------------------------------------------------------------------------------*/
        textView = findViewById(R.id.textView);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(R.string.net_dannikh);
                try {
                    StringBuilder data = new StringBuilder();
                    Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
//                    data.append("Курсы валюты:\n"); // Считываем заголовок страницы
//                    data.append(String.format("%12s %12s %12s %12s\n", "валюта", "наименование", "курс", "изменение").trim());
//                    data.append("\n");
                    Elements e = doc.select("ul.menu-burger__main-list"); // Ищем в документе "<div class="exchange"> с данными о валютах
//                    Element li = e.select("li").get(0);
                    for(Element li : e.select("li")) {
                        data.append(li.text());
                        data.append("\n");
//                        System.out.println(li.text());
                    }
//                    System.out.println(li);
//                    Elements tables = e.select("table"); // Ищем таблицы с котировками
//                    Element table = tables.get(0); // Берем 1 таблицу
                    int i = 0;
                    // Цикл по строкам таблицы
//                    for (Element row : table.select("tr")) { // все строки)
//          for (Element row : table.select("tr:lt(3)")) { // чтение 3 строк (eq - конкретная строка ,
//          lt - ниже, gt - выше)
                        // Цикл по столбцам таблицы
//                        for (Element col : row.select("td")) { //
//                            data.append(String.format("%12s ", col.text())); // Считываем данные с ячейки таблицы
//                        }
//                        data.append("\n"); // Добавляем переход на следующую строку;
//                    }
                    textView.setText(data.toString().trim());
                } catch (Exception e) {
                    textView.setText(R.string.oshibka);
                }
            }
        };
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
        onClickListener.onClick(null); // Нажмем на кнопку "Обновить"
    }
}