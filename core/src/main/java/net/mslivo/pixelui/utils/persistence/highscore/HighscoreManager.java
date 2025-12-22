package net.mslivo.pixelui.utils.persistence.highscore;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import net.mslivo.pixelui.utils.Tools;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;

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
        this.tableSizeMax = Math.max(tableSizeMax, 0);
        this.json = new Json();
        this.json.addClassTag("HighScoreEntry", HighScoreEntry.class);
        this.json.addClassTag("HighScoreTable", HighScoreTable.class);
        this.json.setSerializer(BigInteger.class, new Json.Serializer<BigInteger>() {
            public void write(Json json, BigInteger object, Class knownType) {
                json.writeValue(object == null ? null : object.toString());
            }

            @Override
            public BigInteger read(Json json, JsonValue jsonData, Class type) {
                return jsonData == null || jsonData.isNull()
                        ? null
                        : new BigInteger(jsonData.asString());
            }
        });
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

    private void trimTables() {
        for (int i = 0; i < this.highscoreTables.size; i++) {
            HighScoreTable table = this.highscoreTables.get(i);
            while (table.scores().size > this.tableSizeMax)
                table.scores().removeIndex(table.scores().size - 1);
        }
    }

    private HighScoreTable findOrCreateTable(String table) {
        for (int i = 0; i < this.highscoreTables.size; i++)
            if (this.highscoreTables.get(i).tableName.equals(table))
                return this.highscoreTables.get(i);
        HighScoreTable highScoreTable = new HighScoreTable(table, new Array<>());
        this.highscoreTables.add(highScoreTable);
        return highScoreTable;
    }

    public Array<HighScoreEntry> getTableScores(String table) {
        HighScoreTable highScoreTable = findOrCreateTable(table);
        if (table == null)
            return new Array<>();
        return highScoreTable.scores();
    }

    public boolean isHighScore(String table, long score) {
        return isHighScore(table, BigInteger.valueOf(score));
    }

    public boolean isHighScore(String table, BigInteger score) {
        HighScoreTable highScoreTable = findOrCreateTable(table);
        Array<HighScoreEntry> scores = highScoreTable.scores();
        if (scores.size >= this.tableSizeMax && score.compareTo(scores.peek().score()) <= 0)
            return false;
        return true;
    }

    public SaveScoreResult saveScore(String table, String name, long score) {
        return saveScore(table,name, BigInteger.valueOf(score));
    }

    public SaveScoreResult saveScore(String table, String name, BigInteger score) {
        name = name != null && !name.isBlank() ? name : "?";
        if (!isHighScore(table, score))
            return new SaveScoreResult(table, name, score, 0, false);

        HighScoreTable highScoreTable = findOrCreateTable(table);

        Array<HighScoreEntry> scores = highScoreTable.scores();
        HighScoreEntry entry = new HighScoreEntry(name, score);

        // Insert sorted (descending by score)
        int insertIndex = scores.size;
        for (int i = 0; i < scores.size; i++) {
            if (score.compareTo(scores.get(i).score()) > 0) {
                insertIndex = i;
                break;
            }
        }
        scores.insert(insertIndex, entry);

        saveScores();
        return new SaveScoreResult(table, name, score, (insertIndex + 1), true);
    }


    private void loadScores() {
        this.highscoreTables.clear();

        if (!Files.exists(this.file)) {
            this.saveScores();
        }

        String encoded = Tools.File.readTextFromFile(this.file, true);
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

    public static final class HighScoreTable {
        private String tableName;
        private Array<HighScoreEntry> scores;

        public HighScoreTable(String tableName, Array<HighScoreEntry> scores) {
            this.tableName = tableName;
            this.scores = scores;
        }

        public HighScoreTable() {
        }

        public String tableName() {
            return tableName;
        }

        public Array<HighScoreEntry> scores() {
            return scores;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (HighScoreTable) obj;
            return Objects.equals(this.tableName, that.tableName) &&
                    Objects.equals(this.scores, that.scores);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tableName, scores);
        }

        @Override
        public String toString() {
            return "HighScoreTable[" +
                    "tableName=" + tableName + ", " +
                    "scores=" + scores + ']';
        }

    }

    public static final class HighScoreEntry {
        private String name;
        private BigInteger score;

        public HighScoreEntry(String name, BigInteger score) {
            this.name = name;
            this.score = score;
        }

        public HighScoreEntry() {
        }

        public String name() {
            return name;
        }

        public BigInteger score() {
            return score;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (HighScoreEntry) obj;
            return Objects.equals(this.name, that.name) &&
                    this.score == that.score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, score);
        }

        @Override
        public String toString() {
            return "HighScoreEntry[" +
                    "name=" + name + ", " +
                    "score=" + score + ']';
        }

    }

}
