package moonfather.workshop_for_handsome_adventurer.integration;


import moonfather.workshop_for_handsome_adventurer.ClientConfig;
import moonfather.workshop_for_handsome_adventurer.Constants;
import moonfather.workshop_for_handsome_adventurer.block_entities.BookShelfBlockEntity;
import moonfather.workshop_for_handsome_adventurer.block_entities.PotionShelfBlockEntity;
import moonfather.workshop_for_handsome_adventurer.block_entities.ToolRackBlockEntity;
import moonfather.workshop_for_handsome_adventurer.blocks.ToolRack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ModConfigSpec;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;


public class JadeToolTooltipProvider extends JadeBaseTooltipProvider implements IBlockComponentProvider
{
    private static final JadeToolTooltipProvider instance = new JadeToolTooltipProvider();
    public static JadeToolTooltipProvider getInstance() { return instance; }


    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config)
    {
        if (accessor.getBlockEntity() instanceof PotionShelfBlockEntity || accessor.getBlockEntity() instanceof BookShelfBlockEntity)
        {
            return;
        }
        if (accessor.getBlockEntity() instanceof ToolRackBlockEntity rack)
        {
            int slot = ToolRack.getToolRackSlot((ToolRack) accessor.getBlock(), accessor.getHitResult());
            if (slot >= 0 && ! rack.GetItem(slot).isEmpty())
            {
                this.appendTooltipInternal(tooltip, rack.GetItem(slot));
            }
        }
        else if (accessor.getBlock() instanceof ToolRack rackBlock)
        {
            if (accessor.getLevel().getBlockEntity(accessor.getPosition().above()) instanceof ToolRackBlockEntity rack)
            {
                int slot = ToolRack.getToolRackSlot((ToolRack) accessor.getBlock(), new BlockHitResult(accessor.getHitResult().getLocation(), accessor.getHitResult().getDirection(), accessor.getHitResult().getBlockPos().above(), false));
                if (slot >= 0 && ! rack.GetItem(slot).isEmpty())
                {
                    this.appendTooltipInternal(tooltip, rack.GetItem(slot));
                }
            }
        }
    }

    @Override
    protected ModConfigSpec.ConfigValue<Boolean> getOption()
    {
        return ClientConfig.DetailedWailaInfoForEnchantedTools;
    }



    @Override
    public ResourceLocation getUid()
    {
        return this.pluginId;
    }
    private final ResourceLocation pluginId = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "jade_plugin3");
}