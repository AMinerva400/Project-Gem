package com.omnipotence.game.Menu_Options.Journey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.omnipotence.game.Main;
import com.omnipotence.game.MenuScreen;
import com.omnipotence.game.Practice.abcTutorial;
import com.omnipotence.game.Practice.battleTutorial;
import com.omnipotence.game.Stage.gameData;
import com.omnipotence.game.Stage.gameLevel;
import com.omnipotence.game.Stage.gameStage;
import com.omnipotence.game.defaultScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC and is based on Faizaan Datoo's Screen.
 * This is the level selection screen.
 */

public class LevelScreen extends defaultScreen {

	private Main gameInstance;
	private Skin skin;
	private Stage stage;
    private Integer start, end;
    private Table stageContainer;

    /**
     * This is the Constructor.
     * @param gameInstance: Main variable.
     * */
	public LevelScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
        start = 0;
        end =  gameInstance.gameStages.size();
        Initialize();
	}

    /**
     * This is the Constructor.
     * @param gameInstance: Main variable.
     * @param start: The position of the starting stage.
     * @param end: The position of the ending stage.
     * */
    public LevelScreen(Main gameInstance, int start, int end) {
        this.gameInstance = gameInstance;
        this.start = start;
        this.end = end;
        Initialize();
    }

    /**
     * This function initializes central components
     */
    private void Initialize() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        initializeSkin();

        // Master table that holds all level buttons and logos
        Table container = new Table();
        stage.addActor(container);
        container.setFillParent(true);

        stageContainer = new Table();

        getStage();

        //Sets up the exitbutton
        final ImageButton exitbutton = new ImageButton(AssetManager.getInstance()
                .convertTextureToDrawable("Exit.png"));
        float resizeWidth = Constants.gameWidth(150f);
        float resizeHeight = Constants.gameHeight(150f);
        exitbutton.setSize(resizeWidth, resizeHeight);
        exitbutton.setPosition(Constants.gameX(.975f, resizeWidth),
                Constants.gameY(.975f, resizeHeight));
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameInstance.setScreen(new MenuScreen(gameInstance));
            }
        });
        container.add(exitbutton);

        // Add the levels to a scroll pane
        ScrollPane scroll = new ScrollPane(stageContainer, skin);
        scroll.setFadeScrollBars(false); // Make scroll bars always show
        scroll. setupOverscroll(0f, 30f, 200f);
        container.add(scroll).expand().fill().row();
    }

    /**
     * This function calls displayStage of every stage in gameStages
     */
    private void getStage() {
        for(int i = start; i < end; i++) {
            gameStage gStage = gameInstance.gameStages.get(i);
            displayStage(gStage, i);
        }
    }

    /**
     * This function displays the name, levels, and score of the stage.
     */
    private void displayStage(gameStage gStage, int stageIndex) {
        //Levels Table
        Table levelsContainer = new Table();
        levelsContainer.defaults().pad(5f, 5f, 5f, 5f);
        //Creating the level Grid based on the level size.
        int levelSize = gStage.getLevelSize();
        int columns = (levelSize == 3) ? 2 : (int) Math.sqrt(levelSize);
        int rows = (int) Math.ceil(levelSize / (float)columns);
        //Creating a label for the stage name.
        Label stageName = new Label(gStage.getStageName(), skin, "largeLabel");
        stageName.setAlignment(Align.center);
        stageName.setFontScale(Constants.gameWidth(1.5f), Constants.gameHeight(1.5f));
        levelsContainer.add(stageName).colspan(columns).row();
        //Setting the background for the stage.
        levelsContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("background" + ((stageIndex % 2 == 0) ? "1" : "4")));
        //Adding the levels to the Levels table.
        int levelIndex = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns && levelIndex < levelSize; j++) {
                /*System.out.println("Stage index: "+stageIndex+" Level index: "
                        +levelIndex+" Level Size: "+ levelSize);*/
                levelsContainer.add(createLevelButton(stageIndex, levelIndex)).expand().fill();
                levelIndex++;
            }
            levelsContainer.row();
        }
        //Creates the score for the stage.
        Table levelScore = new Table();
        int userScore = gStage.getStageScore(columns);
        for(int i = 0; i < columns; i++) {
            Image image = new Image(new TextureRegion(new Texture(Gdx.files.internal("Chest_"+
                    ((userScore - i > 0) ? "Gem" : "Close") +".png"))));
            levelScore.add(image).width(Constants.gameWidth(image.getWidth()))
                    .height(Constants.gameHeight(image.getHeight())).pad(0f, 60f, 0f, 60f);
        }
        levelsContainer.add(levelScore).colspan(columns).row();
        //Creates a new row after every 3 columns
        if(!(start == 0 && end == gameInstance.gameStages.size())) {
            stageContainer.add(levelsContainer).width(Constants.getScreenWidth())
                    .height(Constants.getScreenHeight()).colspan(1);
        } else if((stageIndex+1) % 3 == 0) {
            stageContainer.add(levelsContainer).width(Constants.getScreenWidth())
                    .height(Constants.getScreenHeight()).row();
        } else {
            stageContainer.add(levelsContainer).width(Constants.getScreenWidth())
                    .height(Constants.getScreenHeight()).colspan(1);
        }
    }

    /**
     * This function creates the level button.
     */
    private Button createLevelButton(int stageIndex, int levelIndex) {
        gameLevel level = gameInstance.gameStages.get(stageIndex).getLevel(levelIndex);
        String levelName = level.getLevelName();

        //Creating the level button.
        Button button = new Button(skin);
        ButtonStyle style = button.getStyle();
        style.up = style.down = null;
        //Breaks down the levelName by maxChars allowable per Button Width
        int maxChars = 30;
        int height = 0;
        String s = "";
        while(levelName.length() > maxChars-1) {
            String tempString = levelName.substring(0, maxChars);
            int j = (tempString.lastIndexOf(" ") > maxChars/2) ?
                    tempString.lastIndexOf(" ") : maxChars;
            s += (levelName.substring(0, j) + "\n");
            levelName = levelName.substring(j);
            height++;
        }
        s += levelName;
        levelName = s;
        //Creating the level height and width.
        float buttonHeight, buttonWidth;
        buttonWidth = (levelName.length() < maxChars+1) ? (levelName.length() < 11) ? 250f  :
                30f * levelName.length() : 30f * maxChars;
        buttonHeight = (height > 0) ? (height + 1) * 110f : 130f;

        Boolean isBattle = levelName.contains("BATTLE");

        //Creates the label for the level name.
        Label label = new Label(levelName, skin, "default");
        label.setAlignment(Align.center);
        label.setFontScale(Constants.gameWidth(1f), Constants.gameHeight(1f));

        gameData data = level.levelData;
        boolean unlocked = data != null && data.isUnlocked();
        //Decides on the level button background
        Image buttonImage = new Image((skin.getDrawable((unlocked) ? "top" :
                (isBattle) ? "battle" : "locked-level")));
        //Adds the name label and sets the button size
        button.stack(buttonImage, label).width(Constants.gameWidth(buttonWidth)).
                height(Constants.gameHeight(buttonHeight));
        //Add listener to the button.
        addClickListener(button, stageIndex, levelIndex);

        return button;
    }

	/**
	 * This function sets up the skin.
	 */
	private void initializeSkin() {
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"), new TextureAtlas(
				"skin/uiskin.atlas"));
		// Set the unlocked level buttons to be green.
		skin.add("top", skin.newDrawable("default-round", Color.valueOf("#70ff7c")),
				Drawable.class);
		// Set the locked level buttons to be blue.
		skin.add("locked-level", skin.newDrawable("default-round", new Color(0f, 151f, 167f, .9f)),
				Drawable.class);
        // Set the battle level buttons to be red.
        skin.add("battle", skin.newDrawable("default-round", Color.valueOf("#ff7070")),
                Drawable.class);
        FileHandle fileHandle = Gdx.files.internal("skin/fonts/chalkboard-font.fnt");
        FileHandle fileHandle2 = Gdx.files.internal("skin/fonts/chalkboard-font-large.fnt");
        // Set regular font label
        skin.add("regularFont", new BitmapFont(fileHandle));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("regularFont");
        skin.add("default", labelStyle);
        // Set large font label
        skin.add("largeFont", new BitmapFont(fileHandle2));
        Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = skin.getFont("largeFont");
        largeLabelStyle.fontColor = Color.valueOf("#ff7070");
        skin.add("largeLabel", largeLabelStyle);
	}

    /**
     * This function handles the button clicks/taps and plays the level assigned to the button.
     * This function also plays the tutorials for the practice mode gameplay and the battle mode.
     * It is assumed the first battle mode is always the 3rd level of the first stage.
     */
    private void addClickListener(Button button, final int stageIndex, final int levelIndex) {
        final gameStage gStage = gameInstance.gameStages.get(stageIndex);
        final boolean isUnLocked = gStage.getLevel(levelIndex).levelData.isUnlocked();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isUnLocked) {
                    AssetManager.getInstance().getSound("button-click").play();
                    gStage.setCurrentLevel(levelIndex);
                    gameInstance.setCurrentStage(stageIndex);
                    gameInstance.setScreen((stageIndex == 0 && levelIndex == 0) ?
                            new abcTutorial(gameInstance, start, end) :
                            (stageIndex == 0 && levelIndex == 2) ?
                                    new battleTutorial(gameInstance, 'b', start, end) :
                                    gStage.playLevel(gameInstance, start, end));
                }
            }
        });
    }

    /**
     * This function renders the textures.
     */
    @Override
    public void render(float delta) {
        // Update and render the stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * This function disposes of the assets.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}
