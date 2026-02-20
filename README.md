# PinkFont
PinkFont is a Minecraft mod that allows you to tweak the color of the rendered text!
![image]()

## Usage
To install the mod, all you need is to drop the jar for your version of Minecraft into the `mods` folder.

Once the mod is installed, and you boot into the game, you will notice that all previously white text is now of a pinkish tint.
You can configure the color of the text yourself using two ways:

### Modmenu (recommended)
Install the [Mod Menu](https://modrinth.com/mod/modmenu/) mod by putting it into the `mods` folder, as you would with any other mod.

After installing the mod, once you are in the game, you will notice that a new button labeled "Mods" appeared on the main menu.
![image]()

If you press the button, a menu that lists all your mods will open, looking something like this:
![image]()

Hovering over the PinkFont entry will allow you to open the configuration menu for it
![image]()

The configuration menu lets you change 3 different numeric values using sliders, those values being:
- Cyan 
- Magenta
- Yellow

To the average user, RGB is likely more familiar, but PinkFont uses CMY as it subtracts from white instead of adding to black.
If all values are set to 0, all text will keep its original color. If all values are set to 1, all text becomes black.

### Config file
If you do not wish to install the Mod Menu mod, you may manually modify the mods configuration file.

For that, you will want to navigate into the `config` directory in your `.minecraft` folder.
In there, find a file named `pinkfont.json5`. This is the PinkFont config file.

Open that file using a text editor of your choice. It will look something like this:
```json5
{
	cyan: 0.0,
	magenta: 0.2,
	yellow: 0.0
}
```

You may change any of the 3 values (explained before in the Modmenu segment) to your wish, as long as you keep them between 0.0 and 1.0.

## License
PinkFont is licensed under the MIT license.