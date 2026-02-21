package me.andreasmelone.pinkfont.color;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class AwtColorHexSerializer implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(!json.isJsonPrimitive()) throw new IllegalArgumentException("Color must be a int or string!");

        JsonPrimitive primitive = json.getAsJsonPrimitive();
        int argb;
        boolean wasNumber;
        if(primitive.isNumber()) {
            wasNumber = true;
            argb = primitive.getAsInt();
        } else if(primitive.isString()) {
            String str = primitive.getAsString();
            if(str.startsWith("#")) {
                str = str.substring(1);
            } else if(str.startsWith("0x")) {
                str = str.substring(2);
            }

            try {
                argb = Integer.parseUnsignedInt(str, 16);
                wasNumber = false;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Color is invalid string!", e);
            }
        } else {
            throw new IllegalArgumentException("Color must be a int or string!");
        }

        if(wasNumber) {
            return new Color(argb);
        }

        int r = (argb >> 24) & 0xFF;
        int g = (argb >> 16) & 0xFF;
        int b = (argb >> 8) & 0xFF;
        int a = argb & 0xFF;
        
        return new Color(r, g, b, a);
    }

    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        int alpha = src.getAlpha();
        int red = src.getRed();
        int green = src.getGreen();
        int blue = src.getBlue();

        String alphaHex = toHexString(alpha);
        String redHex = toHexString(red);
        String greenHex = toHexString(green);
        String blueHex = toHexString(blue);

        return new JsonPrimitive("#" + redHex + greenHex + blueHex + alphaHex);
    }

    private static String toHexString(int i) {
        if(i > 255 || i < 0) throw new IllegalArgumentException("Int must not be greater than 255!");

        return String.format("%02x", i);
    }
}
