package com.argeath.offersSearch;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

/**
 *
 * @author amino_000
 */
public class Search implements Runnable {
    Thread t;
    String url;
    public volatile static List<Car> cars = new ArrayList<>();
    
    Search(String s) {
        url = s;
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(url).get();
            
            Elements offers = doc.select("table[summary=Ogłoszenie]");
            for(Element el: offers) {
                String urll = el.select("a.detailsLink, a.detailsLinkPromoted").attr("href");
                Offer of = new Offer(urll);
                of.start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
    
    public static void insert() {
        if(cars.isEmpty()) {
            System.out.println("Inserted: 0");
            return;
        }
        
        try {
            try (Connection conn = DB.getInstance().ds.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO `cars` (`specId`, `brand`, `model`, `fuel`, `power`, `capacity`, `year`, `milage`, `bodyType`, `condition`, `images`, `price`, `address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                for(Car c: cars) {
                    stmt.setString(1, c.getSpecId());
                    stmt.setString(2, c.getBrand());
                    stmt.setString(3, c.getModel());
                    stmt.setString(4, c.getFuel());
                    stmt.setInt(5, c.getPower());
                    stmt.setInt(6, c.getCapacity());
                    stmt.setInt(7, c.getYear());
                    stmt.setInt(8, c.getMilage());
                    stmt.setString(9, c.getBodyType());
                    stmt.setString(10, c.getCondition());
                    stmt.setString(11, c.getImages());
                    stmt.setInt(12, c.getPrice());
                    stmt.setString(13, c.getAddress());

                    stmt.addBatch();
                    History.cars.add(c.getSpecId());
                }
                stmt.executeBatch();
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        System.out.println("Inserted: " + cars.size());
        cars.clear();
    }
    
    
}
