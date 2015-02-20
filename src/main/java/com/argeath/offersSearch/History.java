package com.argeath.offersSearch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amino_000
 */
public class History {
    public volatile static List<String> cars = new ArrayList<>();
    
    public static void getLatests() {
        try {
            Connection conn = DB.getInstance().ds.getConnection();

            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery("SELECT `specId` FROM `cars` ORDER BY `id` LIMIT 500;");
            while(result.next()) {
                String specId = result.getString("specId");
                cars.add(specId);
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        System.out.println("History: " + cars.size());
    }
}
