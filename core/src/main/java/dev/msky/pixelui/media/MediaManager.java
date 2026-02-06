package dev.msky.pixelui.media;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.*;
import dev.msky.pixelui.rendering.ExtendedAnimation;
import dev.msky.pixelui.utils.Tools;

import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 07.02.2019.
 */
public final class MediaManager implements Disposable {
    public static final String DIR_MUSIC = "music/", DIR_GRAPHICS = "sprites/", DIR_SOUND = "sound/", DIR_MODELS = "models/";
    public static final int FONT_CUSTOM_SYMBOL_OFFSET = 512;
    private static final String ERROR_FILE_NOT_FOUND = "CMedia File \"%s\": Does not exist";
    private static final String ERROR_ANIMATION_INVALID = "CMedia File \"%s\": Animation dimensions invalid";
    private static final String ERROR_READ_FONT = "Error reading font file \"%s\"";
    private static final String ERROR_READ_FONT_FILE_DESCRIPTOR = "Error reading font file \"%s\": file= descriptor not found";
    private static final String ERROR_READ_FONT_BASE_DECRIPTOR = "Error reading font file \"%s\": base= descriptor not found";
    private static final String ERROR_SYMBOL_ID_DUPLICATE = "Symbol \"%s\" id \"%d\" is already defined in font";
    private static final String ERROR_SYMBOL_NOT_ENOUGH_SPACE = "SymbolArray \"%s\" more symbols defined than available in texture";
    private static final String PACKED_FONT_NAME = "%s_%d.packed";
    private static final GlyphLayout glyphLayout = new GlyphLayout();
    private static final int DEFAULT_PAGE_WIDTH = 4096;
    private static final int DEFAULT_PAGE_HEIGHT = 4096;
    private static final Pattern FNT_FILE_PATTERN = Pattern.compile("file=\"([^\"]+)\"");
    private static final Pattern FNT_LINEHEIGHT_PATTERN = Pattern.compile("lineHeight=([0-9]+)");
    private static final String FONT_FILE_DATA = "char id=%d      x=%d   y=%d   width=%d   height=%d   xoffset=%d   yoffset=%d   xadvance=%d    page=0   chnl=0" + System.lineSeparator();
    private boolean loaded = false;
    private final Array<Sound> medias_sounds = new Array<>();
    private final Array<Music> medias_music = new Array<>();
    private final Array<TextureRegion> medias_images = new Array<>();
    private final Array<BitmapFont> medias_fonts = new Array<>();
    private final Array<TextureRegion[]> medias_arrays = new Array<>();
    private final Array<Texture> medias_textures = new Array<>();
    private final Array<ExtendedAnimation> medias_animations = new Array<>();
    private final Queue<CMedia> loadMediaList = new Queue<>();
    private final Array<CMedia> loadedMediaList = new Array<>();
    private TextureAtlas textureAtlas = null;

    public MediaManager() {
        unloadAndReset();
    }

    /* ----- Prepare ----- */

    public boolean prepareCMedia(CMedia cMedia) {
        if (loaded) return false;
        if (cMedia == null) return false;
        loadMediaList.addLast(cMedia);
        return true;
    }

    public boolean prepareCMedia(CMedia[] cMedias) {
        if (loaded) return false;
        for (int i = 0; i < cMedias.length; i++)
            prepareCMedia(cMedias[i]);
        return true;
    }

    public boolean prepareCMedia(Array<CMedia> cMedias) {
        if (loaded) return false;
        for (int i = 0; i < cMedias.size; i++)
            prepareCMedia(cMedias.get(i));
        return true;
    }

    public boolean isCMediaLoaded(CMedia cMedia){
        return cMedia.mediaManagerIndex != CMedia.INDEX_NONE && loadedMediaList.contains(cMedia, true);
    }

    /* ----- Load ---- */

    public boolean loadAssets() {
        return loadAssets(DEFAULT_PAGE_WIDTH, DEFAULT_PAGE_HEIGHT, null, Texture.TextureFilter.Nearest);
    }

    public boolean loadAssets(LoadProgress progress) {
        return loadAssets(DEFAULT_PAGE_WIDTH, DEFAULT_PAGE_HEIGHT, progress, Texture.TextureFilter.Nearest);
    }

    public boolean loadAssets(LoadProgress progress, Texture.TextureFilter textureFilter) {
        return loadAssets(DEFAULT_PAGE_WIDTH, DEFAULT_PAGE_HEIGHT, progress, textureFilter);
    }

