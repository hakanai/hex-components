Hex Components - A simple hex viewer written in Java.

Copyright (C) 2009-2014  Trejkaz, Hex Project


Usage
-----

For now, see the example in the examples/ subdirectory.


UI Defaults
-----------

`HexViewer` supports the following additional UI defaults.
In all cases, if a value is absent, sensible fallback logic is used to derive a value.

| UI default                       | Fallback                                 |
| -------------------------------- | ---------------------------------------- |
| `HexViewer.background`           | `TextArea.background`                    |
| `HexViewer.foreground`           | `TextArea.foreground`                    |
| `HexViewer.selectionBackground`  | `TextArea.selectionBackground`           |
| `HexViewer.selectionForeground`  | `TextArea.selectionForeground`           |
| `HexViewer.cursorBackground`     | Darker version of `selectionBackground`  |
| `HexViewer.cursorForeground`     | `selectionForeground`                    |
| `HexViewer.cursorRowBackground`  | Lighter version of `selectionBackground` |
| `HexViewer.offsetForeground`     | Lighter version of `foreground`          |
| `HexViewer.font`                 | `TextArea.font`                          |


Building
--------

You'll need a Java build environment.  I'm developing this on Java 7 at
the moment.

You'll also need Ant.  All the other dependencies should be bundled.
If something is missing, prod me to fix it.

To build, execute 'ant' in the top directory. Files for the distribution
will appear in build/dist.


