package moonfather.workshop_for_handsome_adventurer;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig
{
    private static final boolean defaultSimpleTableReplacesVanillaTable = false;
    private static final int defaultSimpleTableNumberOfSlots = 1;
    private static final int defaultDualTableNumberOfSlots = 2;
    private static final String defaultAccessCustomizationItem = "minecraft:name_tag";
    private static final int defaultSlotRoomMultiplier = 6;
    private static final int defaultSlotRoomMaximum = 16;
    private static final boolean defaultOffhandInteractsWithToolRack = true;
    ///---------------------------------------------
    private static final ModConfigSpec.Builder BUILDER;
    public static final ModConfigSpec.ConfigValue<Integer> SimpleTableNumberOfSlots;
    public static final ModConfigSpec.ConfigValue<Integer> DualTableNumberOfSlots;
    public static final ModConfigSpec.ConfigValue<String> AccessCustomizationItem;
    public static final ModConfigSpec.BooleanValue SimpleTableReplacesVanillaTable;
    public static final ModConfigSpec.ConfigValue<Integer> SlotRoomMultiplier;
    public static final ModConfigSpec.ConfigValue<Integer> SlotRoomMaximum;
    public static final ModConfigSpec.BooleanValue OffhandInteractsWithToolRack;
    public static final ModConfigSpec.BooleanValue OffhandInteractsWithPotionShelf;
    static final ModConfigSpec SPEC;



    static //constructor
    {
        BUILDER = new ModConfigSpec.Builder();
        BUILDER.push("Tables");
            SimpleTableNumberOfSlots = BUILDER
                .comment("Customization slots allow you to enable the table to hold items or access nearby chests.")
                .defineInRange("Simple table - number of customization slots", defaultSimpleTableNumberOfSlots, 0, 2);
            DualTableNumberOfSlots = BUILDER
                .comment("Customization slots allow you to enable the table to hold items or access nearby chests, or have lanterns as part of the table.")
                .defineInRange("Dual table - number of customization slots", defaultDualTableNumberOfSlots, 0, 4);
            AccessCustomizationItem = BUILDER
                .comment("Here you can set which item allows you to access nearby inventories from a workbench window. Frankly there is no correct item here - customizations aren't upgrades, they are options - like checkboxes. This may delay availability of this functionality - you can put a minecraft:amethyst_shard if you want it to be available later in the game or a good old minecraft:torch if you want it available right away.").worldRestart()
                .define("Customization - item for inventory access", defaultAccessCustomizationItem);
            SimpleTableReplacesVanillaTable = BUILDER
                .comment("If set to false (default), simple crafting tables are craftable after you have vanilla crafting table. If set to true (not much reason not to be), this mod's crafting tables are craftable from four planks in 2x2 configuration.").worldRestart()
                .define("Simple table replaces vanilla table", defaultSimpleTableReplacesVanillaTable);
        BUILDER.pop();
        BUILDER.push("Potion shelves");
            SlotRoomMultiplier = BUILDER
                .comment("This is a multiplier for the number of bottles that fit in a single potion shelf slot, multiplying normal stack maximum. Default 6 (unrelated to six slots) means each slot can fit 6 non-stackable potions or for example 24 potions that stack up to 4 in players inventory.")
                .defineInRange("Room in one potion shelf slot (multiplier)", defaultSlotRoomMultiplier, 1, 12);
            SlotRoomMaximum = BUILDER
                .comment("This is a total maximum of number of bottles that fit in a single potion shelf slot. Whatever above math gives you will be clipped to fit this value.")
                .defineInRange("Maximum number of potions that fit into one shelf slot", defaultSlotRoomMaximum, 1, 64);
        OffhandInteractsWithPotionShelf = BUILDER
                .comment("If set to false, you need to move a bottle from off-hand to main hand (F) before putting it onto a shelf, it's simpler but needs extra actions. If you set this to true you can put bottles from off-hand to potion shelf directly and you can take items directly; quicker but there might be possible unintended interactions with the shelf.")
                .define("Offhand interacts with tool rack directly", defaultOffhandInteractsWithToolRack);
        BUILDER.pop();
        BUILDER.push("Tool racks");
            OffhandInteractsWithToolRack = BUILDER
                .comment("If set to false, you need to move a tool from off-hand to main hand (F) before putting it onto a toolrack, it's simpler but needs extra actions. If you set this to true you can put tools from off-hand to toolrack directly and you can take items directly; quicker but there might be possible unintended interactions with the toolrack.")
                .define("Offhand interacts with tool rack directly", defaultOffhandInteractsWithToolRack);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
