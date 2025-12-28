package com.quadflame.lumina.listeners;

import net.minecraft.server.v1_8_R3.ChunkSection;
import net.minecraft.server.v1_8_R3.NibbleArray;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.Arrays;

public class ChunkListener implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        relightChunk(event.getChunk());
    }

    private void relightChunk(Chunk bukkitChunk) {
        net.minecraft.server.v1_8_R3.Chunk chunk = ((CraftChunk) bukkitChunk).getHandle();
        for (ChunkSection section : chunk.getSections()) {
            if (section != null) {
                section.b(createSkyLight());
            }
        }
    }

    private NibbleArray createSkyLight() {
        byte[] lightData = new byte[2048];
        Arrays.fill(lightData, (byte) 0xFF);
        return new NibbleArray(lightData);
    }
}
