package net.ggwpgaming.automessage.event;

import net.ggwpgaming.automessage.AutoMessage;
import net.ggwpgaming.automessage.config.AMServerConfigs;
import net.ggwpgaming.automessage.util.AMIO;
import net.ggwpgaming.automessage.util.ValuesNode;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid = AutoMessage.MOD_ID, value = Dist.DEDICATED_SERVER)
public class AMServerHandler {
    public static final String AMServerLogName = "automessage-SERVER_VIEWCOUNT_LOG.txt";
    public static final ArrayList<Integer> SERVER_LIMIT_COUNTER = new ArrayList<>();
    public static final File serverLogCheck = new File(AMServerLogName);
    public static final int initMessageSent = 0;
    static {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, AMServerConfigs.SPEC, "automessage-server.toml");
    }

    @SubscribeEvent
    public static void serverServerTickEvent(TickEvent.ServerTickEvent event)
    {
        if (event.side == LogicalSide.SERVER) {
            if (SERVER_LIMIT_COUNTER.size() == 0)
            {
                for (int listIndex = 0; listIndex < AMServerConfigs.MESSAGES.get().size(); listIndex++) {
                    SERVER_LIMIT_COUNTER.add(0);
                }
            }

            List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();

            if (!serverLogCheck.isFile() && !serverLogCheck.isDirectory())
            {
                LinkedList<ValuesNode> linkedList = new LinkedList<>();
                ArrayList<Integer> initValues = new ArrayList<>();

                if (AMServerConfigs.MESSAGES.get().size() != 0) {
                    for (int i = 0; i < AMServerConfigs.MESSAGES.get().size(); i++) {
                        initValues.add(i, 0);
                    }
                }

                if (AMServerConfigs.MESSAGES.get().size() != 0 && players.size() != 0) {
                    for (int i = 0; i < players.size(); i++) {
                        linkedList.add(new ValuesNode(players.get(i).getStringUUID(), initValues));
                    }
                }

                if (linkedList.size() != 0) {
                    AMIO.writeLinkedListToFile(linkedList, AMServerLogName);
                }

                AutoMessage.println(AMServerLogName + " was initialized!");
            } else if (serverLogCheck.isFile() && !serverLogCheck.isDirectory())
            {
                LinkedList<ValuesNode> retrievedList;

                try
                {
                    retrievedList = AMIO.readLinkedListFromFile(AMServerLogName);
                } catch (Exception exception) {
                    AutoMessage.println(AMServerLogName + " IS CORRUPTED! PLEASE DELETE THE FILE AND RESTART 001!");
                    retrievedList = new LinkedList<ValuesNode>();
                }

                if (players.size() != 0 && retrievedList.size() != 0) {
                    for (int i = 0; i < players.size(); i++) {
                        Player player = players.get(i);
                        if (players.get(i).getStringUUID().equals(retrievedList.get(i).UUID.toString())) {
                            for (int messageIndex = 0; messageIndex < AMServerConfigs.MESSAGES.get().size(); messageIndex++) {
                                final int thisMessageIndex = messageIndex;
                                if (!AMServerConfigs.MESSAGES.get().get(messageIndex).equals("") && (event.getServer().getTickCount() % (AMServerConfigs.INTERVALS.get().get(messageIndex) * 20) == 0) && event.phase == TickEvent.Phase.START) {
                                    if ((SERVER_LIMIT_COUNTER.get(messageIndex) < AMServerConfigs.LIMITS.get().get(messageIndex) &&
                                            retrievedList.get(i).values.get(messageIndex) < AMServerConfigs.HARD_LIMITS.get().get(messageIndex)) ||
                                            (AMServerConfigs.HARD_LIMITS.get().get(messageIndex) == 0)) {
                                        player.sendSystemMessage
                                                (
                                                        Component.literal(AMServerConfigs.MESSAGES.get().get(messageIndex))
                                                                .withStyle(
                                                                        style -> style.withClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, String.valueOf( AMServerConfigs.LINKS.get().get(thisMessageIndex) ) ) )
                                                                )
                                                );
                                        SERVER_LIMIT_COUNTER.set(messageIndex, SERVER_LIMIT_COUNTER.get(messageIndex) + 1);

                                        LinkedList<ValuesNode> linkedList = new LinkedList<>();
                                        ArrayList<Integer> initValues = new ArrayList<>();

                                        if (AMServerConfigs.MESSAGES.get().size() != 0) {
                                            for (int messageCounter = 0; messageCounter < AMServerConfigs.MESSAGES.get().size(); messageCounter++) {
                                                if (messageCounter == messageIndex) {
                                                    initValues.add(messageCounter, retrievedList.get(i).values.get(messageCounter) + 1);
                                                } else {
                                                    initValues.add(messageCounter, retrievedList.get(i).values.get(messageCounter));
                                                }
                                            }
                                        }

                                        if (AMServerConfigs.MESSAGES.get().size() != 0 && players.size() != 0) {
                                            for (int playerCounter = 0; playerCounter < players.size(); playerCounter++) {
                                                linkedList.add(new ValuesNode(players.get(playerCounter).getStringUUID(), initValues));
                                            }
                                        }

                                        if (linkedList.size() != 0) {
                                            try {
                                                AMIO.writeLinkedListToFile(linkedList, AMServerLogName);
                                            } catch(Exception e) {
                                                AutoMessage.println(e.toString());
                                                AMIO.writeLinkedListToFile(linkedList, AMServerLogName);
                                            }
                                        }

                                        AutoMessage.println(AMServerLogName + " has been updated after message sent: " + AMServerConfigs.MESSAGES.get().get(messageIndex));
                                    }
                                }
                            }
                        } else {
                            AutoMessage.println(players.get(i).getStringUUID() + " DOES NOT MATCH " + retrievedList.get(i).UUID + "!!!");
                            AutoMessage.println(AMServerLogName + " IS CORRUPTED! PLEASE DELETE THE FILE AND RESTART 002!");
                        }
                    }
                } else {
                    if (!(players.size() == 0)) {
                        AutoMessage.println(AMServerLogName + " IS CORRUPTED! PLEASE DELETE THE FILE AND RESTART 003!");
                    }
                }
            }
            
            
            
            // SERVER SIDE CHECK NOW
            
        } else {
            AutoMessage.println("did not have time to complete event");
        }
    }
}


