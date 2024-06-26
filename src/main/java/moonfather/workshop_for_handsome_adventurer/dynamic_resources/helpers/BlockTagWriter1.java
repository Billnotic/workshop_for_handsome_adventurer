package moonfather.workshop_for_handsome_adventurer.dynamic_resources.helpers;

import moonfather.workshop_for_handsome_adventurer.dynamic_resources.WoodTypeLister;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class BlockTagWriter1
{
    public static void writeFiles(Map<ResourceLocation, String> cache)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("  \"replace\": false,\n");
        builder.append("  \"values\": [\n");

        String line = "\"workshop_for_handsome_adventurer:simple_table_";
        boolean first = true;
        for (String wood: WoodTypeLister.getWoodIds())
        {
            if (! first)
            {
                builder.append(",\n");
            }
            else
            {
                first = false;
            }
            builder.append(line).append(wood).append('"');
        }
        builder.append("\n  ]\n}\n");
        cache.put(ResourceLocation.fromNamespaceAndPath("c", "tags/block/workbench.json"), builder.toString());
        cache.put(ResourceLocation.fromNamespaceAndPath("c", "tags/item/workbench.json"), builder.toString());
        cache.put(ResourceLocation.fromNamespaceAndPath("c", "tags/item/player_workstations/crafting_tables.json"), builder.toString());
    }
}
