import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SimpleBrowser extends Application {

    private static final String HOME_URL = "balodowser://home";

    private final String homePageHtml = """
        <html>
        <head>
            <style>
                body { background-color: #1c1b22; color: #fbfbfe; font-family: 'Segoe UI', sans-serif; display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100vh; margin: 0; }
                h1 { color: #00ddff; margin-bottom: 5px; font-size: 36px; }
                p { color: #a2a0a6; margin-top: 0; margin-bottom: 30px; font-size: 14px; }
                .search-box { display: flex; width: 480px; background: #2b2a33; border: 1px solid #52525e; border-radius: 8px; padding: 10px 14px; box-shadow: 0 4px 12px rgba(0,0,0,0.3); }
                .search-box input { background: transparent; border: none; color: #fbfbfe; font-size: 16px; outline: none; width: 100%; }
                .shortcuts { display: flex; gap: 20px; margin-top: 35px; }
                .shortcut { background: #2b2a33; border: 1px solid #42414d; padding: 15px 20px; border-radius: 10px; cursor: pointer; text-align: center; text-decoration: none; color: #fbfbfe; width: 85px; font-size: 13px; transition: 0.2s; }
                .shortcut:hover { background: #42414d; border-color: #00ddff; }
            </style>
        </head>
        <body>
            <h1>Dodong Balodowser</h1>
            <p>Your lightning-fast custom Java web browser</p>
            <div class="search-box">
                <input type="text" id="searchInput" placeholder="Search Google or enter address..." autofocus>
            </div>
            <div class="shortcuts">
                <a class="shortcut" href="https://www.google.com">Google</a>
                <a class="shortcut" href="https://www.github.com">GitHub</a>
                <a class="shortcut" href="https://www.youtube.com">YouTube</a>
                <a class="shortcut" href="https://stackoverflow.com">StackOverflow</a>
            </div>
            <script>
                document.getElementById('searchInput').addEventListener('keypress', function (e) {
                    if (e.key === 'Enter') {
                        let val = this.value;
                        if (val.trim() !== '') {
                            if (!val.startsWith('http://') && !val.startsWith('https://')) {
                                if (val.includes('.') && !val.includes(' ')) {
                                    val = 'https://' + val;
                                } else {
                                    val = 'https://www.google.com/search?q=' + encodeURIComponent(val);
                                }
                            }
                            window.location.href = val;
                        }
                    }
                });
            </script>
        </body>
        </html>
    """;

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Load Home screen initially
        loadPage(HOME_URL, webEngine);

        // --- Tab Bar Component ---
        HBox tabBar = new HBox();
        tabBar.getStyleClass().add("tab-bar");

        HBox activeTab = new HBox(8);
        activeTab.getStyleClass().add("tab-active");
        
        Label tabFavicon = new Label("🌐");
        tabFavicon.setStyle("-fx-font-size: 13px;");
        Label tabTitle = new Label("Home");
        tabTitle.getStyleClass().add("tab-title");
        Button tabClose = new Button("×");
        tabClose.getStyleClass().add("tab-close-btn");

        activeTab.getChildren().addAll(tabFavicon, tabTitle, tabClose);
        
        Button newTabBtn = new Button("+");
        newTabBtn.getStyleClass().add("new-tab-btn");
        newTabBtn.setOnAction(e -> loadPage(HOME_URL, webEngine));

        tabBar.getChildren().addAll(activeTab, newTabBtn);

        // --- Navigation Bar Components ---
        Button btnBack = new Button("‹");
        Button btnForward = new Button("›");
        Button btnReload = new Button("↻");
        Button btnHome = new Button("⌂");

        btnBack.getStyleClass().addAll("nav-btn", "nav-circle");
        btnForward.getStyleClass().addAll("nav-btn", "nav-circle");
        btnReload.getStyleClass().add("nav-btn");
        btnHome.getStyleClass().add("nav-btn");

        btnBack.setDisable(true);
        btnForward.setDisable(true);

        // Address Bar Container
        HBox addressContainer = new HBox(6);
        addressContainer.getStyleClass().add("url-container");
        addressContainer.setAlignment(Pos.CENTER_LEFT);

        Label lockIcon = new Label("🔒");
        lockIcon.setStyle("-fx-font-size: 11px; -fx-text-fill: #a2a0a6;");
        
        TextField addressBar = new TextField(HOME_URL);
        addressBar.getStyleClass().add("url-bar");
        HBox.setHgrow(addressBar, Priority.ALWAYS);

        Button btnGo = new Button("➔");
        btnGo.getStyleClass().add("go-btn");

        addressContainer.getChildren().addAll(lockIcon, addressBar, btnGo);

        // Developer Tools Toggle Button
        Button btnDevTools = new Button("</>");
        btnDevTools.getStyleClass().add("nav-btn");
        btnDevTools.setTooltip(new javafx.scene.control.Tooltip("Toggle Developer Tools"));

        HBox navBar = new HBox(8);
        navBar.getStyleClass().add("nav-bar");
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.getChildren().addAll(btnBack, btnForward, btnReload, btnHome, addressContainer, btnDevTools);
        HBox.setHgrow(addressContainer, Priority.ALWAYS);

        // Header container
        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(tabBar, navBar);

        // --- Developer Tools Panel Setup ---
        VBox devToolsPanel = new VBox(5);
        devToolsPanel.getStyleClass().add("devtools-panel");
        devToolsPanel.setPadding(new Insets(8));
        devToolsPanel.setVisible(false);
        devToolsPanel.setManaged(false);

        HBox devToolsHeader = new HBox(10);
        devToolsHeader.setAlignment(Pos.CENTER_LEFT);
        Label devToolsTitle = new Label("Dodong DevTools Console");
        devToolsTitle.setStyle("-fx-text-fill: #00ddff; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        Button closeDevTools = new Button("✕");
        closeDevTools.getStyleClass().add("tab-close-btn");
        closeDevTools.setOnAction(e -> {
            devToolsPanel.setVisible(false);
            devToolsPanel.setManaged(false);
        });
        
        HBox.setHgrow(devToolsTitle, Priority.ALWAYS);
        devToolsHeader.getChildren().addAll(devToolsTitle, closeDevTools);

        TextArea consoleOutput = new TextArea();
        consoleOutput.getStyleClass().add("devtools-console");
        consoleOutput.setEditable(false);
        consoleOutput.setText("Welcome to Dodong DevTools!\nType JS code below and press Enter to execute on the active page.\n");
        VBox.setVgrow(consoleOutput, Priority.ALWAYS);

        TextField jsInput = new TextField();
        jsInput.getStyleClass().add("devtools-input");
        jsInput.setPromptText("Run JavaScript (e.g. document.title) and press Enter...");
        
        jsInput.setOnAction(e -> {
            String script = jsInput.getText();
            if (!script.isEmpty()) {
                consoleOutput.appendText("> " + script + "\n");
                try {
                    Object result = webEngine.executeScript(script);
                    consoleOutput.appendText("  < " + (result != null ? result.toString() : "undefined") + "\n");
                } catch (Exception ex) {
                    consoleOutput.appendText("  ! Error: " + ex.getMessage() + "\n");
                }
                jsInput.clear();
            }
        });

        devToolsPanel.getChildren().addAll(devToolsHeader, consoleOutput, jsInput);

        btnDevTools.setOnAction(e -> {
            boolean isVisible = devToolsPanel.isVisible();
            devToolsPanel.setVisible(!isVisible);
            devToolsPanel.setManaged(!isVisible);
        });

        // Main Browser View layout container
        BorderPane browserPane = new BorderPane();
        browserPane.setTop(topContainer);
        browserPane.setCenter(webView);

        VBox rootContainer = new VBox();
        VBox.setVgrow(browserPane, Priority.ALWAYS);
        rootContainer.getChildren().addAll(browserPane, devToolsPanel);

        // --- Navigation Actions ---
        addressBar.setOnAction(event -> loadPage(addressBar.getText(), webEngine));
        btnGo.setOnAction(event -> loadPage(addressBar.getText(), webEngine));
        btnReload.setOnAction(event -> {
            if (webEngine.getLocation() != null && webEngine.getLocation().equals(HOME_URL)) {
                loadPage(HOME_URL, webEngine);
            } else {
                webEngine.reload();
            }
        });
        btnHome.setOnAction(event -> loadPage(HOME_URL, webEngine));

        btnBack.setOnAction(event -> {
            WebHistory history = webEngine.getHistory();
            if (history.getCurrentIndex() > 0) {
                history.go(-1);
            }
        });

        btnForward.setOnAction(event -> {
            WebHistory history = webEngine.getHistory();
            if (history.getCurrentIndex() < history.getEntries().size() - 1) {
                history.go(1);
            }
        });

        webEngine.getHistory().currentIndexProperty().addListener((obs, oldIdx, newIdx) -> {
            int idx = newIdx.intValue();
            WebHistory history = webEngine.getHistory();
            btnBack.setDisable(idx <= 0);
            btnForward.setDisable(idx >= history.getEntries().size() - 1);
        });

        webEngine.locationProperty().addListener((obs, oldUrl, newUrl) -> {
            if (newUrl != null && !newUrl.equals("data:text/html;charset=utf-8,...")) {
                addressBar.setText(newUrl.equals(HOME_URL) ? "balodowser://home" : newUrl);
                lockIcon.setVisible(newUrl.startsWith("https://"));
            }
        });

        webEngine.titleProperty().addListener((obs, oldTitle, newTitle) -> {
            String title = (newTitle == null || newTitle.isEmpty()) ? "Home" : newTitle;
            if (webEngine.getLocation() != null && webEngine.getLocation().equals(HOME_URL)) {
                title = "Home";
            }
            tabTitle.setText(title);
            primaryStage.setTitle(title + " — Dodong Balodowser");
        });

        // --- Scene Setup (900x700 resolution) ---
        Scene scene = new Scene(rootContainer, 900, 700);
        scene.getStylesheets().add(getClass().getResource("firefox.css").toExternalForm());

        // Optional Window Icon setup (place an icon.png in your folder if desired)
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        } catch (Exception ignored) {}

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dodong Balodowser");
        primaryStage.show();
    }

    private void loadPage(String url, WebEngine engine) {
        if (HOME_URL.equals(url) || url.isEmpty()) {
            engine.loadContent(homePageHtml, "text/html");
        } else {
            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("data:")) {
                url = "https://" + url;
            }
            engine.load(url);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
