package fr.ideria.nooblib;

import fr.ideria.nooblib.data.json.Json;
import fr.ideria.nooblib.data.json.ToJsonAble;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains several methods that can be useful to different situations
 */
public class Nooblib {
    /**
     * To get the last element of an array
     * @param array The array that you want to get the last element
     * @return The last element of the array
     * @param <E> The type of array
     */
    public static <E> E lastOf(E[] array){ return array[array.length - 1]; }
    public static <E> E lastOf(ArrayList<E> list){ return list.get(list.size() - 1); }

    /**
     * To get a random integer between two other integers
     * @param min The minimum value of the returned integer
     * @param max The maximum value of the returned integer
     * @return A random integer between the minimum and the maximum numbers specified
     */
    public static int random(int min, int max){ return min + (int)(Math.random() * ((max - min) + 1)); }
    public static double random(double min, double max){ return min + Math.random() * ((max - min) + 1); }

    /**
     * To get a color from a json file
     * @param json The json of the color
     * @return The color stored in the json file
     */
    public static Color colorFromJson(Json json){
        int red = json.getInteger("red");
        int green = json.getInteger("green");
        int blue = json.getInteger("blue");

        return new Color(red, green, blue);
    }

    public static Color colorFromHexadecimal(String text){
        int red = Integer.parseInt(text.substring(0, 2), 16);
        int green = Integer.parseInt(text.substring(2, 4), 16);
        int blue = Integer.parseInt(text.substring(4, 6), 16);
        return new Color(red, green, blue);
    }

    /**
     * To create an embed
     * @param json The json from the color will be extracted
     * @return The created embed
     */
    public static MessageEmbed createEmbed(Json json){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(json.getString("title"));
        embed.setDescription(json.getString("description"));
        embed.setColor(Nooblib.colorFromHexadecimal(json.getString("color")));

        return embed.build();
    }

    public static int[] integerArrayFromString(String string){
        string = string
                .replace(" ","")
                .replace("[","")
                .replace("]","")
        ;

        String[] numbers = string.split(string);
        int[] array = new int[numbers.length];

        for(int i = 0; i < array.length; i++){
            array[i] = Integer.parseInt(numbers[i]);
        }

        return array;
    }

    public static float[] floatArrayFromString(String string){
        string = string
                .replace(" ","")
                .replace("[","")
                .replace("]","")
        ;

        String[] numbers = string.split(string);
        float[] array = new float[numbers.length];

        for(int i = 0; i < array.length; i++)
            array[i] = Float.parseFloat(numbers[i]);

        return array;
    }

    public static<E extends ToJsonAble> String toString(E[] array){
        StringBuilder result = new StringBuilder("[");

        for(int i = 0; i < array.length; i++){
            E e = array[i];
            result.append(e.toJson().toJSONString());

            if(i != array.length - 1)
                result.append(",");
        }

        return result.append("]").toString();
    }

    public static boolean isDigit(String text){
        List<String> digits = List.of("1234567890".split(""));

        for(String character : text.split(""))
            if(!digits.contains(character))
                return false;

        return true;
    }

    public static BufferedImage getImageFromPath(String path){
        try{
            return ImageIO.read(new File(path));
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}