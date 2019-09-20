package com.company;
/*
    Doplňte funkcionalitu pre aplikáciu Reakčná doba

    Princíp hry

    Hráč po zadaní svojho mena spustí hru.
    Na otázku "Pripraveny ?" odpovedá stlačením ENTER
    Objaví sa hlásenie "Pozooor ..."
    a po náhodne dlhej dobe v intervale 0.5 - 3 sekundy sa objaví povel "START !!!"
    Cieľom hráča je v najrýchlejšom možnom čase opäť stlačiť ENTER.
    Program vypíše čas v milisekundách, ktorý uplynul od zobrazenia povelu START po stlačenie ENTER
    a zaradí ho do usporiadanej tabuľky výkonov (Meno hráča + výkon)
    Na obrazovku vypíše, kde sa daný výkon v tabuľke nachádza a to tak, že vypíše
    5 bezprostredne predchádzajúcich výkonov
    aktuálny výkon
    5 bezprostredne nasledujúcich výkonov
    To všetko v tvare Poradové číslo v tabuľke výkonov Tab6 Meno hráča Tab25 výkon
    Celú tabuľku s novým záznamom zapíše do textového súboru na disk, každý riadok v tvare MenoHraca:vykon

    Hra po spustení načíta zo súboru aktuálnu tabuľku výkonov a požiada hráča o prihlásenie (zadanie mena)
    Potom zobrazí MENU s položkami
    1 - Spusť hru
    2 - Zmena hráča
    3 - TOP 10
    4 - Koniec
    A reaguje podľa výberu

    Hru naprogramujte ako konzolovú aplikáciu aj ako aplikáciu s GUI. Využite pritom MVC.
    Pre meranie času využite funkciu System.currentTimeMillis();
    Hra musí ošetriť aj predčasné stlačenie pred zobrazením START ako chybu a potrestať ju (spôsob trestu je na vás)
*/

import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
import sun.security.jgss.GSSCaller;
import sun.security.krb5.SCDynamicStoreConfig;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ReactBase {
    final int CM_PLAY = 1;
    final int CM_CHANGE_PLAYER = 2;
    final int CM_TOP10 = 3;
    final int CM_QUIT = 4;

    String Player;
    double cas;

    ArrayList<Record>list=new ArrayList<>();
    File file=new File("Player_ratings.txt");

    public static void main(String[] args) throws IOException {
        boolean gameOn;
	    ReactBase Game = new ReactBase();
	    do
            gameOn =  Game.Run();
        while (gameOn);
    }

    public ReactBase() throws IOException {
        ImportRecords();
        NewPlayer();
    }

    public boolean Run() throws IOException {
        switch(Menu()){
            case CM_CHANGE_PLAYER:
                NewPlayer();
                return true;
            case CM_PLAY:
                Double LastTime = Play(Player);
                Sort(new Record(Player, LastTime));
                ShowRecords(new Record(Player, LastTime));
                SaveRecords();
                return true;
            case CM_TOP10:
                ShowTop();
                return true;
            case CM_QUIT:
                return false;
        }
        return true;
    }

    public void ImportRecords() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null){
                String[] tempArray = line.split(":");
                list.add(new Record(tempArray[1], Double.parseDouble(tempArray[0])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            br.close();
        }
    }

    public void NewPlayer(){
        System.out.println("Zadaj meno:");
        Scanner scanner=new Scanner(System.in);
        Player=scanner.nextLine();
        System.out.println("Meno úspešne nastavené");
    }
    public int Menu(){
        int input = 0;
        Scanner scanner = null;
        System.out.println("Vitajte v nasej reakcnej hre!");
        System.out.println("MENU");
        System.out.println("1. ŠTART" + "\n" + "2. NOVY HRAC" + "\n" + "3. TOP 10" + "\n" + "4. KONIEC" + "\n");
        System.out.println("Stlacte prislusne cislo");
        try{
            scanner = new Scanner(System.in);
            input = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Zadali ste neplatny znak!");
        }finally {
            return input;
        }
    }

    public Double Play(String who){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ak si pripraveny stlac ENTER!");
        scanner.nextLine();

        int time = (int) (Math.random()*2500+500);
        System.out.println("Pozoor...");
        try {
            TimeUnit.MILLISECONDS.sleep(time);
            System.out.println("START!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double before = System.currentTimeMillis();
        scanner.nextLine();
        double after = System.currentTimeMillis();
        cas=after-before;

        if(cas <= 0)
            System.out.println("PODVADZAL SI");
        else
            System.out.println("Cas: " + (cas) +"ms");

        return cas;
    }

    public void Sort(Record record){
        list.add(record);

        Collections.sort(list, new CustomComparator());


    }

    public void ShowRecords(Record record){

        int index = list.indexOf(record);

        if(index - 5 >= 0){
            for(int i = (index - 5); i < (index+5); i++){
                list.get(i).toString();
            }
        }else {
            for (Record item : list) {
                item.toString();
            }
        }

        for (Record item : list){
            System.out.println(item.toString());
        }
    }

    public void SaveRecords() throws IOException {
        if(cas > 0){
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            for(Record record : list){
                if (record!=null){
                    bw.write(record.getTime() + ":" + record.getName() + "\n");
                }
            }
            bw.close();
        }
    }

    public void ShowTop(){
        for (int i = 0; i < 10; i ++){
            Record record = null;
            if((record = list.get(i)) != null)
                record.toString();
        }
    }

}
