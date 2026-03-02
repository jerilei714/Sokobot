# Sokobot

A Sokoban puzzle solver and game player written in Java. Sokobot provides both interactive gameplay and an automated solver that finds optimal solutions to Sokoban puzzles using A* search with intelligent heuristics.

## Overview

Sokoban is a classic puzzle game where the player must push boxes onto designated goal locations. Sokobot implements:

- **Interactive Freeplay Mode**: Play Sokoban levels manually using keyboard controls
- **Automated Solver Mode**: Watch the AI solve puzzles automatically using pathfinding algorithms
- **GUI Interface**: Visual representation of game states with Swing-based graphics
- **Multiple Levels**: 17 pre-built maps ranging from simple 2-box puzzles to complex 5-box challenges

## Features

### Solver Algorithm
- Implements A* search with admissible heuristics for efficient puzzle solving
- Detects deadlock states to prune invalid search branches
- Optimal move sequence generation for puzzle solutions

### Game Modes
- **Freeplay (`fp`)**: Control the player manually and solve puzzles at your own pace
- **Bot (`bot`)**: Automatically generates and executes the optimal solution

### Available Maps
The project includes 17 pre-configured maps:
- 2-box puzzles (3 levels)
- 3-box puzzles (3 levels)
- 4-box puzzles (3 levels)
- 5-box puzzles (3 levels)
- Original challenge maps (3 levels)
- Test maps for development

## Building

### Prerequisites
- Java JDK 8 or later
- No external dependencies

### Compilation
```bash
cd sokobot
javac -d bin src/main/*.java src/gui/*.java src/reader/*.java src/solver/*.java
```

## Usage

### Running the Game
```bash
java -cp bin main.Driver <map_name> <mode>
```

### Parameters
- `<map_name>`: Name of the map file (without `.txt` extension)
  - Examples: `twoboxes1`, `threeboxes2`, `original1`
- `<mode>`: Game mode
  - `fp`: Freeplay (manual control)
  - `bot`: Automated solver

### Examples

Play the first two-box puzzle manually:
```bash
java -cp bin main.Driver twoboxes1 fp
```

Watch the solver solve the first three-box puzzle:
```bash
java -cp bin main.Driver threeboxes1 bot
```

## Map Format

Maps are defined in text files using the following symbols:
- `#`: Wall
- `.`: Goal location
- `$`: Box
- `@`: Player starting position
- ` ` (space): Empty walkable space

## Project Structure

```
sokobot/
├── src/
│   ├── main/
│   │   └── Driver.java           # Entry point
│   ├── gui/
│   │   ├── GameFrame.java        # Main window
│   │   ├── GamePanel.java        # Game rendering
│   │   └── BotThread.java        # Solver threading
│   ├── reader/
│   │   ├── FileReader.java       # Map file parsing
│   │   └── MapData.java          # Map data structure
│   └── solver/
│       ├── SokoBot.java          # A* solver implementation
│       ├── Node.java             # Search tree node
│       └── Heuristics.java       # Heuristic functions
└── maps/                         # Level definitions
```

## Controls (Freeplay Mode)

Use arrow keys or WASD to move the player. Push boxes onto the goal locations (`.`) to complete the level.

## License

BSD 2-Clause License - See [LICENSE](LICENSE) file for details.

## Author

Created by Ram Brodett
