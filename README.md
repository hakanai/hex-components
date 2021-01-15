Hex Components - A simple hex viewer written in Java.

Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project


Usage
-----

For now, see the example in the `examples/` subfolder.


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

You'll need a Java build environment.  I'm developing this on Java 11 at
the moment.

To build, execute `gradlew build` in the top project. Jar files will be
created in the `build/libs` folder of each subproject.
