package com.argeath.offersSearch;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author amino_000
 */
public class Offer implements Runnable {
    Thread t;
    String url;
    String specId;
    
    Connection conn = null;
    
    Offer(String s) {
        url = s;
    }
    
    @Override
    public void run() {
        
        Pattern pattern = Pattern.compile("(\\-)([^\\-]*?)(\\.html)");
        Matcher matcher = pattern.matcher(url);
        while(matcher.find()) {
            specId = matcher.group(2);
        }
        
        int found = 0;
        
        if(History.cars.contains(specId)) {
            found = 1;
        } else {
            try {
                conn = DB.getInstance().ds.getConnection();

                Statement stm = conn.createStatement();
                ResultSet result = stm.executeQuery("SELECT COUNT(*) as `count` FROM `cars` WHERE `specId`='" + specId + "';");
                if(result.next()) {
                    found = result.getInt("count");
                    if(found > 0) {
                        History.cars.add(specId);
                    }
                }
                conn.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
        
        if (found == 0) {
            try {
                Document doc = Jsoup.connect(url).get();
                Element div = doc.select("div#offerdescription").get(0);
                Element table = div.select("table.details").get(0);
                
                Car car = new Car();
                
                car.setSpecId(specId);
                
                // Brand (marka)
                car.setBrand(table.select("div:contains(Marka)").select("a").text().trim());
                if(car.getBrand().isEmpty()) {
                    //System.err.println("Brand name not found.");
                    return;
                }
                
                // Model
                car.setModel(table.select("div:contains(Model)").select("a").text().trim());
                if(car.getModel().isEmpty()) {
                    //System.err.println("Model name not found.");
                    return;
                }                
                
                // Capacity (pojemność silnika)
                String capacityText = table.select("div:contains(Poj. silnika)").select("strong").text().replaceAll( "[^\\d]", "" );

                int capacity = Integer.parseInt(capacityText.substring(0, capacityText.length()-1));
                car.setCapacity(capacity);
                
                // Year (rok produkcji)
                String str = table.select("div:contains(Rok produkcji)").select("strong").text().replaceAll( "[^\\d]", "" );

                int i = Integer.parseInt(str);
                car.setYear(i);
                
                // Power (moc silnika)
                str = table.select("div:contains(Moc)").select("strong").text().replaceAll( "[^\\d]", "" );
                if(str.length() > 0) {
                    i = Integer.parseInt(str);
                    car.setPower(i);
                }
                
                // Milage (przebieg)
                str = table.select("div:contains(Przebieg)").select("strong").text().replaceAll( "[^\\d]", "" );
                if(str.length() > 0) {
                    i = Integer.parseInt(str);
                    car.setMilage(i);
                }
                // Fuel (Paliwo)
                car.setFuel(table.select("div:contains(Paliwo)").select("a").text().trim());
                
                // Condition (stan techniczny)
                car.setCondition(table.select("div:contains(Stan)").select("a").text().trim());
                
                // BodyType (typ nadwozia)
                car.setBodyType(table.select("div:contains(Typ)").select("a").text().trim());
                
                Elements imgs = doc.select("div.photo-glow > img");
                for(Element img: imgs) {
                    str = img.attr("src").trim();
                    car.addImage(str);
                }
                
                //Price (cena)
                str = doc.select("div.pricelabel").select("strong").text().trim();
                if(str.length() > 0) {
                    int doubles = StringUtils.countMatches(str, "zł");
                    
                    if(doubles > 1) {
                        str = str.substring(0, str.length()/doubles);
                    }
                    str = str.replaceAll( "[^\\d]", "" );
                    int commas = StringUtils.countMatches(str, ",");
                    
                    if(commas > 0) {
                        str = str.substring(0, str.length()-2);
                        System.out.println(car.getSpecId() + " - Przecinek - sprawdz poprawnosc. Zapisana cena: " + i);
                    }
                    
                    i = Integer.parseInt(str);
                    car.setPrice(i);
                }
                
                
                // Address
                car.setAddress(doc.select("div.address").select("p").text().trim());
                
                
                if(car.getPrice() > 0 && car.getPrice() < 300000) {
                    car.insert();
                    return;
                }
                System.out.print("!");
            } catch(IndexOutOfBoundsException ex) {
                // Offer is expired.
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
        
    
}
