package moonfather.workshop_for_handsome_adventurer.dynamic_resources;

import moonfather.workshop_for_handsome_adventurer.Constants;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.config.DynamicAssetCommonConfig;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.helpers.BlockTagWriter2;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLConfig;
import net.neoforged.fml.loading.FMLEnvironment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MissingMappingsHandler
{
    // handles the missing mappings
    public static void prepareMappings()
    {
        for (String earlier : earlierTypes)
        {
            if (! WoodTypeLister.getWoodIds().contains(earlier))
            {
                for (String prefix : BlockTagWriter2.files)
                {
                    ResourceLocation missing = ResourceLocation.fromNamespaceAndPath(Constants.MODID, prefix + earlier);
                    ResourceLocation fallback = ResourceLocation.fromNamespaceAndPath(Constants.MODID, prefix + "oak");
                    BuiltInRegistries.BLOCK.addAlias(missing, fallback);
                }
            }
        }
    }



    // reads all previously existing wood types
    public static void read()
    {
        String raw = "";
        try
        {
            Path path = Path.of("config", "workshop", "mappings.dat");
            raw = Files.readString(path);
        }
        catch (IOException ignored) {       }
        Arrays.stream(raw.split(",\\s*")).forEach(earlierTypes::add);
        earlierTypes.remove("");
    }
    private static final HashSet<String> earlierTypes = new HashSet<>();



    public static void storeForNextTime()
    {
        WoodTypeLister.getWoodIds().forEach(earlierTypes::add);
        String raw = String.join(", ", earlierTypes);
        try
        {
            Path path = Path.of("config", "workshop", "mappings.dat");;
            Files.writeString(path, raw, StandardOpenOption.CREATE_NEW);
        }
        catch (IOException ignored) {       }
    }
}
