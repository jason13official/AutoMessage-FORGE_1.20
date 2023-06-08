package net.ggwpgaming.automessage.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;


public class AMClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<String>> MESSAGES;
    public static final ForgeConfigSpec.ConfigValue<List<String>> LINKS;
    public static final ForgeConfigSpec.ConfigValue<List<Integer>> INTERVALS;
    public static final ForgeConfigSpec.ConfigValue<List<Integer>> LIMITS;
    public static final ForgeConfigSpec.ConfigValue<List<Integer>> HARD_LIMITS;

    static {
        BUILDER.push("[-----AutoMessage Client Configuration-----]");

        BUILDER.comment(" Configuration in this file is for client-side only (i.e. \"Thank you for downloading this modpack\" being sent only once for the client, and never again)");

        BUILDER.comment(" !#! SET VALUES TO 0 OR \"\" IF UNUSED / NO LIMIT. !#! ");
        BUILDER.comment(" Color Name      Chat Color Codes");
        BUILDER.comment(" Dark Red        §4");
        BUILDER.comment(" Red             §c");
        BUILDER.comment(" Gold            §6");
        BUILDER.comment(" Yellow          §e");
        BUILDER.comment(" Dark Green      §2");
        BUILDER.comment(" Green           §a");
        BUILDER.comment(" Aqua            §b");
        BUILDER.comment(" Dark Aqua       §3");
        BUILDER.comment(" Dark Blue       §1");
        BUILDER.comment(" Blue            §9");
        BUILDER.comment(" Light Purple    §d");
        BUILDER.comment(" Dark Purple     §5");
        BUILDER.comment(" White           §f");
        BUILDER.comment(" Gray            §7");
        BUILDER.comment(" Dark Gray       §8");
        BUILDER.comment(" Black           §0");
        BUILDER.comment(" Reset           §r");
        BUILDER.comment(" ");
        BUILDER.comment(" MESSAGES = [\"§2Thank you very much for downloading More Decorations§f\", \"§cDon't worry, this message will not appear again.§f\"]");
        MESSAGES = BUILDER.comment().define("MESSAGES", new ArrayList<>());
        BUILDER.comment(" ");
        BUILDER.comment(" LINKS = [\"https://www.google.com\", \"https://www.yahoo.com\"]");
        LINKS = BUILDER.comment().define("LINKS", new ArrayList<>());
        BUILDER.comment(" ");
        BUILDER.comment(" Intervals should be spaced out if possible, to avoid repeat messages from file writing lag ");
        BUILDER.comment(" INTERVALS = [5, 10]");
        INTERVALS = BUILDER.comment().define("INTERVALS", new ArrayList<>());
        BUILDER.comment(" ");
        BUILDER.comment(" LIMITS = [3, 0]");
        LIMITS = BUILDER.comment().define("LIMITS", new ArrayList<>());
        BUILDER.comment(" ");
        BUILDER.comment(" HARD LIMITS = [1, 1]");
        HARD_LIMITS = BUILDER.comment().define("HARD_LIMITS", new ArrayList<>());


        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
/*

BUILDER.comment(" Color Name      Chat Color Codes")
BUILDER.comment(" Dark Red        §4")
BUILDER.comment(" Red             §c")
BUILDER.comment(" Gold            §6")
BUILDER.comment(" Yellow          §e")
BUILDER.comment(" Dark Green      §2")
BUILDER.comment(" Green           §a")
BUILDER.comment(" Aqua            §b")
BUILDER.comment(" Dark Aqua       §3")
BUILDER.comment(" Dark Blue       §1")
BUILDER.comment(" Blue            §9")
BUILDER.comment(" Light Purple    §d")
BUILDER.comment(" Dark Purple     §5")
BUILDER.comment(" White           §f")
BUILDER.comment(" Gray            §7")
BUILDER.comment(" Dark Gray       §8")
BUILDER.comment(" Black           §0")
BUILDER.comment(" Reset           §r")

Color Codes
Color Name	    Chat Color Codes	MOTD Color Codes	Hex Color Codes	Description
Dark Red	    §4	                \u00A74	            #AA0000	        dark_red
Red	            §c	                \u00A7c	            #FF5555	        red
Gold	        §6	                \u00A76	            #FFAA00	        gold
Yellow	        §e	                \u00A7e	            #FFFF55	        yellow
Dark Green	    §2	                \u00A72	            #00AA00	        dark_green
Green	        §a	                \u00A7a	            #55FF55	        green
Aqua	        §b	                \u00A7b	            #55FFFF	        aqua
Dark Aqua	    §3	                \u00A73	            #00AAAA	        dark_aqua
Dark Blue	    §1	                \u00A71	            #0000AA	        dark_blue
Blue	        §9	                \u00A79	            #5555FF	        blue
Light Purple	§d	                \u00A7d	            #FF55FF	        light_purple
Dark Purple	    §5	                \u00A75	            #AA00AA	        dark_purple
White	        §f	                \u00A7f	            #FFFFFF	        white
Gray	        §7	                \u00A77	            #AAAAAA	        gray
Dark Gray	    §8	                \u00A78	            #555555	        dark_gray
Black	        §0	                \u00A70	            #000000	        black
Reset	        §r	                \u00A7r		                        reset


Formatting Codes
Formatting Description	Chat Formatting Codes	MOTD Formatting Codes
Bold	                §l	                    \u00A7l
Italic	                §o	                    \u00A7o
Underline	            §n	                    \u00A7n
Strikethrough	        §m	                    \u00A7m
Obfuscated	            §k	                    \u00A7k

*/