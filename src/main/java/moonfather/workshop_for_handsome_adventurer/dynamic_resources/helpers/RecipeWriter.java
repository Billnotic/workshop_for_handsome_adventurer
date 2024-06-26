package moonfather.workshop_for_handsome_adventurer.dynamic_resources.helpers;

import moonfather.workshop_for_handsome_adventurer.Constants;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.AssetReader;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.WoodTypeCommonManager;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.WoodTypeLister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.util.List;
import java.util.Map;

public class RecipeWriter
{
    public static void writeFiles(Map<ResourceLocation, String> cache)
    {
        final String SPRUCE = "spruce";
        for (String file: allRecipes)
        {
            String original = AssetReader.getInstance(PackType.SERVER_DATA, Constants.MODID).getText(ResourceLocation.fromNamespaceAndPath(Constants.MODID, file));
            // it never will be null, won't even check
            for (String wood: WoodTypeLister.getWoodIds())
            {
                String newRecipe = original
                        .replace("minecraft:stripped_spruce_log", getStrippedLog(wood))
                        .replace("minecraft:spruce_slab", getSlab(wood))
                        .replace("minecraft:spruce_planks", getPlanks(wood))
                        .replace(SPRUCE, wood);
                cache.put(ResourceLocation.fromNamespaceAndPath(Constants.MODID, file.replace(SPRUCE, wood)), newRecipe);
            }
            if (! conversionRecipes.contains(file))
            {
                for (ResourceLocation duplicate : WoodTypeLister.getDuplicateWoods()) // once again, with feeling
                {
                    String newRecipe = original
                            .replace("minecraft:stripped_spruce", duplicate.getNamespace() + ":stripped_" + duplicate.getPath()) // these will have logs
                            .replace("minecraft:spruce", duplicate.toString())
                            .replace(SPRUCE, duplicate.getPath());
                    cache.put(ResourceLocation.fromNamespaceAndPath(Constants.MODID, file.replace(SPRUCE, duplicate.getPath() + "_" + duplicate.getNamespace())), newRecipe);
                }
            }
        }
    }



    private static String getStrippedLog(String wood)
    {
        String sub = WoodTypeCommonManager.getLogRecipeSubstitute(wood);
        if (sub != null)
        {
            return sub;
        }
        WoodTypeCommonManager.WoodSet specialSet = WoodTypeCommonManager.getWoodSet(wood);
        if (specialSet != null)
        {
            return JOIN.formatted(specialSet.modId(), specialSet.log());
        }
        return TEMPLATE_LOG.formatted(WoodTypeLister.getHostMod(wood), wood);
    }
    private static final String JOIN = "%s:%s";
    private static final String JOIN3 = "%s:%s%s";
    private static final String TEMPLATE_LOG = "%s:stripped_%s_log";



    private static String getSlab(String wood)
    {
        WoodTypeCommonManager.WoodSet specialSet = WoodTypeCommonManager.getWoodSet(wood);
        if (specialSet != null)
        {
            return JOIN.formatted(specialSet.modId(), specialSet.slab());
        }
        return JOIN3.formatted(WoodTypeLister.getHostMod(wood), wood, "_slab");
    }



    static String getPlanks(String wood)
    {
        WoodTypeCommonManager.WoodSet specialSet = WoodTypeCommonManager.getWoodSet(wood);
        if (specialSet != null)
        {
            return JOIN.formatted(specialSet.modId(), specialSet.planks());
        }
        return JOIN3.formatted(WoodTypeLister.getHostMod(wood), wood, "_planks");
    }



    private static final String[] allRecipes = {
            "recipe/book_shelf_double_spruce.json",
            "recipe/book_shelf_minimal_spruce.json",
            "recipe/book_shelf_open_double_spruce.json",
            "recipe/book_shelf_open_minimal_from_double_spruce.json",
            "recipe/book_shelf_open_minimal_spruce.json",
            "recipe/book_shelf_with_lanterns_spruce.json",
            "recipe/potion_shelf_spruce.json",
            "recipe/simple_table_normal_spruce.json",
            "recipe/simple_table_replacement_spruce.json",
            "recipe/tool_rack_double_spruce.json",
            "recipe/tool_rack_framed_spruce.json",
            "recipe/tool_rack_pframed_spruce.json",
            "recipe/tool_rack_single_from_multi_spruce.json",
            "recipe/tool_rack_single_spruce.json",
            "recipe/workstation_placer_spruce.json"
    };
    private static final List<String> conversionRecipes = List.of(
            "recipe/book_shelf_minimal_spruce.json",
            "recipe/book_shelf_open_double_spruce.json",
            "recipe/book_shelf_open_minimal_from_double_spruce.json",
            "recipe/book_shelf_open_minimal_spruce.json",
            "recipe/book_shelf_with_lanterns_spruce.json",
            "recipe/tool_rack_double_spruce.json",
            "recipe/tool_rack_pframed_spruce.json",
            "recipe/tool_rack_single_from_multi_spruce.json"
    );
}
