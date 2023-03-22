package moonfather.workshop_for_handsome_adventurer;

import com.mojang.logging.LogUtils;
import moonfather.workshop_for_handsome_adventurer.initialization.ClientSetup;
import moonfather.workshop_for_handsome_adventurer.initialization.CommonSetup;
import moonfather.workshop_for_handsome_adventurer.initialization.Registration;
import moonfather.workshop_for_handsome_adventurer.integration.TOPRegistration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(Constants.MODID)
public class ModWorkshop
{
    //NOW                        LATER refactoring events.

    //--all-- --MUST--
    //...
    //--all-- --NTH--
    //...

    //--rack-- --MUST--
    //potion shelf shift for all
    //...
    //--rack-- --NTH--
    //.....
    //check offhand how feels -> not well

    //--simpletable--  --MUST--
    //...
    //--simpletable--  --NTH--
    //.....


    //--dualtable--  --MUST--
    //...
    //--dualtable--  --NTH--
    //...

    //--before--pub--
    //todo: printLn, CR in toml, ver
    //--known-- issues
    //integration: WTHIT is entirely unsupported, Jade and TOP are fine. it's not my fault, i wanted to add support but i can not. blame wthit author while switching to an alternative.
    //vanilla behavior: when you have two-block rack and you're looking so that the top half isn't visible, items disappear. i think it's not that much of a problem.
    //integration: crescent hammer (thermal) won't go onto the rack.  it's fine.
    //--later--
    //last thing in 1.0 rename chests at the cost of 1 XPL
    //after 1.0: render items on top of tables
    //after 1.0: crates from create, maybe in 1.2
    //after 1.0: todo list similar to one in BiblioCraft
    //after 1.0: bookshelf like the one in BiblioCraft; no gui; oh and apparently exactly that is coming in 1.20
    //after 1.0: more than 8 chests
    //maybe after 1.1: storage drawer support. maybe.
    //maybe after 1.1: sword rack? maybe. decorative, single sword, horizontal, no gui
    //maybe after 1.1: chest markers (similar to item frames); maybe? no gui, pull items from chest
    //maybe after 1.1: nine extra storage slots in dual table
    //probably not: weapon rack (vertical weapons),  sword and shield on wall...


    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModWorkshop()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OptionsHolder.COMMON_SPEC);
        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::Initialize));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::RegisterRenderers));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::StitchTextures));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        String[] woodTypes = {"oak", "spruce", "jungle", "birch", "dark_oak"};
        for (String woodType: woodTypes)
        {
            InterModComms.sendTo("carryon", "blacklistBlock", () -> Constants.MODID + ":tool_rack_double_" + woodType);
            InterModComms.sendTo("carryon", "blacklistBlock", () -> Constants.MODID + ":tool_rack_framed_" + woodType);
            InterModComms.sendTo("carryon", "blacklistBlock", () -> Constants.MODID + ":tool_rack_pframed_" + woodType);
            InterModComms.sendTo("carryon", "blacklistBlock", () -> Constants.MODID + ":dual_table_bottom_left_" + woodType);
            InterModComms.sendTo("carryon", "blacklistBlock", () -> Constants.MODID + ":dual_table_bottom_right_" + woodType);
            InterModComms.sendTo("carryon", "blacklistBlock", () -> Constants.MODID + ":dual_table_top_left_" + woodType);
            InterModComms.sendTo("carryon", "blacklistBlock", () -> Constants.MODID + ":dual_table_top_right_" + woodType);
        }
        /////
        if (ModList.get().isLoaded("theoneprobe"))
        {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPRegistration::instance);
        }
    } 
}
