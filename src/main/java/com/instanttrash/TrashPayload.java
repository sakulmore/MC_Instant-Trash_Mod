package com.instanttrash;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

@org.jspecify.annotations.NullMarked
public record TrashPayload(int syncId, int slotIndex, boolean matchAll) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TrashPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("instant-trash", "trash"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TrashPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, TrashPayload::syncId,
            ByteBufCodecs.INT, TrashPayload::slotIndex,
            ByteBufCodecs.BOOL, TrashPayload::matchAll,
            TrashPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}