    public boolean loadAssets(int pageWidth, int pageHeight) {
        return loadAssets(pageWidth, pageHeight, null, Texture.TextureFilter.Nearest);
    }


    private Pixmap modifyPixmapAddOutline(Pixmap pixmap, CMediaFont.FontOutline outline, int symbolAreaY) {
        if (outline == null) return pixmap;
        pixmap.setBlending(Pixmap.Blending.None);


        // detect outline
        final Queue<GridPoint2> outLinePoints = new Queue<>();
        final Queue<GridPoint2> removePoints = new Queue<>();
        final int outlineColorRGBA8888 = Color.rgba8888(outline.color());
        final int clearColorRGBA8888 = Color.rgba8888(Color.CLEAR);

        for (int ix = 0; ix < pixmap.getWidth(); ix++) {
            for (int iy = 0; iy < pixmap.getHeight(); iy++) {
                int pixel = pixmap.getPixel(ix, iy);
                float a = getPixelAlpha(pixel);

                if (a == 0f) continue;

                if (outline.outlineOnly()) removePoints.addLast(new GridPoint2(ix, iy));

                if (!outline.outlineSymbols() && iy > symbolAreaY) {
                    continue;
                }

                // UP/DOWN/LEFT_RIGHT
                if ((iy + 1) < pixmap.getHeight() && getPixelAlpha(pixmap.getPixel(ix, iy + 1)) == 0f && ((outline.directions() & OUTLINE.DOWN) != 0))
                    outLinePoints.addLast(new GridPoint2(ix, iy + 1));
                if ((iy - 1) >= 0 && getPixelAlpha(pixmap.getPixel(ix, iy - 1)) == 0f && ((outline.directions() & OUTLINE.UP) != 0))
                    outLinePoints.addLast(new GridPoint2(ix, iy - 1));
                if ((ix - 1) >= 0 && getPixelAlpha(pixmap.getPixel(ix - 1, iy)) == 0f && ((outline.directions() & OUTLINE.LEFT) != 0))
                    outLinePoints.addLast(new GridPoint2(ix - 1, iy));
                if ((ix + 1) < pixmap.getWidth() && getPixelAlpha(pixmap.getPixel(ix + 1, iy)) == 0f && ((outline.directions() & OUTLINE.RIGHT) != 0))
                    outLinePoints.addLast(new GridPoint2(ix + 1, iy));
                // CORNERS
                if ((ix - 1) >= 0 && (iy + 1) < pixmap.getHeight() && getPixelAlpha(pixmap.getPixel(ix - 1, iy + 1)) == 0f && ((outline.directions() & OUTLINE.LEFT_DOWN) != 0))
                    outLinePoints.addLast(new GridPoint2(ix - 1, iy + 1));
                if ((ix + 1) < pixmap.getWidth() && (iy + 1) < pixmap.getHeight() && getPixelAlpha(pixmap.getPixel(ix + 1, iy + 1)) == 0f && ((outline.directions() & OUTLINE.RIGHT_DOWN) != 0))
                    outLinePoints.addLast(new GridPoint2(ix + 1, iy + 1));
                if ((ix + 1) < pixmap.getWidth() && (iy - 1) >= 0 && getPixelAlpha(pixmap.getPixel(ix + 1, iy - 1)) == 0f && ((outline.directions() & OUTLINE.RIGHT_UP) != 0))
                    outLinePoints.addLast(new GridPoint2(ix + 1, iy - 1));
                if ((ix - 1) >= 0 && (iy - 1) >= 0 && getPixelAlpha(pixmap.getPixel(ix - 1, iy - 1)) == 0f && ((outline.directions() & OUTLINE.LEFT_UP) != 0))
                    outLinePoints.addLast(new GridPoint2(ix - 1, iy - 1));

            }
        }

        // create outline
        while (!outLinePoints.isEmpty()) {
            GridPoint2 outlinePixel = outLinePoints.removeLast();
            pixmap.drawPixel(outlinePixel.x, outlinePixel.y, outlineColorRGBA8888);
        }

        // remove
        while (!removePoints.isEmpty()) {
            GridPoint2 removePixel = removePoints.removeLast();
            pixmap.drawPixel(removePixel.x, removePixel.y, clearColorRGBA8888);
        }

        return pixmap;
    }


    private Pixmap copyPixmap(Pixmap originalPixmap, int newWidth, int newHeight, int srcX, int sryY, int srcWidth, int srcHeight) {
        Pixmap result = new Pixmap(newWidth, newHeight, originalPixmap.getFormat());
        result.setBlending(Pixmap.Blending.None);
        result.setColor(0, 0, 0, 0);
        result.fill();
        result.drawPixmap(originalPixmap, 0, 0, srcX, sryY, srcWidth, srcHeight);
        return result;
    }

