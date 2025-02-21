package net.mehvahdjukaar.supplementaries.reg;

import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.client.ModMaterials;
import net.mehvahdjukaar.supplementaries.client.WallLanternTexturesRegistry;
import net.mehvahdjukaar.supplementaries.common.block.tiles.BookPileBlockTile;
import net.mehvahdjukaar.supplementaries.integration.CompatObjects;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.*;

public class ModTextures {

    //minecraft
    public static final ResourceLocation WHITE_CONCRETE_TEXTURE = new ResourceLocation("minecraft:block/white_concrete_powder");
    public static final ResourceLocation SAND_TEXTURE = new ResourceLocation("minecraft:block/sand");
    public static final ResourceLocation CHAIN_TEXTURE = new ResourceLocation("minecraft:block/chain");
    public static final ResourceLocation HONEY_TEXTURE = new ResourceLocation("minecraft:block/honey_block_side");
    public static final ResourceLocation SLIME_TEXTURE = new ResourceLocation("minecraft:block/slime_block");

    //blocks (to stitch)
    public static final ResourceLocation FISHIES_TEXTURE = Supplementaries.res("blocks/fishies");
    public static final ResourceLocation BELLOWS_TEXTURE = Supplementaries.res("entity/bellows");
    public static final ResourceLocation CLOCK_HAND_TEXTURE = Supplementaries.res("blocks/clock_hand");
    public static final ResourceLocation HOURGLASS_REDSTONE = Supplementaries.res("blocks/hourglass_redstone");
    public static final ResourceLocation HOURGLASS_GLOWSTONE = Supplementaries.res("blocks/hourglass_glowstone");
    public static final ResourceLocation HOURGLASS_SUGAR = Supplementaries.res("blocks/hourglass_sugar");
    public static final ResourceLocation HOURGLASS_BLAZE = Supplementaries.res("blocks/hourglass_blaze");
    public static final ResourceLocation HOURGLASS_GUNPOWDER = Supplementaries.res("blocks/hourglass_gunpowder");
    public static final ResourceLocation BLACKBOARD_GRID = Supplementaries.res("blocks/blackboard_grid");

    public static final ResourceLocation TIMBER_CROSS_BRACE_TEXTURE = Supplementaries.res("blocks/timber_cross_brace");
    public static final ResourceLocation BLACKBOARD_TEXTURE = Supplementaries.res("blocks/blackboard");

    //entities
    public static final ResourceLocation GLOBE_TEXTURE = Supplementaries.res("textures/entity/globes/globe_the_world.png");
    public static final ResourceLocation GLOBE_FLAT_TEXTURE = Supplementaries.res("textures/entity/globes/globe_flat.png");
    public static final ResourceLocation GLOBE_MOON_TEXTURE = Supplementaries.res("textures/entity/globes/globe_moon.png");
    public static final ResourceLocation GLOBE_SUN_TEXTURE = Supplementaries.res("textures/entity/globes/globe_sun.png");
    public static final ResourceLocation GLOBE_SHEARED_TEXTURE = Supplementaries.res("textures/entity/globes/globe_sheared.png");

    public static final ResourceLocation ROPE_ARROW = Supplementaries.res("textures/entity/rope_arrow.png");
    public static final ResourceLocation RED_MERCHANT = Supplementaries.res("textures/entity/misc/red_merchant.png");
    public static final ResourceLocation RED_MERCHANT_CHRISTMAS = Supplementaries.res("textures/entity/misc/christmas_merchant.png");
    public static final ResourceLocation STATUE = Supplementaries.res("textures/entity/statue.png");

    public static final ResourceLocation FIREFLY_TEXTURE = Supplementaries.res("textures/entity/firefly.png");
    public static final ResourceLocation BELL_ROPE_TEXTURE = Supplementaries.res("textures/entity/bell_rope.png");
    public static final ResourceLocation BELL_CHAIN_TEXTURE = Supplementaries.res("textures/entity/bell_chain.png");
    public static final ResourceLocation THICK_GOLEM = Supplementaries.res("textures/entity/misc/iron_golem.png");
    public static final ResourceLocation SEA_PICKLE_RICK = Supplementaries.res("textures/entity/misc/sea_pickle.png");
    public static final ResourceLocation JAR_MAN = Supplementaries.res("textures/entity/misc/jar_man.png");
    public static final ResourceLocation SLIME_ENTITY_OVERLAY = Supplementaries.res("textures/entity/slime_overlay.png");


