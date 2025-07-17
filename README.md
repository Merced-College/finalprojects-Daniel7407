[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=19900856)
# cpsc39-finalProjects

# Terminal File Manager

**Name:** Daniel Pulido-Alaniz
**Date:** 7/17/2025

---

## Overview

Terminal File Manager is a Java-based virtual file system simulation that provides a command-line interface for managing files and directories. It mimics the basic functionality of a real file system, allowing users to practice and learn common terminal commands in a safe, controlled environment.

The program represents the file system as a tree structure of nodes, where each node can be a file or a folder. The `FileManager` class manages the current directory context using a stack and supports navigation and modification of the file system through commands such as:

- **`cd`**: Change the current directory, supporting navigation to subfolders, the parent directory (`..`), or the root.
- **`ls`**: List contents of the current directory, with optional alphabetical sorting.
- **`pwd`**: Print the full path of the current working directory.
- **`mkdir`**: Create new folders inside the current directory.
- **`touch`**: Create new files in the current directory.
- **`delete`**: Remove files or folders from the current directory.
- **`find`**: Search recursively from the current directory for a file or folder by name.
- **`stats`**: Display statistics about the file system, including total files, folders, and maximum directory depth.
- **`help`**: Provide usage information for all commands or a specific command.

The file system hierarchy is built using the `FileSystemNode` class, which differentiates files and folders and stores children nodes for folders. The application also supports basic command validation and outputs helpful messages for invalid operations or inputs.

The program’s modular design allows easy extension by adding new commands or modifying the file system structure. It provides a useful tool for those learning terminal commands, file system navigation, and basic file operations without interacting with the real file system.

---
