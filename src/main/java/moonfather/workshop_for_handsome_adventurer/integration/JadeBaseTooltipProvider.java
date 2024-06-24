package moonfather.workshop_for_handsome_adventurer.integration;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import snownee.jade.api.ITooltip;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class JadeBaseTooltipProvider extends WailaBaseProvider
{
    protected void appendTooltipInternal(ITooltip tooltip, ItemStack item)
    {
        List<IElement> list = new ArrayList<>(3);
        list.add(IElementHelper.get().item(item));
        list.add(IElementHelper.get().spacer(4, 12));
        list.add(IElementHelper.get().text(item.getHoverName()));
        tooltip.add(list);
        if (this.optionsCacheTime <= 0)
        {
            this.optionEnchantments = getOption().get();
            this.optionsCacheTime = 35;
        }
        this.optionsCacheTime -= 1;
        if (this.optionEnchantments)
        {
            List<Component> enchantments = this.getEnchantmentParts(item);
            if (enchantments != null)
            {
                for (int i = 0; i < enchantments.size(); i += 1)
                {
                    list = new ArrayList<>();
                    list.add(IElementHelper.get().spacer(4, 12));
                    list.add(IElementHelper.get().text(enchantments.get(i)));
                    tooltip.add(list);
                }
            }
        }
    }

    protected abstract ModConfigSpec.ConfigValue<Boolean> getOption();

    private int optionsCacheTime = 0;
    private boolean optionEnchantments = false;
}
