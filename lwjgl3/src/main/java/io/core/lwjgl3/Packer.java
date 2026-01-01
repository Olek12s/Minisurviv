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
        TexturePacker.process("assets/textures/tiles", "assets", "tiles");
    }
}
