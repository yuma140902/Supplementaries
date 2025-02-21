package net.mehvahdjukaar.supplementaries.common.capabilities.mob_container;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.api.ICatchableMob;
import net.mehvahdjukaar.supplementaries.common.items.AbstractMobContainerItem;
import net.mehvahdjukaar.supplementaries.common.network.ClientBoundSyncCapturedMobsPacket;
import net.mehvahdjukaar.supplementaries.common.network.ClientBoundSyncSongsPacket;
import net.mehvahdjukaar.supplementaries.common.network.NetworkHandler;
import net.mehvahdjukaar.supplementaries.common.world.songs.SongsManager;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CapturedMobHandler extends SimpleJsonResourceReloadListener {

    public static final List<String> COMMAND_MOBS = new ArrayList<>();

    private static final Map<EntityType<?>, DataDefinedCatchableMob> CUSTOM_MOB_PROPERTIES = new IdentityHashMap<>();
    private static DataDefinedCatchableMob MODDED_FISH_PROPERTIES;

    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static final CapturedMobHandler RELOAD_INSTANCE = new CapturedMobHandler();

    private CapturedMobHandler() {
        super(GSON, "catchable_mobs_properties");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        CUSTOM_MOB_PROPERTIES.clear();
        var list = new ArrayList<DataDefinedCatchableMob>();
        jsons.forEach((key, json) -> {
            var v = DataDefinedCatchableMob.CODEC.parse(JsonOps.INSTANCE, json);
            var data = v.getOrThrow(false, e -> Supplementaries.LOGGER.error("failed to parse structure map trade: {}", e));
            if (key.getPath().equals("generic_fish")) {
                MODDED_FISH_PROPERTIES = data;
            } else {
                list.add(data);
            }
        });
        for (var c : list) {
            for (var o : c.getOwners()) {
                Registry.ENTITY_TYPE.getOptional(o).ifPresent(e -> CUSTOM_MOB_PROPERTIES.put(e, c));
            }
        }
    }


    public static void sendDataToClient(ServerPlayer player) {
        Set<DataDefinedCatchableMob> set = new HashSet<>(CUSTOM_MOB_PROPERTIES.values());
        NetworkHandler.CHANNEL.sendToClientPlayer(player,
                new ClientBoundSyncCapturedMobsPacket(set,MODDED_FISH_PROPERTIES));
    }

    public static void acceptClientData(Set<DataDefinedCatchableMob> list, @Nullable DataDefinedCatchableMob defaultFish) {
        if(defaultFish != null){
            MODDED_FISH_PROPERTIES = defaultFish;
        }
        CUSTOM_MOB_PROPERTIES.clear();
        for (var c : list) {
            for (var o : c.getOwners()) {
                Registry.ENTITY_TYPE.getOptional(o).ifPresent(e -> CUSTOM_MOB_PROPERTIES.put(e, c));
            }
        }
    }

    public static ICatchableMob getDataCap(EntityType<?> type, boolean isFish) {
        var c = CUSTOM_MOB_PROPERTIES.get(type);
        if (c == null && isFish) return MODDED_FISH_PROPERTIES;
        return c;
    }

    public static ICatchableMob getCatchableMobCapOrDefault(Entity entity) {
        if (entity instanceof ICatchableMob cap) return cap;
        var forgeCap = getForgeCap(entity);
        if (forgeCap != null) return forgeCap;
        var prop = getDataCap(entity.getType(), BucketHelper.isModdedFish(entity));
        if (prop != null) return prop;
        return ICatchableMob.DEFAULT;
    }

    @ExpectPlatform
    private static ICatchableMob getForgeCap(Entity entity) {
        throw new AssertionError();
    }



    //debug
    private static void saveFile(DataDefinedCatchableMob data) {
        File folder = PlatformHelper.getGamePath().resolve("test_cap").toFile();

        if (!folder.exists()) {
            folder.mkdir();
        }
        try {

            File exportPath = new File(folder, data.getOwners().get(0).toString().replace(":","_")+ ".json");
            try (FileWriter writer = new FileWriter(exportPath)) {
                var j = DataDefinedCatchableMob.CODEC.encodeStart(JsonOps.INSTANCE, data);
                JsonWriter w = new JsonWriter(writer);

                CapturedMobHandler.GSON.toJson(sortJson(j.result().get().getAsJsonObject()), writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject sortJson(JsonObject jsonObject) {
        try {
            Map<String, JsonElement> joToMap = new TreeMap<>();
            jsonObject.entrySet().forEach(e -> {
                var j = e.getValue();
                if (j instanceof JsonObject jo) j = sortJson(jo);
                joToMap.put(e.getKey(), j);
            });
            JsonObject sortedJSON = new JsonObject();
            joToMap.forEach(sortedJSON::add);
            return sortedJSON;
        } catch (Exception ignored) {
        }
        return jsonObject;
    }
}
