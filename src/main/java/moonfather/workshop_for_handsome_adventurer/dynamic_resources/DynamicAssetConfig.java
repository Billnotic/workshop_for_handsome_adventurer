package moonfather.workshop_for_handsome_adventurer.dynamic_resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moonfather.workshop_for_handsome_adventurer.Constants;
import net.minecraftforge.fml.loading.FMLConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class DynamicAssetConfig
{
    public static boolean separateCreativeTab()
    {
        return SecondCreativeTab.items_table1.size() >= getClient().minimum_number_of_sets_for_separate_creative_tab;
    }

    public static boolean masterLeverOn()
    {
        return getCommon().generate_blocks_for_mod_added_woods;
    }

    ///////////////////

    private static InstantConfigServer getCommon()
    {
        if (common == null)
        {
            Path configPath = Path.of(FMLConfig.defaultConfigPath(), "../config", Constants.MODID + "-special-common.json");
            boolean readingFailed = false;
            if (configPath.toFile().exists())
            {
                try
                {
                    Gson gson = new Gson();
                    common = gson.fromJson(Files.readString(configPath), InstantConfigServer.class);
                }
                catch (IOException ignored)
                {
                }
            }
            if (common == null)
            {
                common = new InstantConfigServer();
            }
            if (readingFailed || ! configPath.toFile().exists())
            {
                try
                {
                    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                    String text = gson.toJson(common, InstantConfigServer.class);
                    Files.writeString(configPath, text, StandardCharsets.US_ASCII, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                }
                catch (IOException e)
                {
                    readingFailed = true;
                }
            }
        }
        return common;
    }
    private static InstantConfigServer common = null;

    private static InstantConfigClient getClient()
    {
        if (client == null)
        {
            Path configPath = Path.of(FMLConfig.defaultConfigPath(), "..", "config", Constants.MODID + "-special-client.json");
            boolean readingFailed = false;
            if (configPath.toFile().exists())
            {
                try
                {
                    Gson gson = new Gson();
                    client = gson.fromJson(Files.readString(configPath), InstantConfigClient.class);
                }
                catch (IOException e)
                {
                    readingFailed = true;
                }
            }
            if (client == null)
            {
                client = new InstantConfigClient();
            }
            if (readingFailed || ! configPath.toFile().exists())
            {
                try
                {
                    Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                    String text = gson.toJson(client, InstantConfigClient.class);
                    Files.writeString(configPath, text, StandardCharsets.US_ASCII, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                }
                catch (IOException ignored)
                {
                }
            }
        }
        return client;
    }
    private static InstantConfigClient client = null;

    //////////////////////////////////////////////

    public static String getLogRecipeSubstitution(String wood)
    {
        return getFromSplitConfig(getCommon().stripped_log_substitution_list_for_recipes, subRecipeList, wood);
    }
    public static String getLogTexSubstitution(String wood)
    {
        return getFromSplitConfig(getClient().stripped_log_substitution_list_for_textures, subTextureList, wood);
    }
    public static String getPlankPath(String wood)
    {
        return getFromSplitConfig(getClient().texture_template1_list, plankPathList, wood);
    }
    public static String getLogPath(String wood)
    {
        return getFromSplitConfig(getClient().texture_template2_list, logPathList, wood);
    }
    public static boolean isUsingDarkerWorkstation(String wood)
    {
        return getClient().use_darker_workstation_model.contains(wood);
    }
    private static final Map<String, String> plankPathList = new HashMap<>();
    private static final Map<String, String> subRecipeList = new HashMap<>();
    private static final Map<String, String> subTextureList = new HashMap<>();
    private static final Map<String, String> logPathList = new HashMap<>();

    private static String getFromSplitConfig(String input, Map<String, String> list, String wood)
    {
        if (list.isEmpty())
        {
            String[] temp1 = input.split(", *");
            for (String s : temp1)
            {
                String[] temp2 = s.split(" *= *");
                if (temp2.length == 2)
                {
                    list.put(temp2[0], temp2[1]);
                }
            }
        }
        return list.getOrDefault(wood, null);
    }

    ////////////////////////////

    private static class InstantConfigServer // because the other kind is unavailable early
    {
        public String generate_blocks_comment1 = "Unicorn-magic-powered system that automatically adds tables/racks/shelves for all wood types in the game (yes, modded ones too, that is the point of this system). If you turn this off (does not work? please report!), workshop blocks will only be added in vanilla wood types.";
        public String generate_blocks_comment2 = "Option requires game restart. Will synchronize it in future versions, for now users need to be careful.";
        public boolean generate_blocks_for_mod_added_woods = true;
        //public String stripped_log_substitution_list_for_recipes = "bamboo=minecraft:stripped_bamboo_block, treated_wood=minecraft:polished_blackstone,  embur=byg:stripped_embur_pedu,  sythian=byg:stripped_sythian_stem, bulbis=minecraft:smooth_stone";

        public String stripped_log_substitution_comment = "For wood types that do not have stripped logs, you can specify table top block here. If you do not, we are skipping that wood type.";
        public String stripped_log_substitution_list_for_recipes = "bamboo=minecraft:stripped_bamboo_block, treated_wood=minecraft:polished_blackstone,  embur=byg:stripped_embur_pedu,  sythian=byg:stripped_sythian_stem, bulbis=byg:stripped_bulbis_stem";

    }

    private static class InstantConfigClient // because the other kind is unavailable early
    {
        public String minimum_number_of_sets_comment = "How many wood sets are needed for this mod to use a second tab in creative mode for dynamically created blocks. Set to a high number to disable second tab and shove everything into first.";
        public int minimum_number_of_sets_for_separate_creative_tab = 4;

        public String stripped_log_substitution_comment = "For wood types that do not have stripped logs, you can specify table top block here. If you do not, we are skipping that wood type.";
        public String stripped_log_substitution_list_for_textures = "embur=embur, sythian=sythian, bamboo=stripped_bamboo_block, bulbis=bulbis";

        public String texture_template1_comment = "Tells us where to find plank textures, in case mod uses subdirectories (like byg) or different file names. Second %s below is the wood type. Separate using commas.";
        public String texture_template1_list = "byg=%s:block/%s/planks";
        public String texture_template2_comment = "Tells us where to find stripped log textures, in case mod uses subdirectories (like byg) or different file names. Second %s below is the wood type. Separate using commas.";
        public String texture_template2_list = "byg=%s:block/%s/stripped_log";

        public String use_darker_workstation_comment = "Slightly different model. Do not worry about this. Or just list dark woods here.";
        public String use_darker_workstation_model = "embur,hellbark,bulbis,cika,lament";
    }
}
