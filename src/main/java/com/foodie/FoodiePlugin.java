package com.foodie;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Provides;
import javax.inject.Inject;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.lang.reflect.Type;
import java.util.*;

@PluginDescriptor(
        name = "Foodie",
        description = "Custom sayings to accompany a delicious treat."
)
public class FoodiePlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private FoodieConfig config;

    private Map<String, String> foodToPhrases = new HashMap<>();

    private String lastFoodEaten = "";

    @Override
    protected void startUp() throws Exception
    {
        updateFoodToPhrasesMap();
    }

    @Override
    protected void shutDown() throws Exception
    {
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage)
    {
        if ((chatMessage.getType().equals(ChatMessageType.SPAM) || chatMessage.getType().equals(ChatMessageType.GAMEMESSAGE))
                && (isDrink(chatMessage) || isFood(chatMessage)))
        {

            List<String> phrases = new ArrayList<>();

            for (String food : foodToPhrases.keySet()) {
                if (chatMessage.getMessage().contains(food)) {
                    phrases.add(foodToPhrases.get(food));
                }
            }

            if (config.randomize()) {
                if (isDrink(chatMessage)) {
                    phrases.addAll(Arrays.asList(config.drinkPhrases().split(FoodieConfig.DELIMITER)));
                }
                else if(isFood(chatMessage)) {
                    phrases.addAll(Arrays.asList(config.foodPhrases().split(FoodieConfig.DELIMITER)));
                }
            }

            //Override phrases with the picky eater phrases
            if (lastFoodEaten.equals(chatMessage.getMessage()) && config.pickyEater()) {
                phrases.clear();
                phrases.addAll(Arrays.asList(config.pickyEaterPhrases().split(FoodieConfig.DELIMITER)));
            }

            if (phrases.isEmpty()) {
                return;
            }

            lastFoodEaten = chatMessage.getMessage();
            client.getLocalPlayer().setOverheadText(phrases.get(new Random().nextInt(phrases.size())));
            client.getLocalPlayer().setOverheadCycle(120);
            return;
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!"foodie".equals(event.getGroup()))
        {
            return;
        }
        updateFoodToPhrasesMap();
    }

    @Provides
    FoodieConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(FoodieConfig.class);
    }

    private void updateFoodToPhrasesMap() {

        foodToPhrases.clear();
        foodToPhrases = initFoodToPhrases();

        if (config.foodToPhrases() != null && !config.foodToPhrases().isEmpty()) {
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            foodToPhrases.putAll(new Gson().fromJson(config.foodToPhrases(), mapType));
        }
    }

    /*
     * The common food to phrase map.
     * It's hidden in this class to provide easter eggs for users to find.
     * The phrase can be overwritten by the Food:Phrases config.
     */
    private Map<String, String> initFoodToPhrases() {

        Map<String, String> foodToPhrases = new HashMap<>();
        foodToPhrases.put("manta ray", "This one's for my boy Steve Irwin!");
        foodToPhrases.put("Wizard's Mind Bomb", "Wimbly bimbly, my brain now thinks nimbly!");
        foodToPhrases.put("lobster", "Bleh! Prisoner food!");
        foodToPhrases.put("purple sweet", "Ooo, piece of candy!");
        foodToPhrases.put("meat", "Bleh! It's raw!");

        return foodToPhrases;
    }

    private boolean isDrink(ChatMessage message) {
        return message.getMessage().startsWith("You drink");
    }

    private boolean isFood(ChatMessage message) {
        return message.getMessage().startsWith("You eat");
    }
}