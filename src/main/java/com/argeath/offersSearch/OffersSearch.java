package com.argeath.offersSearch;

/**
 *
 * @author amino_000
 */
public class OffersSearch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread t;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(DB.getInstance().ds == null)
                    return;
                while(true) {
                    try {
                        Search cars;
                        for(int i=1; i<10; i++) {
                            Search.insert();
                            //System.out.println("History: " + History.cars.size());
                            System.out.println("-----");
                            System.out.println("Page: " + i);
                            cars = new Search("http://olx.pl/motoryzacja/samochody/?search%5Border%5D=created_at%3Adesc%3Fpage%3D1&page=" + i);
                            cars.start();
                            if(i>= 10) {
                                i=1;
                            }
                            Thread.sleep(5 * 1000);
                        }
                    } catch (InterruptedException ex) {
                        System.out.println(ex);
                        break;
                    }
                }
                DB.getInstance().ds.close();
            }
        });
        t.start();
    }
}
