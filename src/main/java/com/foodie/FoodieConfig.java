package com.foodie;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.util.ArrayList;
import java.util.List;

@ConfigGroup("foodie")
public interface FoodieConfig extends Config
{
    @ConfigItem(
            keyName = "foodPhrases",
            name = "Food Phrases",
            description = "A comma separated list of food phrases.",
            position = 1
    )
    default String foodPhrases()
    {
        List<String> phrases = new ArrayList<>();
        phrases.add("Delicious! Finally some good food!");
        phrases.add("Ugh! Hard to fit anymore food. Maybe a beer instead...");
        phrases.add("Just a little snack!");
        return String.join(",", phrases);
    }

    @ConfigItem(
            keyName = "drinkPhrases",
            name = "Drink Phrases",
            description = "A comma separated list of drink phrases.",
            position = 2
    )
    default String drinkPhrases()
    {
        List<String> phrases = new ArrayList<>();
        phrases.add("Cheers boys!");
        phrases.add("*smash* Bring me another!");
        return String.join(",", phrases);
    }

    @ConfigItem(
            keyName = "itemToPhrases",
            name = "Item : Phrases",
            description = "A JSON Map of consumable items to phrases.",
            position = 3
    )
    default String foodToPhrases()
    {
        List<String> foodPhrases = new ArrayList<>();
        foodPhrases.add("\"manta ray\" : \"This one's for my boy Steve Irwin!\"");
        foodPhrases.add("\"Wizard's Mind Bomb\":\"Wimbly bimbly, my brain now thinks nimbly!\"");
        foodPhrases.add("\"super combat potion\":\"LERRRRRROOOOYYYYY JENKINSSSSSS!!!\"");
        return "{" + String.join(",", foodPhrases) + "}";
    }

    @ConfigItem(
            keyName = "randomize",
            name = "Randomize",
            description = "Randomize the phrases for a consumed item.",
            position = 4
    )
    default boolean randomize()
    {
        return true;
    }
}