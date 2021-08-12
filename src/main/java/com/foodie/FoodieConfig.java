package com.foodie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigGroup("foodie")
public interface FoodieConfig extends Config
{

    static String DELIMITER = ":";

    @ConfigSection(
            name = "Phrase Lists",
            description = "Food, Drink, Item : Phrase, Picky Eater Phrases.",
            position = 0,
            closedByDefault = true
    )
    String phraseLists = "phrases";

    @ConfigItem(
            keyName = "foodPhrases",
            name = "Food Phrases",
            description = "A colon separated list of food phrases.",
            position = 1,
            section = phraseLists
    )
    default String foodPhrases()
    {
        List<String> phrases = new ArrayList<>();
        phrases.add("Delicious! Finally some good food!");
        phrases.add("Just a little snack!");
        phrases.add("A tasty morsel!");
        phrases.add("My compliments to the chef!");
        phrases.add("Mama Mia, that's a spicy meatball!");
        phrases.add("By Nieve, I needed that!");
        phrases.add("Yum!");
        return String.join(DELIMITER, phrases);
    }

    @ConfigItem(
            keyName = "drinkPhrases",
            name = "Drink Phrases",
            description = "A colon separated list of drink phrases.",
            position = 2,
            section = phraseLists
    )
    default String drinkPhrases()
    {
        List<String> phrases = new ArrayList<>();
        phrases.add("Cheers boys!");
        phrases.add("*smash* Bring me another!");
        phrases.add("Bottoms up!");
        phrases.add("I'll feel this one tomorrow.");
        phrases.add("*Hic, Hic* In dog beers, I've only had one!");
        return String.join(DELIMITER, phrases);
    }

    @ConfigItem(
            keyName = "itemToPhrases",
            name = "Item : Phrases",
            description = "A JSON Map of consumable items to phrases.",
            position = 3,
            section = phraseLists
    )
    default String foodToPhrases()
    {
        Map<String, String> foodToPhrases = new HashMap<>();
        foodToPhrases.put("super combat potion","LEEEERRRRRROOOOYYYYY JENKINSSSSSS!!!");

        Type gsonType = new TypeToken<HashMap>(){}.getType();
        return new Gson().toJson(foodToPhrases,gsonType);
    }

    @ConfigItem(
            keyName = "pickyEaterPhrases",
            name = "Picky Eater Phrases",
            description = "A phrase for food consumed more than once in a row.",
            position = 4,
            section = phraseLists
    )
    default String pickyEaterPhrases()
    {
        List<String> phrases = new ArrayList<>();
        phrases.add("I could use a bit more variety...");
        phrases.add("Again!?");
        phrases.add("Now I know how dogs feel...");
        phrases.add("They say variety is the spice of life...");

        return String.join(DELIMITER, phrases);
    }

    @ConfigItem(
            keyName = "pickyEater",
            name = "Picky Eater",
            description = "Declare that your character is a picky eater!",
            position = 5
    )
    default boolean pickyEater()
    {
        return false;
    }

    @ConfigItem(
            keyName = "randomize",
            name = "Randomize",
            description = "Randomize the phrases for a consumed item.",
            position = 6
    )
    default boolean randomize()
    {
        return true;
    }

}