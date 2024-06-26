package moonfather.workshop_for_handsome_adventurer.dynamic_resources;

import moonfather.workshop_for_handsome_adventurer.Constants;
import moonfather.workshop_for_handsome_adventurer.dynamic_resources.helpers.*;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class OurServerPack extends BaseResourcePack
{
    private final PackLocationInfo locationInfo;

    public OurServerPack(PackLocationInfo locationInfo)
    {
        super(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        this.locationInfo = locationInfo;
    }



    @Override
    protected void buildResources(Map<ResourceLocation, String> cache)
    {
        CompletableFuture<HolderLookup.Provider> holderProvider = CompletableFuture.supplyAsync(
                VanillaRegistries::createLookup,
                Util.backgroundExecutor()
        );

        RecipeWriter.writeFiles(cache);
        LootTableWriter.writeFiles(cache);
    }



    @Override
    protected boolean isNotOurRecipe(String namespace)
    {
        return ! namespace.equals(Constants.MODID) && ! namespace.equals("tetra_tables");
    }



    @Override
    public PackLocationInfo location() { return this.locationInfo; }

    @Override
    public String packId() { return "Workshop - auto-generated recipes and loot tables"; }
}
