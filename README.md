# PinkFont
PinkFont is a Minecraft mod that allows you to tweak the color of the rendered text!
![image](https://github.com/RaydanOMGr/PinkFont/blob/screenshots/main_menu.webp?raw=true)

## Usage
To install the mod, you need to drop the jar for your version of Minecraft into the `mods` folder.
Then, you also need to install [YACL](https://modrinth.com/mod/yacl) for your version of the game the same way.

Once the mod is installed, and you boot into the game, you will notice that all previously white text is now of a pinkish tint.
You can configure the color of the text yourself using two ways:

### Config menu
#### Fabric
Install the [Mod Menu](https://modrinth.com/mod/modmenu/) mod by putting it into the `mods` folder, as you would with any other mod.

After installing the mod, once you are in the game, you will notice that a new button labeled "Mods" appeared on the main menu.
![image](https://github.com/RaydanOMGr/PinkFont/blob/screenshots/mods_button.webp?raw=true)

If you press the button, a menu that lists all your mods will open, looking something like this:
![image](https://github.com/RaydanOMGr/PinkFont/blob/screenshots/modmenu.webp?raw=true)

Hovering over the PinkFont entry will allow you to open the configuration menu for it
![image](https://github.com/RaydanOMGr/PinkFont/blob/screenshots/hover.webp?raw=true)

#### NeoForge
On NeoForge, no additional mods are required for a mod menu, as NeoForge by default ships with one.

When you open the mod menu, you will be presented with a list of all mods installed in your current instance of the game.
You will want to find the PinkFont entry in that list and select it by clicking on it, then you will be able to press the Config button, which will open the configuration menu.

#### Configuration

The configuration menu is the same independent of platform.
![image](https://github.com/RaydanOMGr/PinkFont/blob/screenshots/config_screen.webp?raw=true)

Hovering over any of the options will show a description of how they work on the left side of the screen.
From now on, you can experiment with the colors and even add gradient colors like this:
![image](https://github.com/RaydanOMGr/PinkFont/blob/screenshots/rainbow_gradient.webp?raw=true)

Pretty [neat](https://www.curseforge.com/minecraft/mc-mods/neat), right?

### Config file
If you do not wish to install the Mod Menu mod, you may manually modify the mods configuration file.

For that, you will want to navigate into the `config` directory in your `.minecraft` folder.
In there, find a file named `pinkfont.json5`. This is the PinkFont config file.

Open that file using a text editor of your choice. It will look something like this:
```json5
{
	// The color mode defines how the colors get applied. Possible modes: SUBTRACT, SET, SET_KEEP_BRIGHTNESS
	colorMode: "SUBTRACT",
	// Whether dark colors should be made brighter so applied colors can be seen
	brightenDark: false,
	// If the previous option is toggled, this is the brightness value that text will have at least (range: 0.0 - 1.0, values above 0.3 may affect shadow colors)
	minBrightness: 0.2,
	// The amount of time it takes to go from one color to another
	duration: 0.0,
	// A list of colors to smoothly swap between in the format "#RRGGBBAA"
	colors: [
		"#ffccffff"
	]
}
```

It is fairly descriptive by itself, so I don't think I need to clarify anything additionally here.

## License
PinkFont is licensed under the MIT license.