package net.mehvahdjukaar.supplementaries.common.block.tiles;

import net.mehvahdjukaar.moonlight.api.block.IOwnerProtected;
import net.mehvahdjukaar.supplementaries.reg.ModTextures;
import net.mehvahdjukaar.supplementaries.common.block.blocks.SpeakerBlock;
import net.mehvahdjukaar.supplementaries.common.network.ClientBoundPlaySpeakerMessagePacket;
import net.mehvahdjukaar.supplementaries.common.network.NetworkHandler;
import net.mehvahdjukaar.supplementaries.configs.CommonConfigs;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SpeakerBlockTile extends BlockEntity implements Nameable, IOwnerProtected {
    private UUID owner = null;

    private String message = "";
    private boolean narrator = false;
    //distance in blocks
    private double volume = CommonConfigs.Blocks.SPEAKER_RANGE.get();
    private Component customName;

    public SpeakerBlockTile(BlockPos pos, BlockState state) {
        super(ModRegistry.SPEAKER_BLOCK_TILE.get(), pos, state);
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    @Override
    public Component getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    public Component getCustomName() {
        return this.customName;
    }

    public Component getDefaultName() {
        return Component.translatable("block.supplementaries.speaker_block");
    }

    public double getVolume() {
        return volume;
    }

    public boolean isNarrator() {
        return narrator;
    }

    public String getMessage() {
        return message;
    }

    public void setSettings(double volume, boolean narrator, String message){
        this.volume = volume;
        this.narrator = narrator;
        this.message = message;
    }

    public void setNarrator(boolean narrator) {
        this.narrator = narrator;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("CustomName", 8)) {
            this.customName = Component.Serializer.fromJson(compound.getString("CustomName"));
        }

        this.message = compound.getString("Message");
        if (!CommonConfigs.Blocks.SPEAKER_NARRATOR.get()) this.narrator = false;
        else this.narrator = compound.getBoolean("Narrator");
        this.volume = compound.getDouble("Volume");
        this.loadOwner(compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (this.customName != null) {
            compound.putString("CustomName", Component.Serializer.toJson(this.customName));
        }
        compound.putString("Message", this.message);
        compound.putBoolean("Narrator", this.narrator);
        compound.putDouble("Volume", this.volume);
        this.saveOwner(compound);
    }

    public void sendMessage() {
        BlockState state = this.getBlockState();

        if (level instanceof ServerLevel server && !this.message.equals("")) {
            // particle
            BlockPos pos = this.getBlockPos();
            level.blockEvent(pos, this.getBlockState().getBlock(), 0, 0);

            Style style = !state.getValue(SpeakerBlock.ANTIQUE) ? Style.EMPTY.applyFormats(ChatFormatting.ITALIC) :
                    Style.EMPTY.withFont(ModTextures.ANTIQUABLE_FONT).applyFormats(ChatFormatting.ITALIC);

            Component message = Component.literal(this.getName().getString() + ": " + this.message)
                    .withStyle(style);

            NetworkHandler.CHANNEL.sendToAllClientPlayersInRange(server, pos,
                    this.volume, new ClientBoundPlaySpeakerMessagePacket(message, this.narrator));

        }
    }

    @Nullable
    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID owner) {
        this.owner = owner;
    }


}