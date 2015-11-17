package com.example.user.bborders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private GridView gridView1;
    private Button button;
    private List<String> data=new ArrayList<String>();
    private Helper helper=new Helper();
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private int activeflag=0;
    private int waitflag=0;
    private int updateflag=0;
    private int waitposition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView1 = (GridView) findViewById(R.id.gridView);
        button = (Button) findViewById(R.id.button);
        Set_spinner();
        checkInternetConenction();

    }
    public void onMyButtonClick(View view)
    {
        updateflag=1;
        checkInternetConenction();
    }
    public void Grid_fill(int index){


        data.clear();
        switch (index)
        {
            case 1:

                int Lit1=0;
                int Lit2=6;
                int Lit3=7;
                int Lit4=8;
                for (String link : helper.Get_Litva()) {
                    data.add(helper.Get_Litva().get(Lit1));
                    data.add(helper.Get_Border().get(Lit2)+','+helper.Get_Border().get(Lit3)+','+helper.Get_Border().get(Lit4));
                    Lit1++;
                    Lit2=Lit2+3;
                    Lit3=Lit3+3;
                    Lit4=Lit4+3;
                }
                break;
            case 2:
                int Lat1=0;
                int Lat2=0;
                int Lat3=1;
                int Lat4=2;
                for (String link : helper.Get_Latvia()) {
                    data.add(helper.Get_Latvia().get(Lat1));
                    data.add(helper.Get_Border().get(Lat2)+','+helper.Get_Border().get(Lat3)+','+helper.Get_Border().get(Lat4));
                    Lat1++;
                    Lat2=Lat2+3;
                    Lat3=Lat3+3;
                    Lat4=Lat4+3;
                }
                break;
            case 3:
                int Pol1=0;
                int Pol2=18;
                int Pol3=19;
                int Pol4=20;
                for (String link : helper.Get_Poland()) {
                    data.add(helper.Get_Poland().get(Pol1));
                    data.add(helper.Get_Border().get(Pol2)+','+helper.Get_Border().get(Pol3)+','+helper.Get_Border().get(Pol4));
                    Pol1++;
                    Pol2=Pol2+3;
                    Pol3=Pol3+3;
                    Pol4=Pol4+3;
                }

                break;
            case 4:
                int Ukr1=0;
                int Ukr2=18;
                int Ukr3=19;
                int Ukr4=20;
                for (String link : helper.Get_Ukraine()) {
                    data.add(helper.Get_Ukraine().get(Ukr1));
                    data.add(helper.Get_Border().get(Ukr2)+','+helper.Get_Border().get(Ukr3)+','+helper.Get_Border().get(Ukr4));
                    Ukr1++;
                    Ukr2=Ukr2+3;
                    Ukr3=Ukr3+3;
                    Ukr4=Ukr4+3;
                }
                break;
            default:
                break;
        }
        ArrayAdapter<String> gridadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data);
        gridView1.clearTextFilter();
        gridView1.setAdapter(gridadapter);
    }
    public void Set_spinner(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,helper.Get_countries());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setEnabled(false);
        spinner.setAdapter(adapter);
        // заголовок
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                if (activeflag == 1) {

                    Toast.makeText(getBaseContext(), helper.Get_countries().get(position), Toast.LENGTH_SHORT).show();
                    Grid_fill(position);
                } else {
                    if (position != 0) {
                        waitflag = 1;
                        waitposition = position;
                        Toast.makeText(getBaseContext(), "Приложение получает данные подождите", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            spinner.setEnabled(true);
            new NewThread().execute(this);
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Проверьте интернет соединение.")
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    checkInternetConenction();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
        return false;
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

        return super.onOptionsItemSelected(item);
    }

    public class NewThread extends AsyncTask< MainActivity ,Void,Void> {

        // Метод выполняющий запрос в фоне, в версиях выше 4 андроида, запросы в главном потоке выполнять
        // нельзя, поэтому все что вам нужно выполнять - выносите в отдельный тред
        @Override
        protected Void doInBackground(MainActivity... urls) {
            // класс который захватывает страницу
            for(MainActivity url:urls)
            {
                url.helper.parse();
                url.activeflag=1;

            }
            // ничего не возвращаем потому что я так захотел)
            return null;
        }
        protected void onPostExecute(Void result) {
            if(waitflag!=0)
            {
                Grid_fill(waitposition);
                waitflag=0;
                waitposition=0;
            }
            if(updateflag!=0)
            {
                if(spinner.getSelectedItemPosition()!=0) {
                    Grid_fill(spinner.getSelectedItemPosition());
                    Toast.makeText(getBaseContext(), "Информация обновлена", Toast.LENGTH_SHORT).show();
                }
                updateflag=0;
            }
        }
    }
}