    //gui
    public static final ResourceLocation SLIME_GUI_OVERLAY = Supplementaries.res("textures/gui/slime_overlay.png");
    public static final ResourceLocation BLACKBOARD_GUI_TEXTURE = Supplementaries.res("textures/gui/blackboard.png");
    public static final ResourceLocation CONFIG_BACKGROUND = Supplementaries.res("textures/gui/config_background.png");
    public static final ResourceLocation NOTICE_BOARD_GUI_TEXTURE = Supplementaries.res("textures/gui/notice_board_gui.png");
    public static final ResourceLocation SACK_GUI_TEXTURE = Supplementaries.res("textures/gui/sack_gui.png");
    public static final ResourceLocation SLOT_TEXTURE = Supplementaries.res("textures/gui/slot.png");
    public static final ResourceLocation PULLEY_BLOCK_GUI_TEXTURE = Supplementaries.res("textures/gui/pulley_block_gui.png");
    public static final ResourceLocation PRESENT_GUI_TEXTURE = Supplementaries.res("textures/gui/present_gui.png");
    public static final ResourceLocation TRAPPED_PRESENT_GUI_TEXTURE = Supplementaries.res("textures/gui/trapped_present_gui.png");
    public static final ResourceLocation RED_MERCHANT_GUI_TEXTURE = Supplementaries.res("textures/gui/red_merchant.png");

    public static final Map<BannerPattern, ResourceLocation> FLAG_TEXTURES = new IdentityHashMap<>();
    public static final Map<BookPileBlockTile.BookColor, ResourceLocation> BOOK_TEXTURES = new EnumMap<>(BookPileBlockTile.BookColor.class);
    public static final ResourceLocation BOOK_ENCHANTED_TEXTURES = Supplementaries.res("entity/books/book_enchanted");
    public static final ResourceLocation BOOK_TOME_TEXTURES = Supplementaries.res("entity/books/book_tome");
    public static final ResourceLocation BOOK_WRITTEN_TEXTURES = Supplementaries.res("entity/books/book_written");
    public static final ResourceLocation BOOK_AND_QUILL_TEXTURES = Supplementaries.res("entity/books/book_and_quill");
    public static final ResourceLocation BOOK_ANTIQUE_TEXTURES = Supplementaries.res("entity/books/book_antique");
    public static final ResourceLocation BUBBLE_BLOCK_TEXTURE = Supplementaries.res("blocks/bubble_block");

    public static final LinkedHashMap<Block, ResourceLocation> SKULL_CANDLES_TEXTURES = new LinkedHashMap<>();

    public static final ResourceLocation ANTIQUABLE_FONT = Supplementaries.res("antiquable");

    static{
        for (BookPileBlockTile.BookColor color : BookPileBlockTile.BookColor.values()) {
            BOOK_TEXTURES.put(color, Supplementaries.res("entity/books/book_" + color.getName()));
        }

        //first key and default one too
        SKULL_CANDLES_TEXTURES.put(Blocks.CANDLE, Supplementaries.res("textures/entity/skull_candles/default.png"));
        for (DyeColor color : DyeColor.values()) {
            Block candle = Registry.BLOCK.get(new ResourceLocation(color.getName() + "_candle"));
            SKULL_CANDLES_TEXTURES.put(candle, Supplementaries.res("textures/entity/skull_candles/" + color.getName() + ".png"));
        }
        //worst case this becomes null
        SKULL_CANDLES_TEXTURES.put(CompatObjects.SOUL_CANDLE.get(), Supplementaries.res("textures/entity/skull_candles/soul.png"));
    }

    public static List<ResourceLocation> getTexturesForBlockAtlas() {
        List<ResourceLocation> blocks = new ArrayList<>(List.of(
                FISHIES_TEXTURE, BELLOWS_TEXTURE, CLOCK_HAND_TEXTURE, HOURGLASS_REDSTONE,
                HOURGLASS_GLOWSTONE, HOURGLASS_SUGAR, HOURGLASS_BLAZE,
                HOURGLASS_GUNPOWDER, BLACKBOARD_GRID, BUBBLE_BLOCK_TEXTURE));

        for (var s : ModMaterials.SIGN_POSTS_MATERIALS.values()) {
            blocks.add(s.texture());
        }
        blocks.addAll(WallLanternTexturesRegistry.SPECIAL_TEXTURES.values());
        return blocks;
    }

    public static List<ResourceLocation> getTexturesForBannerAtlas() {
        List<ResourceLocation> list = new ArrayList<>();
        if (ModTextures.FLAG_TEXTURES.isEmpty()) {
            for (BannerPattern pattern : Registry.BANNER_PATTERN) {

                FLAG_TEXTURES.put(pattern, Supplementaries.res("entity/flags/" +
                        Registry.BANNER_PATTERN.getKey(pattern).toShortLanguageKey().replace(":","/").replace(".","/")));
            }
        }
        try {
            ModTextures.FLAG_TEXTURES.values().stream().filter(r -> !MissingTextureAtlasSprite.getLocation().equals(r))
                    .forEach(list::add);
        } catch (Exception ignored) {
        }
        return list;
    }

    public static List<ResourceLocation> getTexturesForShulkerAtlas() {
        List<ResourceLocation> list = new ArrayList<>();

        list.add(ModTextures.BOOK_ENCHANTED_TEXTURES);
        list.add(ModTextures.BOOK_TOME_TEXTURES);
        list.add(ModTextures.BOOK_WRITTEN_TEXTURES);
        list.add(ModTextures.BOOK_AND_QUILL_TEXTURES);
        list.add(ModTextures.BOOK_ANTIQUE_TEXTURES);
        list.addAll(ModTextures.BOOK_TEXTURES.values());

        return list;
    }

}