    private CreateFontResult createFont(BitMapFontInformation bitMapFontInformation, CMediaFontSymbol[] symbols, CMediaFont.FontOutline outline) {


        // Load Original Texture
        Pixmap pixmap = createTexturePixmap(bitMapFontInformation.textureFileHandle);

        // Load Symbols
        StringBuilder fntFileData = new StringBuilder();
        ObjectMap<CMediaFontSymbol, Pixmap[]> symbolToPixMap = new ObjectMap<>();
        IntSet uniqueSymbolIds = new IntSet();

        for (int i = 0; i < symbols.length; i++) {
            Pixmap symbolPixmap = createTexturePixmap(Tools.File.findResource(symbols[i].file));
            Pixmap[] symbolPixmapResults;
            switch (symbols[i]) {
                case CMediaFontSymbolSingle singleSymbol -> {
                    if (!uniqueSymbolIds.contains(singleSymbol.id)) {
                        symbolPixmapResults = new Pixmap[]{symbolPixmap};
                        uniqueSymbolIds.add(singleSymbol.id);
                    } else {
                        throw new RuntimeException(String.format(ERROR_SYMBOL_ID_DUPLICATE, singleSymbol.file, singleSymbol.id));
                    }
                }
                case CMediaFontSymbolArray arraySymbol -> {
                    int symbolsX = MathUtils.floor(symbolPixmap.getWidth() / (float) arraySymbol.regionWidth);
                    int symbolsY = MathUtils.floor(symbolPixmap.getHeight() / (float) arraySymbol.regionHeight);
                    int maxSymbolsInPixMap = symbolsX * symbolsY;
                    int symbolsMax = Math.min(arraySymbol.ids.length, arraySymbol.frameLength);

                    if (symbolsMax > maxSymbolsInPixMap) {
                        throw new RuntimeException(String.format(ERROR_SYMBOL_NOT_ENOUGH_SPACE, arraySymbol.file));
                    }

                    symbolPixmapResults = new Pixmap[symbolsMax];

                    // Skip offset
                    int currentX = 0;
                    int currentY = 0;
                    for (int i2 = 0; i2 < arraySymbol.frameOffset; i2++) {
                        currentX += arraySymbol.regionWidth;
                        if (currentX + arraySymbol.regionWidth > symbolPixmap.getWidth()) {
                            currentX = 0;
                            currentY += arraySymbol.regionHeight;
                        }
                    }

                    for (int i2 = 0; i2 < symbolsMax; i2++) {
                        int symbolID = arraySymbol.ids[i2];
                        if (!uniqueSymbolIds.contains(symbolID)) {
                            symbolPixmapResults[i2] = copyPixmap(symbolPixmap, arraySymbol.regionWidth, arraySymbol.regionHeight, currentX, currentY, arraySymbol.regionWidth, arraySymbol.regionHeight);
                            uniqueSymbolIds.add(symbolID);
                        } else {
                            throw new RuntimeException(String.format(ERROR_SYMBOL_ID_DUPLICATE, arraySymbol.file, symbolID));
                        }
                        currentX += arraySymbol.regionWidth;
                        if ((currentX + arraySymbol.regionWidth) > symbolPixmap.getWidth()) {
                            currentX = 0;
                            currentY += arraySymbol.regionHeight;
                        }
                    }
                }
            }
            symbolToPixMap.put(symbols[i], symbolPixmapResults);
        }

        // Extend Pixmap at bottom to create symbol Area
        int symbolAreaHeight = 0;
        int symbolHeightMax = 0;
        int xCurrent = 0;
        for (int i = 0; i < symbols.length; i++) {
            Pixmap[] symbolPixmaps = symbolToPixMap.get(symbols[i]);
            for (int i2 = 0; i2 < symbolPixmaps.length; i2++) {
                Pixmap symbolPixmap = symbolPixmaps[i2];

                if ((xCurrent + symbolPixmap.getWidth()) > pixmap.getWidth()) {
                    symbolAreaHeight += symbolHeightMax;
                    symbolHeightMax = 0;
                    xCurrent = 0;
                }

                symbolHeightMax = Math.max(symbolHeightMax, symbolPixmap.getHeight());
                xCurrent += symbolPixmap.getWidth();
            }
        }
        symbolAreaHeight += symbolHeightMax;
        int resultPixmapWidth = pixmap.getWidth();
        int originalHeight = pixmap.getHeight();
        int resultPixMapHeight = originalHeight + symbolAreaHeight;
        pixmap = copyPixmap(pixmap, resultPixmapWidth, resultPixMapHeight, 0, 0, pixmap.getWidth(), pixmap.getHeight());


        // create symbols
        xCurrent = 0;
        symbolHeightMax = 0;
        int yCurrent = originalHeight;
        for (int i = 0; i < symbols.length; i++) {
            Pixmap[] symbolPixmaps = symbolToPixMap.get(symbols[i]);
            for (int i2 = 0; i2 < symbolPixmaps.length; i2++) {
                Pixmap symbolPixmap = symbolPixmaps[i2];

                if ((xCurrent + symbolPixmap.getWidth()) > pixmap.getWidth()) {
                    yCurrent += symbolHeightMax;
                    symbolHeightMax = 0;
                    xCurrent = 0;
                }

                pixmap.drawPixmap(symbolPixmap, xCurrent, yCurrent);

                int symbolId = switch (symbols[i]) {
                    case CMediaFontSymbolSingle singleSymbol -> singleSymbol.id;
                    case CMediaFontSymbolArray arraySymbol -> arraySymbol.ids[i2];
                };

                fntFileData.append(String.format(FONT_FILE_DATA, FONT_CUSTOM_SYMBOL_OFFSET + symbolId, xCurrent, yCurrent, symbolPixmap.getWidth(), symbolPixmap.getHeight(), -1, ((bitMapFontInformation.lineHeight - 1) - symbolPixmap.getHeight()) - symbols[i].y_offset, (symbolPixmap.getWidth() - 1) + symbols[i].x_advance));

                symbolHeightMax = Math.max(symbolHeightMax, symbolPixmap.getHeight());
                xCurrent += symbolPixmap.getWidth();
            }
        }


        // outline everything
        if (outline != null) {
            modifyPixmapAddOutline(pixmap, outline, originalHeight);
        }

        // Dispose Symbol Pixmaps
        symbolToPixMap.values().forEach(pixmaps -> {
            for (int i = 0; i < pixmaps.length; i++)
                pixmaps[i].dispose();
        });

        return new CreateFontResult(pixmap, fntFileData.toString());
    }


