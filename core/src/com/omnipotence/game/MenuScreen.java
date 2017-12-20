package com.omnipotence.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.omnipotence.game.Menu_Options.Training.TrainingSelectionScreen;
import com.omnipotence.game.Menu_Options.Journey.CharacterSelectionScreen;
import com.omnipotence.game.Menu_Options.ScoreScreen;
import com.omnipotence.game.Menu_Options.StoryScreen;
import com.omnipotence.game.Menu_Options.Battle.MultiplayerSelectionScreen;
import com.omnipotence.game.util.AssetManager;
import com.omnipotence.game.util.Constants;

/**
 * Copyright 2015, Omnipotence, LLC, All rights reserved.
 * Created by Omnipotence, LLC.
 * The main menu for the game.
 */
public class MenuScreen extends defaultScreen {

	private Main gameInstance;
	private Stage stage;
    private SpriteBatch spriteBatch;
    private Animation ring;
    private float time = 0f;

	/**
	 * This is the Constructor.
     * @param gameInstance: Instance of Main for access to variables stored only in the
	 *            Main class.
	 */
	public MenuScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
        stage = new Stage();
        spriteBatch = new SpriteBatch();

        initMainContainer();

        // Add Textures to the main container
        addLogo();
        addButtons();

        Gdx.input.setInputProcessor(stage); // Allows the stage to take in input
	}

    /**
     * This function renders the textures and this includes the ring animation.
     */
	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
        spriteBatch.begin();
        time += delta;
        spriteBatch.draw(ring.getKeyFrame(time, true),
                Constants.gameX(.825f, Constants.gameWidth(ring.getKeyFrame(time).getRegionWidth())),
                Constants.gameY(.9f, Constants.gameWidth(ring.getKeyFrame(time).getRegionHeight())),
                Constants.gameWidth(ring.getKeyFrame(time).getRegionWidth()),
                Constants.gameHeight(ring.getKeyFrame(time).getRegionHeight()));
        spriteBatch.end();
	}

	/**
	 * Initialize the main table (container) with a background. This table holds
	 * and positions all buttons on the menu.
	 */
	private void initMainContainer() {
        Table mainContainer = new Table();
        mainContainer.setBackground(AssetManager.getInstance()
                .convertTextureToDrawable("menuBackground"));
		mainContainer.setFillParent(true);
        stage.addActor(mainContainer);
	}
	
	/**
	 * This function adds the logo and sets up the ring animation.
	 */
	private void addLogo() {
        Image projectGemLogo = new Image(AssetManager.getInstance()
                .convertTextureToDrawable("gameLogo.png"));
        ring = new Animation(1/7f, (new TextureAtlas(
                Gdx.files.internal("Ring/RingWand.txt"))).getRegions());
/*        chest = new Animation(1/15f, (new TextureAtlas(
                Gdx.files.internal("Chest/chestOpen.txt"))).getRegions());*/
        float width = Constants.gameWidth(projectGemLogo.getWidth()*1.75f);
        float height = Constants.gameHeight(projectGemLogo.getHeight()*1.75f);
        projectGemLogo.setSize(width, height);
        projectGemLogo.setPosition(Constants.gameX(.4f, width), Constants.gameY(.85f, height));
		stage.addActor(projectGemLogo);
	}

	/**
	 * This function adds the menu buttons to the Screen.
	 */
	private void addButtons() {
        setButton("training", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameInstance.setScreen(new TrainingSelectionScreen(gameInstance));
                System.out.println("Playing training Screen");
                dispose();
            }
        }, 0);

        setButton("journey", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //AssetManager.getInstance().getSound("button-click").play();
                gameInstance.setScreen(new CharacterSelectionScreen(gameInstance));
                dispose();
            }
        }, 1);

        setButton("story", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameInstance.setScreen(new StoryScreen(gameInstance));
                System.out.println("Playing Story Screen");
                dispose();
            }
        }, 2);

        setButton("multiplayer", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameInstance.setScreen(new MultiplayerSelectionScreen(gameInstance));
                dispose();
            }
        }, 3);

        setButton("scores", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameInstance.setScreen(new ScoreScreen(gameInstance));
            }
        }, 4);

    }

    /**
     * This function sets up the basic button stuff.
     */
    private void setButton(String s, ChangeListener listener, int i) {
        TextureRegionDrawable buttonIcon = AssetManager.getInstance()
                .convertTextureToDrawable(s+"Button.png");
        TextureRegionDrawable buttonLogo = AssetManager.getInstance()
                .convertTextureToDrawable(s+"Logo.png");
        ImageButton imageButton = new ImageButton(buttonIcon);
        imageButton.add(new Image(buttonLogo));
        float width = Constants.gameWidth(256f + 60f * s.length());
        float height = Constants.gameHeight(192f);
        imageButton.setSize(width, height);
        imageButton.setPosition(Constants.gameX(.35f, 0),
                Constants.gameY(.65f-(.15f * i),height));
        imageButton.addListener(listener);
        stage.addActor(imageButton);
    }

    /**
     * This function disposes of the assets.
     */
    @Override
    public void dispose() {
        stage.dispose();
        spriteBatch.dispose();
    }

}
