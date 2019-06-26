import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Scraper {
    private static String[] pokemonList = {
            "Missingno", "Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon", "Charizard", "Squirtle", "Wartortle", "Blastoise", "Caterpie", "Metapod", "Butterfree", "Weedle", "Kakuna", "Beedrill", "Pidgey", "Pidgeotto", "Pidgeot", "Rattata", "Raticate", "Spearow", "Fearow", "Ekans", "Arbok", "Pikachu", "Raichu", "Sandshrew", "Sandslash", "Nidoran♀", "Nidorina", "Nidoqueen", "Nidoran♂", "Nidorino", "Nidoking", "Clefairy", "Clefable", "Vulpix", "Ninetales", "Jigglypuff", "Wigglytuff", "Zubat", "Golbat", "Oddish", "Gloom", "Vileplume", "Paras", "Parasect", "Venonat", "Venomoth", "Diglett", "Dugtrio", "Meowth", "Persian", "Psyduck", "Golduck", "Mankey", "Primeape", "Growlithe", "Arcanine", "Poliwag", "Poliwhirl", "Poliwrath", "Abra", "Kadabra", "Alakazam", "Machop", "Machoke", "Machamp", "Bellsprout", "Weepinbell", "Victreebel", "Tentacool", "Tentacruel", "Geodude", "Graveler", "Golem", "Ponyta", "Rapidash", "Slowpoke", "Slowbro", "Magnemite", "Magneton", "Farfetch'd", "Doduo", "Dodrio", "Seel", "Dewgong", "Grimer", "Muk", "Shellder", "Cloyster", "Gastly", "Haunter", "Gengar", "Onix", "Drowzee", "Hypno", "Krabby", "Kingler", "Voltorb", "Electrode", "Exeggcute", "Exeggutor", "Cubone", "Marowak", "Hitmonlee", "Hitmonchan", "Lickitung", "Koffing", "Weezing", "Rhyhorn", "Rhydon", "Chansey", "Tangela", "Kangaskhan", "Horsea", "Seadra", "Goldeen", "Seaking", "Staryu", "Starmie", "Mr. Mime", "Scyther", "Jynx", "Electabuzz", "Magmar", "Pinsir", "Tauros", "Magikarp", "Gyarados", "Lapras", "Ditto", "Eevee", "Vaporeon", "Jolteon", "Flareon", "Porygon", "Omanyte", "Omastar", "Kabuto", "Kabutops", "Aerodactyl", "Snorlax", "Articuno", "Zapdos", "Moltres", "Dratini", "Dragonair", "Dragonite", "Mewtwo", "Mew"
    };

    public static void scrapeImages() {
        for(int i = 0; i < pokemonList.length; i++) {
            (new File("data" + File.separator + pokemonList[i])).mkdir() ;

            for(int j = 0; j < pokemonList.length; j++) {
                try {
                    Document d = Jsoup.connect("https://pokemon.alexonsager.net/" + i + "/" + j).userAgent("Mozilla/5.0").get();
                    HttpURLConnection httpCon = (HttpURLConnection)(new URL(d.getElementById("pk_img").attr("src")).openConnection());
                    httpCon.addRequestProperty("User-Agent", "Mozilla/5.0");
                    if(httpCon.getResponseCode() == 200) {
                        InputStream inputStream = httpCon.getInputStream();
                        File f = new File("data" + File.separator + pokemonList[i] + File.separator + d.getElementById("pk_name").text() + "-" + i + "-" + j + ".png");
                        FileOutputStream outputStream = new FileOutputStream(f);
                        int bytesRead;
                        byte[] buffer = new byte[4096];
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.close();
                        inputStream.close();
                        System.out.println(pokemonList[i] + "   [" + i + "]: Added fusion with " + pokemonList[j] + " ("+(j+1)+"/" + pokemonList.length + ")");
                    } else {
                        System.out.println("File download failed on " + pokemonList[i] + " x " + pokemonList[j]);
                    }

                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void findMissing(String path) {
        ArrayList<Integer> names = new ArrayList<>();

        for(File f : new File(path).listFiles()) {
            names.add(Integer.parseInt(f.getName().substring(f.getName().lastIndexOf("-")+1, f.getName().lastIndexOf("."))));
        }
        int sum = 152*(151)/2;

        for(Integer i : names) {
            sum -= i;
        }

        Collections.sort(names);
        System.out.println(names);
        System.out.println(sum);

    }

    public static void main(String[] args) {
//        scrapeImages();
        findMissing("/Users/zackamiton/Code/Fusiondex/data/Porygon");
    }
}