    private float getPixelAlpha(int pixel) {
        return (pixel & 0xFF) / 255f;
    }

    private Pixmap createTexturePixmap(FileHandle textureFileHandle) {
        TextureData textureData = TextureData.Factory.loadFromFile(textureFileHandle, Pixmap.Format.RGBA8888, false);
        textureData.prepare();
        return textureData.consumePixmap();
    }

    public boolean loadAssets(int pageWidth, int pageHeight, LoadProgress loadProgress, Texture.TextureFilter textureFilter) {
        if (loaded) return false;
        PixmapPacker pixmapPacker = new PixmapPacker(pageWidth, pageHeight, Pixmap.Format.RGBA8888, 4, true);
        Array<CMediaFont> fontCMediaLoadStack = new Array<>();
        Array<CMediaSprite> spriteCMediaLoadStack = new Array<>();
        Array<CMediaSound> soundCMediaLoadStack = new Array<>();
        Array<CMediaTexture> textureCMediaLoadStack = new Array<>();
        ObjectMap<CMediaFont, String> createFontFontFile = new ObjectMap<>();
        ObjectMap<CMediaFont, String> createFontAtlasPackedName = new ObjectMap<>();
        ObjectMap<CMediaFont, Texture> createFontFontTexture = new ObjectMap<>();
        ObjectMap<CMediaTexture, Texture> offAtlasTextures = new ObjectMap();
        int step = 0;
        int stepsMax = 0;

        // split into Image and Sound data, skip duplicates, check format and index
        while (!loadMediaList.isEmpty()) {
            final CMedia loadMedia = loadMediaList.removeFirst();

            if (!Tools.File.findResource(loadMedia.file).exists()) {
                throw new RuntimeException(String.format(ERROR_FILE_NOT_FOUND, loadMedia.file));
            }

            switch (loadMedia) {
                case CMediaSprite cMediaSprite -> spriteCMediaLoadStack.add(cMediaSprite);
                case CMediaSound cMediaSound -> soundCMediaLoadStack.add(cMediaSound);
                case CMediaFont cMediaFont -> fontCMediaLoadStack.add(cMediaFont);
                case CMediaTexture cMediaTexture -> textureCMediaLoadStack.add(cMediaTexture);
            }

            stepsMax++;
        }
        medias_images.clear();
        medias_arrays.clear();
        medias_textures.clear();
        medias_animations.clear();
        medias_fonts.clear();
        medias_sounds.clear();
        medias_music.clear();

        // Load Sprite Data Into Pixmap Packer
        for (int i = 0; i < spriteCMediaLoadStack.size; i++) {
            CMediaSprite cMediaSprite = spriteCMediaLoadStack.get(i);
            FileHandle fileHandle = Tools.File.findResource(cMediaSprite.file);
            String packedTextureName = cMediaSprite.file;
            if (pixmapPacker.getRect(packedTextureName) == null) {
                Pixmap pixmap = createTexturePixmap(fileHandle);
                pixmapPacker.pack(packedTextureName, pixmap);
                pixmap.dispose();
            }
            step++;
            if (loadProgress != null) loadProgress.onLoadStep(cMediaSprite.file, step, stepsMax);
        }

        // Create and Load Font Data Into Pixmap Packer
        int fontCount = 1;
        for (int i = 0; i < fontCMediaLoadStack.size; i++) {
            CMediaFont cMediaFont = fontCMediaLoadStack.get(i);

            BitMapFontInformation textureFileHandle = extractBitmapFontInformation(Tools.File.findResource(cMediaFont.file));
            String packedFontTextureName = String.format(PACKED_FONT_NAME, cMediaFont.file, fontCount);
            CreateFontResult fontResult = createFont(textureFileHandle, cMediaFont.symbols, cMediaFont.outline);

            createFontFontFile.put(cMediaFont, fontResult.fontFileData);

            // pack
            pixmapPacker.pack(packedFontTextureName, fontResult.pixmap);
            createFontAtlasPackedName.put(cMediaFont, packedFontTextureName);

            fontResult.pixmap.dispose();
            fontCount++;
            step++;
            if (loadProgress != null) loadProgress.onLoadStep(cMediaFont.file, step, stepsMax);
        }

        // Load Off-Atlas Textures into memory
        for (int i = 0; i < textureCMediaLoadStack.size; i++) {
            CMediaTexture cMediaTexture = textureCMediaLoadStack.get(i);
            FileHandle fileHandle = Tools.File.findResource(cMediaTexture.file);
            Texture texture = new Texture(fileHandle, cMediaTexture.format, false);
            texture.setFilter(cMediaTexture.filter, cMediaTexture.filter);
            texture.setWrap(cMediaTexture.uWrap, cMediaTexture.vWrap);
            offAtlasTextures.put(cMediaTexture, texture);
        }

        // Create TextureAtlas
        this.textureAtlas = new TextureAtlas();
        pixmapPacker.updateTextureAtlas(textureAtlas, textureFilter, textureFilter, false);
        pixmapPacker.dispose();

        // Fill medias_images, medias_arrays, medias_animations
        for (int i = 0; i < spriteCMediaLoadStack.size; i++) {
            final CMediaSprite cMediaSprite = spriteCMediaLoadStack.get(i);
            final TextureRegion textureRegion = textureAtlas.findRegion(cMediaSprite.file);
            switch (cMediaSprite) {
                case CMediaImage cMediaImage -> {
                    cMediaImage.mediaManagerIndex = medias_images.size;
                    medias_images.add(textureRegion);
                }
                case CMediaArray cMediaArray -> {
                    cMediaArray.mediaManagerIndex = medias_arrays.size;
                    medias_arrays.add(splitFrames(cMediaArray, textureRegion, cMediaArray.frameWidth, cMediaArray.frameHeight, cMediaArray.frameOffset, cMediaArray.frameLength).toArray(TextureRegion[]::new));
                }
                case CMediaAnimation cMediaAnimation -> {
                    ExtendedAnimation extendedAnimation = new ExtendedAnimation(cMediaAnimation.animationSpeed, splitFrames(cMediaAnimation, textureRegion, cMediaAnimation.frameWidth, cMediaAnimation.frameHeight, cMediaAnimation.frameOffset, cMediaAnimation.frameLength), cMediaAnimation.playMode);
                    try {
                        extendedAnimation.getKeyFrame(0);
                    } catch (ArithmeticException e) {
                        throw new RuntimeException(String.format(ERROR_ANIMATION_INVALID, cMediaAnimation.file));
                    }
                    cMediaAnimation.mediaManagerIndex = medias_animations.size;
                    medias_animations.add(extendedAnimation);
                }
            }
            loadedMediaList.add(cMediaSprite);
        }

        // Fill medias_fonts
        for (int i = 0; i < fontCMediaLoadStack.size; i++) {
            CMediaFont cMediaFont = fontCMediaLoadStack.get(i);
            TextureRegion fontTextureRegion = new TextureRegion(textureAtlas.findRegion(createFontAtlasPackedName.get(cMediaFont)));

            BitmapFont bitmapFont = new BitmapFont(new FontFileHandle(Tools.File.findResource(cMediaFont.file), createFontFontFile.get(cMediaFont)), fontTextureRegion);

            bitmapFont.setColor(Color.GRAY);
            bitmapFont.getData().markupEnabled = cMediaFont.markupEnabled;

            cMediaFont.mediaManagerIndex = medias_fonts.size;
            medias_fonts.add(bitmapFont);
        }
        // Fill medias_textures
        for (int i = 0; i < textureCMediaLoadStack.size; i++) {
            CMediaTexture cMediaTexture = textureCMediaLoadStack.get(i);
            cMediaTexture.mediaManagerIndex = medias_textures.size;
            medias_textures.add(offAtlasTextures.get(cMediaTexture));
        }

        // Fill medias_sounds, medias_music
        for (int i = 0; i < soundCMediaLoadStack.size; i++) {
            CMediaSound soundMedia = soundCMediaLoadStack.get(i);
            switch (soundMedia) {
                case CMediaSoundEffect cMediaSoundEffect -> {
                    cMediaSoundEffect.mediaManagerIndex = medias_sounds.size;
                    medias_sounds.add(Gdx.audio.newSound(Tools.File.findResource(cMediaSoundEffect.file)));
                }
                case CMediaMusic cMediaMusic -> {
                    cMediaMusic.mediaManagerIndex = medias_music.size;
                    medias_music.add(Gdx.audio.newMusic(Tools.File.findResource(soundMedia.file)));
                }
            }
            loadedMediaList.add(soundMedia);
            step++;
            if (loadProgress != null) loadProgress.onLoadStep(soundMedia.file, step, stepsMax);
        }
        soundCMediaLoadStack.clear();

        // 7. Clean up & Finish
        spriteCMediaLoadStack.clear();
        createFontFontFile.clear();
        createFontAtlasPackedName.clear();
        this.loaded = true;
        return true;
    }


