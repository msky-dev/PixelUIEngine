package net.mslivo.pixelui.utils.persistence.highscore;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import net.mslivo.pixelui.utils.Tools;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.function.Supplier;

public class HighscoreManager {

    private final byte[] encryptionKey;
    private final Json json;
    private final Path file;
    private final int tableSizeMax;
    private Array<HighScoreTable> highscoreTables;

    public HighscoreManager(Path file, byte[] encryptionKey) {
        this(file, encryptionKey, 10);
    }

    public HighscoreManager(Path file, byte[] encryptionKey, int tableSizeMax) {
        this.file = file;
        this.encryptionKey = encryptionKey;
        this.tableSizeMax = Math.max(tableSizeMax,0);
        this.json = new Json();
        this.json.addClassTag("HighScoreEntry", HighScoreEntry.class);
        this.json.addClassTag("HighScoreTable", HighScoreTable.class);
        this.highscoreTables = new Array<>();
        this.loadScores();
    }

    private void saveScores() {
        this.trimTables();
        // Save
        String jsonScores = json.toJson(this.highscoreTables);
        byte[] encrypted = xor(jsonScores.getBytes(StandardCharsets.UTF_8), this.encryptionKey);
        String encoded = Base64.getEncoder().encodeToString(encrypted);
        Tools.File.writeTextToFile(this.file, encoded, true);
    }

    private void trimTables(){
        for(int i = 0; i<this.highscoreTables.size; i++){
            HighScoreTable table = this.highscoreTables.get(i);
            while (table.scores().size > this.tableSizeMax)
                table.scores().removeIndex(table.scores().size - 1);
        }
    }

    private HighScoreTable findTable(String name){
        for(int i = 0; i<this.highscoreTables.size; i++)
            if(this.highscoreTables.get(i).tableName.equals(name))
                return this.highscoreTables.get(i);
        return null;
    }

    public Array<HighScoreEntry> getTableScores(String table){
        HighScoreTable highScoreTable = findTable(table);
        if(table == null)
            return new Array<>();
        return highScoreTable.scores();
    }

    public CheckScoreResult checkScore(String table, long score, Supplier<String> nameEntry){
        HighScoreTable highScoreTable = findTable(table);
        if(highScoreTable == null) {
            highScoreTable = new HighScoreTable(table, new Array<>());
            this.highscoreTables.add(highScoreTable);
        }

        Array<HighScoreEntry> scores = highScoreTable.scores();

        HighScoreEntry last = scores.peek();
        if (score <= last.score())
            return new CheckScoreResult(false,0);

        String name = nameEntry.get();
        if (name == null)
            name = "?";

        HighScoreEntry entry = new HighScoreEntry(name, score);

        // Insert sorted (descending by score)
        int insertIndex = scores.size;
        for (int i = 0; i < scores.size; i++) {
            if (score > scores.get(i).score()) {
                insertIndex = i;
                break;
            }
        }
        scores.insert(insertIndex, entry);

        saveScores();
        return new CheckScoreResult(true,(insertIndex+1));
    }

    public record CheckScoreResult(boolean isHighScore, int place){
    }

    private void loadScores() {
        this.highscoreTables.clear();

        if(!Files.exists(this.file)){
            this.saveScores();
        }

        String encoded = Tools.File.readTextFromFile(this.file,true);
        byte[] encrypted = Base64.getDecoder().decode(encoded);
        byte[] decrypted = xor(encrypted, this.encryptionKey);

        String jsonScores = new String(decrypted, StandardCharsets.UTF_8);
        highscoreTables = json.fromJson(Array.class, jsonScores);
    }

    private static byte[] xor(byte[] data, byte[] key) {
        byte[] out = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            out[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return out;
    }

    public record HighScoreTable(String tableName, Array<HighScoreEntry> scores) {
    }

    public record HighScoreEntry(String name, long score) {
    }

}
