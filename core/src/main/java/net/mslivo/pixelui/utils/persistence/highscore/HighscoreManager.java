package net.mslivo.pixelui.utils.persistence.highscore;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import net.mslivo.pixelui.utils.Tools;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

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

    private HighScoreTable findOrCreateTable(String table){
        for(int i = 0; i<this.highscoreTables.size; i++)
            if(this.highscoreTables.get(i).tableName.equals(table))
                return this.highscoreTables.get(i);
        HighScoreTable highScoreTable = new HighScoreTable(table, new Array<>());
        this.highscoreTables.add(highScoreTable);
        return highScoreTable;
    }

    public Array<HighScoreEntry> getTableScores(String table){
        HighScoreTable highScoreTable = findOrCreateTable(table);
        if(table == null)
            return new Array<>();
        return highScoreTable.scores();
    }

    public boolean isHighScore(String table, long score){
        HighScoreTable highScoreTable = findOrCreateTable(table);
        Array<HighScoreEntry> scores = highScoreTable.scores();
        HighScoreEntry last = scores.peek();
        if (score <= last.score())
            return false;
        return true;
    }

    public SaveScoreResult saveScore(String table, String name, long score){
        name = name != null && !name.isBlank() ? name : "?";
        if(!isHighScore(table, score))
            return new SaveScoreResult(table,name, score,0,false);

        HighScoreTable highScoreTable = findOrCreateTable(table);

        Array<HighScoreEntry> scores = highScoreTable.scores();
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
        return new SaveScoreResult(table, name, score,(insertIndex+1), true);
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
