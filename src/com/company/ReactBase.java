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

import sun.security.jgss.GSSCaller;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;

public class ReactBase {
    final int CM_PLAY = 1;
    final int CM_CHANGE_PLAYER = 2;
    final int CM_TOP10 = 3;
    final int CM_QUIT = 4;
    String Player;
    double cas;
    ArrayList<String>list=new ArrayList<>();
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
                int LastTime = Play(Player);
                Sort(Player, LastTime);
                ShowRecords(Player, LastTime);
                SaveRecords();
                return true;
            case CM_TOP10:
                ShowRecords("", 0);
                return true;
            case CM_QUIT:
                return false;
        }
        return true;
    }

    public void ImportRecords() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            while (br.readLine()!=null){
            list.add(br.readLine());
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

    public int Play(String who){
        return Integer.MAX_VALUE;
    }

    public void Sort(String who, int record){}

    public void ShowRecords(String who, int record){}

    public void SaveRecords() throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
    }

}
