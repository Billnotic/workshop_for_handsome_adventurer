package moonfather.workshop_for_handsome_adventurer.initialization;

import moonfather.workshop_for_handsome_adventurer.dynamic_resources.MissingMappingsHandler;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup
{
	public static void init(FMLCommonSetupEvent event)
	{
		if (ModList.get().isLoaded("craftingtweaks")) {
			try {
				Class.forName("moonfather.workshop_for_handsome_adventurer.integration.CraftingTweaksProvider").getConstructor().newInstance();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
