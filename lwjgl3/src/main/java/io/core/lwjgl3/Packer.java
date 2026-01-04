package io.core.lwjgl3;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer
{
    public static void main(String[] args) {
        packTextures();
    }

    /**
     * TexturePacker provides an efficient algorithm for packing textures into a single file called an atlas.
     * Packed textures can be found inside a given atlas by their names, such as "grass"
     * (the same name as the packed file).
     *
     * Example:
     * TexturePacker.process("assets/textures/tiles", "assets", "tiles");
     * where "tiles" is the name of the atlas. This atlas contains all textures from the
     * "assets/textures/tiles" folder.
     *
     * Finding a region inside a texture atlas is done as follows:
     * TextureRegion grass = atlas.findRegion("grass");
     *
     * Drawing is done by passing a reference to the texture region.
     */
    private static void packTextures() {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.duplicatePadding = true;
        // Duplicates the edge pixels of each sprite in the atlas.
        // This prevents "ghost pixels" or thin lines between tiles when the GPU interpolates textures
        // (e.g., during scaling or linear filtering).
        // TLDR:   Repeat edge pixels to prevent ghosting between tiles

        TexturePacker.process(settings,"assets/textures/tiles", "assets", "tiles");
        TexturePacker.process(settings,"assets/textures/entities", "assets", "entities");
    }
}