//package net.ggwpgaming.automessage.event;
//
//import net.ggwpgaming.automessage.AutoMessage;
//import net.ggwpgaming.automessage.config.AMClientConfigs;
//import net.ggwpgaming.automessage.config.AMServerConfigs;
//import net.ggwpgaming.automessage.util.AMIO;
//import net.ggwpgaming.automessage.util.ValuesNode;
//import net.minecraft.network.chat.Component;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//@Mod.EventBusSubscriber(modid = AutoMessage.MOD_ID)
//public class AMEventHandler {
//    public static final String AMClientLogName = "automessage-CLIENT_VIEWCOUNT_LOG.txt";
//    public static final String AMServerLogName = "automessage-SERVER_VIEWCOUNT_LOG.txt";
//    public static final ArrayList<Integer> CLIENT_LIMIT_COUNTER = new ArrayList<>();
//    public static final ArrayList<Integer> SERVER_LIMIT_COUNTER = new ArrayList<>();
//    public static final File clientLogCheck = new File(AMClientLogName);
//    public static final File serverLogCheck = new File(AMServerLogName);
//
//    @SubscribeEvent
//    public static void serverTickEvent(TickEvent.ServerTickEvent event)
//    {
//        if (true) {
//            if (CLIENT_LIMIT_COUNTER.size() == 0 && SERVER_LIMIT_COUNTER.size() == 0)
//            // INITIALIZE OUR RUNNING COUNTERS TO 0
//            {
//                for (int listIndex = 0; listIndex < AMClientConfigs.MESSAGES.get().size(); listIndex++) {
//                    CLIENT_LIMIT_COUNTER.add(0);
//                }
//                for (int listIndex = 0; listIndex < AMServerConfigs.MESSAGES.get().size(); listIndex++) {
//                    SERVER_LIMIT_COUNTER.add(0);
//                }
//            }
//            // get our player list (client may LAN with multiple players)
//            List<ServerPlayer> players = event.getServer().getPlayerList().getPlayers();
//
//            // file check + initialization
//
//
//            if (!clientLogCheck.isFile() && !clientLogCheck.isDirectory())
//            // we couldn't find the client file
//            {
//                // initialize a linked list to hold each player as ValueNode ("String, ArrayList<Integer>")
//                LinkedList<ValuesNode> linkedList = new LinkedList<>();
//
//                // initialize an array list to hold count for each message sent to player
//                ArrayList<Integer> initValues = new ArrayList<>();
//
//                // set initValues counters to 0 before adding to each player
//                if (AMClientConfigs.MESSAGES.get().size() != 0) {
//                    for (int i = 0; i < AMClientConfigs.MESSAGES.get().size(); i++) {
//                        initValues.add(i, 0);
//                    }
//                } else {
//                    // do nothing, no messages to set counters for
//                }
//
//                if (AMClientConfigs.MESSAGES.get().size() != 0 && players.size() != 0) {
//                    // if there are messages in config and players in the world
//                    for (int i = 0; i < players.size(); i++) {
//                        // for each player, add them to our linked list
//                        linkedList.add(new ValuesNode(players.get(i).getStringUUID(), initValues));
//                    }
//                } else {
//                    // do nothing, no players to initialize and no message to add counters for
//                }
//
//                if (linkedList.size() != 0) {
//                    // write our initialized players to our configuration!
//                    AMIO.writeLinkedListToFile(linkedList, AMClientLogName);
//                } else {
//                    // do nothing, no players have been added to our linked list
//                }
//
//                AutoMessage.println(AMClientLogName + " was initialized!");
//            } else if (clientLogCheck.isFile() && !clientLogCheck.isDirectory())
//            // we found our client file!
//            {
//                LinkedList<ValuesNode> retrievedList;
//
//
//                try
//                // are we able to use our method to read the file?
//                {
//                    // retrieve players from our player log as a list of: "UUID, values"
//                    retrievedList = AMIO.readLinkedListFromFile(AMClientLogName);
//                } catch (Exception exception)
//                // something has gone terribly wrong
//                {
//                    AutoMessage.println(AMClientLogName + " IS CORRUPTED! PLEASE DELETE THE FILE AND RESTART 001!");
//
//                    // initialize an empty list for debugging Input/Output to files
//                    retrievedList = new LinkedList<ValuesNode>();
//                }
//
//
//                if (players.size() != 0 && retrievedList.size() != 0)
//                // at least one player exists in the world, and we were able to read our log file
//                {
//                    for (int i = 0; i < players.size(); i++)
//                    // for every player that exists in the world
//                    {
//                        Player player = players.get(i);
//                        if (players.get(i).getStringUUID().equals(retrievedList.get(i).UUID.toString()))
//                        // are we sending to the same player as in our log file? | SIDE NOTE: "==" does not work for strings in java lol
//                        {
//                            for (int messageIndex = 0; messageIndex < AMClientConfigs.MESSAGES.get().size(); messageIndex++)
//                            // for every message
//                            {
//                                if (!AMClientConfigs.MESSAGES.get().get(messageIndex).equals("") && (event.getServer().getTickCount() % (AMClientConfigs.INTERVALS.get().get(messageIndex) * 20) == 0) && event.phase == TickEvent.Phase.START)
//                                // if our message is not empty, and we're hitting an interval to send the message
//                                {
//                                    if
//                                    (
//                                        // SOFT LIMIT COUNTER CHECK
//                                            (CLIENT_LIMIT_COUNTER.get(messageIndex) < AMClientConfigs.LIMITS.get().get(messageIndex) &&
//                                        // HARD LIMIT COUNTER CHECK
//                                        retrievedList.get(i).values.get(messageIndex) < AMClientConfigs.HARD_LIMITS.get().get(messageIndex)) ||
//                                                    (AMClientConfigs.HARD_LIMITS.get().get(messageIndex) == 0)
//                                    ) {
//                                        player.sendSystemMessage(Component.literal(AMClientConfigs.MESSAGES.get().get(messageIndex)));
//                                        CLIENT_LIMIT_COUNTER.set(messageIndex, CLIENT_LIMIT_COUNTER.get(messageIndex) + 1);
//
//                                        // write our incremented count to our log file by gathering players to linked list and overwriting the file
//                                        // AutoMessage.HARD_COUNTER.set(messageIndex, AutoMessage.HARD_COUNTER.get(messageIndex)+1);
//
//                                        // THIS LOGIC WILL ONLY OCCUR WHEN HITTING A MESSAGE INTERVAL, shouldn't be too intense, right?
//
//
//                                        // initialize a linked list to hold each player as ValueNode ("String, ArrayList<Integer>")
//                                        LinkedList<ValuesNode> linkedList = new LinkedList<>();
//
//                                        // initialize an array list to hold count for each message sent to player
//                                        ArrayList<Integer> initValues = new ArrayList<>();
//
//                                        // update initValues counters before adding to each player
//                                        if (AMClientConfigs.MESSAGES.get().size() != 0)
//                                        // if our config has messages
//                                        {
//                                            for (int messageCounter = 0; messageCounter < AMClientConfigs.MESSAGES.get().size(); messageCounter++)
//                                            // iterate over client messages
//                                            {
//                                                if (messageCounter == messageIndex)
//                                                // if the message index we're updating is the same as the message index we sent
//                                                {
//                                                    initValues.add(messageCounter, retrievedList.get(i).values.get(messageCounter) + 1);
//                                                } else {
//                                                    initValues.add(messageCounter, retrievedList.get(i).values.get(messageCounter));
//                                                }
//                                            }
//                                        } else {
//                                            // do nothing, no messages to set counters for
//                                        }
//
//                                        if (AMClientConfigs.MESSAGES.get().size() != 0 && players.size() != 0) {
//                                            // if there are messages in config and players in the world
//                                            for (int playerCounter = 0; playerCounter < players.size(); playerCounter++) {
//                                                // for each player, add them to our linked list
//                                                linkedList.add(new ValuesNode(players.get(playerCounter).getStringUUID(), initValues));
//                                            }
//                                        } else {
//                                            // do nothing, no players to initialize and no message to add counters for
//                                        }
////                                        player.sendSystemMessage(Component.literal("MESSAGE HAS COUNTER: "+linkedList.get(i).values.get(messageIndex).toString()));
//
//                                        if (linkedList.size() != 0) {
//                                            // write our initialized players to our configuration!
//                                            try {
//                                                AMIO.writeLinkedListToFile(linkedList, AMClientLogName);
//                                            } catch(Exception e) {
//                                                AutoMessage.println(e.toString());
//                                                AMIO.writeLinkedListToFile(linkedList, AMClientLogName);
//                                            }
//                                        } else {
//                                            // do nothing, no players have been added to our linked list
//                                        }
//                                        AutoMessage.println(AMClientLogName + " has been updated after message sent: "+AMClientConfigs.MESSAGES.get().get(messageIndex));
//
//
//                                        // THIS LOGIC WILL ONLY OCCUR WHEN HITTING A MESSAGE INTERVAL, shouldn't be too intense, right?
//
//                                    }
//                                } else {
//                                    // do nothing, empty string value in messages
//                                }
//                            }
//
//                        } else
//                        // our list is not ordered properly, or a UUID value was changed in the file but outside the game
//                        {
//                            AutoMessage.println(players.get(i).getStringUUID() + " DOES NOT MATCH " + retrievedList.get(i).UUID + "!!!");
//                            AutoMessage.println(AMClientLogName + " IS CORRUPTED! PLEASE DELETE THE FILE AND RESTART 002!");
//                        }
//                    }
//                } else {
//                    // player may have erased some inner content of file?
//                    if (!(players.size() == 0))
//                    //
//                    {
//                        AutoMessage.println(AMClientLogName + " IS CORRUPTED! PLEASE DELETE THE FILE AND RESTART 003!");
//                    }
//                }
//
//            }
//
//
//            //        if (!serverLogCheck.isFile() && !serverLogCheck.isDirectory())
//            //        // we couldn't find the server file
//            //        {
//            //            AutoMessage.println("SERVER LOG NOT FOUND!");
//            //            // initialize the server log file with current players
//            //        }
//            //        else if (clientLogCheck.isFile() && !clientLogCheck.isDirectory())
//            //        // we found our client file!
//            //        {
//            //            AutoMessage.println("SERVER LOG WAS FOUND!");
//            //            // read the server log file
//            //        }
//
//            //        // Sending messages
//            //        for (Player player : players)
//            //        {}
//        } else {AutoMessage.println("did not have time to complete event");}
//    }
//}
