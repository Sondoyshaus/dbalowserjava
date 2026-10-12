# Dodong Balodowser 🌐

A sleek, lightweight desktop web browser written in Java using JavaFX and styled after Firefox's modern dark theme. **Dodong Balodowser** features a custom home screen, integrated multi-tab styling, navigation controls, and built-in developer tools.

## ✨ Features

* **Firefox-Inspired Dark UI**: Modern dark theme crafted with CSS.

* **Custom Home Screen**: Quick shortcuts (Google, Gemini, GitHub, YouTube, StackOverflow) and direct search bar functionality.

* **Developer Tools Console (`</>`)**: Execute JavaScript commands directly against active pages and inspect outputs in real time.

* **Compact Window Resolution**: Pre-configured default viewport optimized for 900x700 resolution.

* **Software Rendering Fallback**: Built-in hardware acceleration flags (`-Dprism.order=sw`) to ensure crash-free performance across hardware setups.

## 🛠️ Prerequisites

Before compiling and running the project locally, ensure you have the following installed:

* **JDK 27** (or Java 17+)

* **JavaFX SDK 27** (containing `javafx.controls` and `javafx.web`)

## 📂 Project Structure

```
├── SimpleBrowser.java      # Main application source code
├── firefox.css             # Theme stylesheet for tab bar, address bar, and DevTools
├── build_fat_jar.bat       # Windows build script to compile and package into JAR
├── run_app.bat             # Execution script with JavaFX module flags
└── README.md               # Project documentation

```

## 🚀 Getting Started

### 1. Clone the Repository

```
git clone https://github.com/your-username/Dodong-Balodowser.git
cd Dodong-Balodowser

```

### 2. Configure Paths

Ensure your build scripts point to your installed JDK and JavaFX SDK directories. For example, in `build_fat_jar.bat` and `run_app.bat`:

```
set "JDK_DIR=C:\program files\JDK\jdk-27"
set "JFX_DIR=C:\program files\JFX\javafx-sdk-27\lib"

```

### 3. Build the Application

Run the build script to compile the code and generate the `DodongBalodowser.jar` file:

```
build_fat_jar.bat

```

### 4. Launch the Browser

Run the execution script to launch Dodong Balodowser:

```
run_app.bat

```

## 📄 License

This project is licensed under the Apache License 2.0 — see the [LICENSE](https://github.com/Sondoyshaus/dbalowserjava/blob/main/LICENSE) file for details.
