package com.example.user.bborders;

/**
 * Created by User on 12.11.2015.
 */
import java.net.URL;


import java.lang.String;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Helper {
    private static List<String> list = new ArrayList<String>() {
        {
            add("Выберите страну");
            add("Литва");
            add("Латвия");
            add("Польша");
            add("Украина");
        }
    };
    private static List<String> Litva_list = new ArrayList<String>() {
        {
            add("Котловка");
            add("Каменный Лог");
            add("Бенякони");
            add("Привалка");
        }
    };
    private static List<String> Latvia_list = new ArrayList<String>() {
        {
            add("Григоровщин");
            add("Урбаны");
        }
    };
    private static List<String> Poland_list = new ArrayList<String>() {
        {
            add("Брузги");
            add("Берестовица");
            add("Козловичи");
            add("Брест,авто.");
            add("Домачево");
            add("Песчатка");
        }
    };
    private static List<String> Ukraine_list = new ArrayList<String>() {
        {
            add("Александров");
            add("Верхний Теребежов");
            add("Веселовка");
            add("Комарин");
            add("Мокраны");
            add("Мохро");
            add("Невель");
            add("Новая Гута");
            add("Новая Рудня");
            add("Олтуш");
            add("Томашовка");
            add("Глушкевичи");
        }
    };
    private Document doc;
    private Elements metaElements;
    private List<String> border_data=new ArrayList<String>();
    public void parse()
    {
        try {
            doc = Jsoup.connect("http://gpk.gov.by/maps/ochered.php").get();
            metaElements = doc.select("td");
            int i=0;

            for (Element link : metaElements) {
                if(!link.ownText().equals("") && i>=12)
                {
                    border_data.add(link.ownText());
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Helper()
    {

    }

    public List<String> Get_countries()
    {
        return list;
    }
    public List<String> Get_Litva()
    {
        return Litva_list;
    }
    public List<String> Get_Latvia()
    {
        return Latvia_list;
    }
    public List<String> Get_Poland()
    {
        return Poland_list;
    }
    public List<String> Get_Ukraine()
    {
        return Ukraine_list;
    }
    public List<String> Get_Border()
    {
        return border_data;
    }
}

