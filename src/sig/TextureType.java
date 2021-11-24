package sig;

public enum TextureType {
    GRASS_TOP(0,0),
    STONE(1,0),
    DIRT(2,0),
    GRASS2(3,0),
    PLANK(4,0),
    SLAB(5,0),
    SLAB_TOP(6,0),
    BRICK(7,0),
    TNT(8,0),
    TNT_TOP(9,0),
    TNT_BOT(10,0),
    WEB(11,0),
    FLOWER_RED(12,0),
    FLOWER_YELLOW(13,0),
    SOLID_WATER(14,0),
    SAPLING(15,0),
    COBBLESTONE(0,1),
    BEDROCK(1,1),
    SAND(2,1),
    GRAVEL(3,1),
    LOG(4,1),
    LOG_TOP(5,1),
    IRON_BLOCK(6,1),
    GOLD_BLOCK(7,1),
    DIAMOND_BLOCK(8,1),
    EMERALD_BLOCK(9,1),
    FIELD_MUSHROOM_RED(12,1),
    FIELD_MUSHROOM_BROWN(13,1),
    SAPLING2(14,1),
    GOLD_ORE(0, 2),
    IRON_ORE(1, 2),
    COAL_ORE(2, 2),
    BOOKSHELF(3, 2),
    MOSSY_COBBLESTONE(4, 2),
    OBSIDIAN(5, 2),
    GRASS_SIDE_TOP(6, 2),
    FIELD_GRASS(7, 2),
    GRASS(8, 2),
    LIGHT_BLOCK(9, 2),
    UNUSED_1(10,2),
    CRAFTING_TABLE_TOP(11,2),
    FURNACE_FRONT(12,2),
    FURNACE_BACK(13,2),
    DISPENSER_FRONT(14,2),
    UNUSED_2(15,2),
    SPONGE(0, 3),
    GLASS(1, 3),
    DIAMOND_ORE(2, 3),
    REDSTONE_ORE(3, 3),
    LEAVES(4, 3),
    LEAVES2(5, 3),
    STONE_BRICK(6, 3),
    FIELD_DEAD_GRASS(7, 3),
    FIELD_GRASS2(8, 3),
    UNUSED_3(9, 3),
    UNUSED_4(10,3),
    CRAFTING_TABLE(11,3),
    CRAFTING_TABLE_FRONT(12,3),
    FURNACE_FRONT_COOKING(13,3),
    FURNACE(14,3),
    SAPLING3(15,3),
    WOOL(0, 4),
    CAGE(1, 4),
    SNOW(2, 4),
    ICE(3, 4),
    SNOW_DIRT(4, 4),
    CACTUS_TOP(5, 4),
    CACTUS(6, 4),
    CACTUS_INSIDE_TOP(7, 4),
    CLAY(8, 4),
    SUGARCANE(9, 4),
    JUKEBOX(10,4),
    JUKEBOX_TOP(11,4),
    LILYPAD(12,4),
    PODZOL(13,4),
    PODZOL_TOP(14,4),
    SAPLING4(15,4),
    TORCH(0, 5),
    DOOR(1, 5,1,2),
    IRON_DOOR(2, 5,1,2),
    LADDER(3, 5),
    TRAPDOOR(4, 5),
    IRON_BARS(5, 5),
    WET_SOIL(6, 5),
    DRY_SOIL(7, 5),
    WHEAT_0(8, 5),
    WHEAT_1(9, 5),
    WHEAT_2(10,5),
    WHEAT_3(11,5),
    WHEAT_4(12,5),
    WHEAT_5(13,5),
    WHEAT_6(14,5),
    WHEAT(15,5),
    LEVER(0, 6),
    UNUSED_5(1, 6),
    UNUSED_6(2, 6),
    REDSTONE_TORCH(3, 6),
    MOSSY_STONE_BRICK(4, 6),
    CRACKED_STONE_BRICK(5, 6),
    PUMPKIN_TOP(6, 6),
    NETHERRACK(7, 6),
    SOUL_SAND(8, 6),
    GLOWSTONE(9, 6),
    STICKY_PISTON_HEAD(10,6),
    PISTON_HEAD(11,6),
    PISTON(12,6),
    DROPPER_SIDE(13,6),
    DROPPER_FRONT(14,6),
    MELON_STEM(15,6),
    TRACK_TURN(0, 7),
    BLACK_WOOL(1, 7),
    GRAY_WOOL(2, 7),
    REDSTONE_TORCH_OFF(3, 7),
    SPRUCE_LOG(4, 7),
    BIRCH_LOG(5, 7),
    PUMPKIN(6, 7),
    PUMPKIN_FRONT(7, 7),
    JACKOLANTERN(8, 7),
    CAKE_TOP(9, 7),
    CAKE(10,7),
    CAKE_EATEN(11,7),
    CAKE_BOTTOM(12,7),
    RED_MUSHROOM(13,7),
    BROWN_MUSHROOM(14,7),
    MELON_STEM_CONNECTED(15,7),
    TRACK(0, 8),
    RED_WOOL(1, 8),
    PINK_WOOL(2, 8),
    REDSTONE_REPEATER_OFF(3, 8),
    LEAVES3(4, 8),
    LEAVES4(5, 8),
    BED_TOP(6, 8,2,1),
    UNUSED_7(7, 8),
    MELON(8, 8),
    MELON_TOP(9, 8),
    CAULDRON_TOP(10,8),
    CAULDRON_INSIDE(11,8),
    CAKE_ICON(12,8),
    MUSHROOM_STALK(13,8),
    MUSHROOM_INSIDE(14,8),
    VINES(15,8),
    LAPIS_LAZULI_BLOCK(0, 9),
    GREEN_WOOL(1, 9),
    LIME_WOOL(2, 9),
    REDSTONE_REPEATER_ON(3, 9),
    GLASS_PANE_SIDE(4, 9),
    BED_FRONT(5, 9),
    BED_SIDE(6, 9,2,1),
    UNUSED_8(7, 9),
    BED_BACK(8, 9),
    JUNGLE_LOG(9, 9),
    CAULDRON(10,9),
    CAULDRON_BOTTOM(11,9),
    BREWING_STAND_BASE(12,9),
    BREWING_STAND(13,9),
    END_EYE_PORTAL(14,9),
    END_EYE_PORTAL_SIDE(15,9),
    LAPIS_LAZULI_ORE(0, 10),
    BROWN_WOOL(1, 10),
    YELLOW_WOOL(2, 10),
    POWERED_RAIL_OFF(3, 10),
    REDSTONE_4(4, 10),
    REDSTONE_2(5, 10),
    ENCHANTMENT_TABLE_TOP(6, 10),
    DARK_PURPLE_BLOCK(7, 10),
    COCOA_BEAN(8, 10),
    COCOA_BEAN_1(9, 10),
    COCOA_BEAN_0(10,10),
    EMERALD_ORE(11,10),
    TRIPWIRE_HOOK(12,10),
    TRIPWIRE(13,10),
    EYE_OF_ENDER(14,10),
    END_STONE(15,10),
    SANDSTONE_TOP(0, 11),
    BLUE_WOOL(1, 11),
    LIGHT_BLUE_WOOL(2, 11),
    POWERED_RAIL(3, 11),
    UNUSED_9(4, 11),
    UNUSED_10(5, 11),
    ENCHANTMENT_TABLE(6, 11),
    ENCHANTMENT_TABLE_BOTTOM(7, 11),
    COMMAND_BLOCK(8, 11),
    COMMAND_BLOCK_TOP(9, 11),
    MINI_DOOR(10,11),
    UNUSED_11(11,11),
    UNUSED_12(12,11),
    UNUSED_13(13,11),
    UNUSED_14(14,11),
    UNUSED_15(15,11),
    SANDSTONE(0, 12),
    PURPLE_WOOL(1, 12),
    MAGENTA_WOOL(2, 12),
    DETECTOR_RAIL(3, 12),
    JUNGLE_LEAVES(4, 12),
    JUNGLE_LEAVES2(5, 12),
    SPRUCE_PLANK(6, 12),
    JUNGLE_PLANK(7, 12),
    CARROT_0(8, 12),
    CARROT_1(9, 12),
    CARROT_2(10,12),
    CARROT(11,12),
    POTATO(12,12),
    WATER(13,12),
    UNUSED_16(14,12),
    UNUSED_17(15,12),
    SANDSTONE_BOTTOM(0, 13),
    CYAN_WOOL(1, 13),
    ORANGE_WOOL(2, 13),
    REDSTONE_LAMP_OFF(3, 13),
    REDSTONE_LAMP(4, 13),
    CHISELED_STONE(5, 13),
    BIRCH_PLANK(6, 13),
    DARK_STONE(7, 13),
    CRACKED_PARTIAL_STONE(8, 13),
    UNUSED_18(9, 13),
    UNUSED_19(10,13),
    UNUSED_20(11,13),
    UNUSED_21(12,13),
    UNUSED_22(13,13),
    UNUSED_23(14,13),
    UNUSED_24(15,13),
    NETHER_BRICK(0, 14),
    LIGHT_GRAY_WOOL(1, 14),
    NETHER_WART0(2, 14),
    NETHER_WART1(3, 14),
    NETHER_WART(4, 14),
    CHISELED_SANDSTONE_DECORATED(5, 14),
    CHISELED_SANDSTONE(6, 14),
    DARK_PARTIALSTONE(7, 14),
    CRAKED_PARTIAL_STONE2(8, 14),
    UNUSED_25(9, 14),
    UNUSED_26(10,14),
    UNUSED_27(11,14),
    UNUSED_28(12,14),
    UNUSED_29(13,14),
    UNUSED_30(14,14),
    LAVA(15,14),
    BREAK_0(0, 15),
    BREAK_1(1, 15),
    BREAK_2(2, 15),
    BREAK_3(3, 15),
    BREAK_4(4, 15),
    BREAK_5(5, 15),
    BREAK_6(6, 15),
    BREAK_7(7, 15),
    BREAK_8(8, 15),
    BREAK_9(9, 15),
    UNUSED_31(10,15),
    UNUSED_32(11,15),
    UNUSED_33(12,15),
    UNUSED_34(13,15),
    UNUSED_35(14,15),
    UNUSED_36(15,15),
    ;
    int texX,texY,texWidth,texHeight;

    TextureType(int texX,int texY) {
        this(texX,texY,1,1);
    }
    TextureType(int texX,int texY,int texWidth,int texHeight) {
        this.texX=texX;
        this.texY=texY;
        this.texWidth=texWidth;
        this.texHeight=texHeight;
    }
}