    private BitMapFontInformation extractBitmapFontInformation(FileHandle fontFileHandle) {
        boolean fileFound = false, baseFound = false;
        FileHandle textureHandle = null;
        int lineHeight = 0;
        try (BufferedReader bufferedReader = fontFileHandle.reader(1024, Charset.defaultCharset().name())) {
            String line;
            Matcher matcher;
            while ((line = bufferedReader.readLine()) != null && (!fileFound || !baseFound)) {
                if (!fileFound) {
                    matcher = FNT_FILE_PATTERN.matcher(line);
                    if (matcher.find()) {
                        textureHandle = Tools.File.findResource(fontFileHandle.parent() + "/" + matcher.group(1));
                        fileFound = true;
                    }
                }
                if (!baseFound) {
                    matcher = FNT_LINEHEIGHT_PATTERN.matcher(line);
                    if (matcher.find()) {
                        lineHeight = Integer.parseInt(matcher.group(1));
                        baseFound = true;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(ERROR_READ_FONT, e);
        }

        if (!fileFound) throw new RuntimeException(ERROR_READ_FONT_FILE_DESCRIPTOR);
        if (!baseFound) throw new RuntimeException(ERROR_READ_FONT_BASE_DECRIPTOR);

        return new BitMapFontInformation(textureHandle, lineHeight);
    }

    private Array<TextureRegion> splitFrames(CMediaSprite cMediaSprite, TextureRegion textureRegion, int tile_width, int tile_height, int frameOffset, int frameLength) {
        int width = (textureRegion.getRegionWidth() / tile_width);
        int height = (textureRegion.getRegionHeight() / tile_height);
        int maxFrames = width * height;
        int toFrame = Math.min(frameOffset + frameLength, maxFrames - 1);

        TextureRegion[][] tmp = textureRegion.split(tile_width, tile_height);
        Array<TextureRegion> result = new Array<>();
        int frameCounter = 0;
        framesLoop:
        for (int ix = 0; ix < tmp.length; ix++) {
            for (int iy = 0; iy < tmp[0].length; iy++) {
                frameCounter++;
                if (frameCounter > frameOffset) {
                    result.add(tmp[ix][iy]);
                    if (result.size >= frameLength)
                        break framesLoop;
                }
            }
        }
        return result;
    }


    /* --- Unload  ---- */
    public boolean unloadAndReset() {
        if (!loaded) return false;
        // Dispose Atlas
        if (textureAtlas != null) this.textureAtlas.dispose();
        textureAtlas = null;

        // Dispose and null
        medias_textures.forEach(texture -> texture.dispose());
        medias_sounds.forEach(sound -> sound.dispose());
        medias_music.forEach(music -> music.dispose());
        medias_fonts.forEach(bitmapFont -> bitmapFont.dispose());

        this.medias_textures.clear();
        this.medias_images.clear();
        this.medias_arrays.clear();
        this.medias_animations.clear();
        this.medias_sounds.clear();
        this.medias_music.clear();
        this.medias_fonts.clear();

        // Reset lists
        this.loadedMediaList.clear();
        this.loadMediaList.clear();
        this.loaded = false;
        return true;
    }

    public TextureRegion sprite(CMediaSprite cMediaSprite, int arrayIndex, float animationTimer) {
        return switch (cMediaSprite) {
            case CMediaImage cMediaImage -> medias_images.get(cMediaImage.mediaManagerIndex);
            case CMediaAnimation cMediaAnimation -> medias_animations.get(cMediaAnimation.mediaManagerIndex).getKeyFrame(animationTimer);
            case CMediaArray cMediaArray -> medias_arrays.get(cMediaArray.mediaManagerIndex)[arrayIndex];
        };
    }

    public Texture texture(CMediaTexture cMediaTexture) {
        return medias_textures.get(cMediaTexture.mediaManagerIndex);
    }

    public TextureRegion image(CMediaImage cMediaImage) {
        return medias_images.get(cMediaImage.mediaManagerIndex);
    }

    public ExtendedAnimation animation(CMediaAnimation cMediaAnimation) {
        return medias_animations.get(cMediaAnimation.mediaManagerIndex);
    }

    public TextureRegion array(CMediaArray cMediaArray, int arrayIndex) {
        return medias_arrays.get(cMediaArray.mediaManagerIndex)[arrayIndex];
    }

    public Sound sound(CMediaSoundEffect cMediaSoundEffect) {
        return medias_sounds.get(cMediaSoundEffect.mediaManagerIndex);
    }

    public Music music(CMediaMusic cMediaMusic) {
        return medias_music.get(cMediaMusic.mediaManagerIndex);
    }

    public int spriteWidth(CMediaSprite cMediaSprite) {
        return switch (cMediaSprite) {
            case CMediaImage cMediaImage -> medias_images.get(cMediaImage.mediaManagerIndex).getRegionWidth();
            case CMediaArray cMediaArray -> cMediaArray.frameWidth;
            case CMediaAnimation cMediaAnimation -> cMediaAnimation.frameWidth;
            default -> throw new IllegalStateException("Unexpected value: " + cMediaSprite);
        };
    }

    public int textureWidth(CMediaTexture cMediaTexture) {
        return medias_textures.get(cMediaTexture.mediaManagerIndex).getWidth();
    }

    public int textureHeight(CMediaTexture cMediaTexture) {
        return medias_textures.get(cMediaTexture.mediaManagerIndex).getWidth();
    }


    public int imageWidth(CMediaImage cMediaImage) {
        return medias_images.get(cMediaImage.mediaManagerIndex).getRegionWidth();
    }

    public int arrayWidth(CMediaArray cMediaArray) {
        return cMediaArray.frameWidth;
    }

    public int animationWidth(CMediaAnimation cMediaAnimation) {
        return cMediaAnimation.frameWidth;
    }

    public int spriteHeight(CMediaSprite cMedia) {
        return switch (cMedia) {
            case CMediaImage cMediaImage -> medias_images.get(cMediaImage.mediaManagerIndex).getRegionHeight();
            case CMediaArray cMediaArray -> cMediaArray.frameHeight;
            case CMediaAnimation cMediaAnimation -> cMediaAnimation.frameHeight;
        };
    }

    public int imageHeight(CMediaImage cMediaImage) {
        return medias_images.get(cMediaImage.mediaManagerIndex).getRegionHeight();
    }

    public int arrayHeight(CMediaArray cMediaArray) {
        return cMediaArray.frameHeight;
    }

    public int animationHeight(CMediaAnimation cMediaAnimation) {
        return cMediaAnimation.frameHeight;
    }

    public int spriteWidthHalf(CMediaSprite cMediaSprite) {
        return MathUtils.floor(spriteWidth(cMediaSprite) / 2f);
    }

    public int spriteHeightHalf(CMediaSprite cMediaSprite) {
        return MathUtils.floor(spriteHeight(cMediaSprite) / 2f);
    }

    public int arrayWidthHalf(CMediaArray cMediaArray) {
        return MathUtils.floor(arrayWidth(cMediaArray) / 2f);
    }

    public int arrayHeightHalf(CMediaArray cMediaArray) {
        return MathUtils.floor(arrayHeight(cMediaArray) / 2f);
    }

    public int imageWidthHalf(CMediaImage cMediaImage) {
        return MathUtils.floor(imageWidth(cMediaImage) / 2f);
    }

    public int imageHeightHalf(CMediaImage cMediaImage) {
        return MathUtils.floor(imageHeight(cMediaImage) / 2f);
    }

    public int animationWidthHalf(CMediaAnimation cMediaAnimation) {
        return MathUtils.floor(animationWidth(cMediaAnimation) / 2f);
    }

    public int animationHeightHalf(CMediaAnimation cMediaAnimation) {
        return MathUtils.floor(animationHeight(cMediaAnimation) / 2f);
    }

    public int textureWidthHalf(CMediaTexture cMediaTexture) {
        return MathUtils.floor(textureWidth(cMediaTexture) / 2f);
    }

    public int textureHeightHalf(CMediaTexture cMediaTexture) {
        return MathUtils.floor(textureWidth(cMediaTexture) / 2f);
    }

    public int arraySize(CMediaArray cMediaArray) {
        return medias_arrays.get(cMediaArray.mediaManagerIndex).length;
    }

    public int arrayLastIndex(CMediaArray cMediaArray) {
        return medias_arrays.get(cMediaArray.mediaManagerIndex).length - 1;
    }

    public int arrayIndex(CMediaArray cMediaArray, float pct) {
        final int lastIndex = medias_arrays.get(cMediaArray.mediaManagerIndex).length - 1;
        return MathUtils.floor(Math.clamp(pct, 0f, 1f) * lastIndex);
    }

    public BitmapFont font(CMediaFont cMediaFont) {
        return medias_fonts.get(cMediaFont.mediaManagerIndex);
    }

    public int fontTextWidth(final CMediaFont cMediaFont, final CharSequence text) {
        return fontTextWidth(cMediaFont, text, 0, text.length());
    }

    public int fontTextWidth(final CMediaFont cMediaFont, final CharSequence text, final int start, final int end) {
        final BitmapFont font = font(cMediaFont);
        final int textLength = text.length();
        glyphLayout.setText(font, text, Math.min(start, textLength), Math.min(end, textLength), font.getColor(), 0, Align.left, false, null);
        return MathUtils.round(glyphLayout.width);
    }

    public int fontTextHeight(final CMediaFont cMediaFont, final CharSequence text) {
        return fontTextHeight(cMediaFont, text, 0, text.length());
    }

    public int fontTextHeight(final CMediaFont cMediaFont, final CharSequence text, final int start, final int end) {
        final BitmapFont font = font(cMediaFont);
        glyphLayout.setText(font(cMediaFont), text, start, end, font.getColor(), 0, Align.left, false, null);
        return MathUtils.round(glyphLayout.height);
    }

    /* ---- Shutdown ---- */
    @Override
    public void dispose() {
        this.unloadAndReset();
    }

    public boolean isLoaded() {
        return loaded;
    }

    private class BitMapFontInformation {
        public final FileHandle textureFileHandle;
        public final int lineHeight;

        public BitMapFontInformation(FileHandle textureFileHandle, int lineHeight) {
            this.textureFileHandle = textureFileHandle;
            this.lineHeight = lineHeight;
        }
    }

    private class CreateFontResult {
        public final Pixmap pixmap;
        public final String fontFileData;

        public CreateFontResult(Pixmap pixmap, String fontFileData) {
            this.pixmap = pixmap;
            this.fontFileData = fontFileData;
        }
    }

    private class FontFileHandle extends FileHandle {
        private final byte[] modifiedData;

        public FontFileHandle(FileHandle originalFile, String additionalData) {
            super(originalFile.file());

            // Read original file content
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (InputStream inputStream = originalFile.read()) {
                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Append additional data
                outputStream.write(additionalData.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Store the combined data
            this.modifiedData = outputStream.toByteArray();
        }

        @Override
        public InputStream read() {
            // Provide the modified data as an InputStream
            return new ByteArrayInputStream(modifiedData);
        }
    }

}
