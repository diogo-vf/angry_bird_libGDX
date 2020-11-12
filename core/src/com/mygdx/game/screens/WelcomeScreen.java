package com.mygdx.game.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AngryBird;
import com.mygdx.game.enums.Language;
import com.mygdx.game.enums.ScreenName;
import com.mygdx.game.models.Button;
import com.mygdx.game.models.Label;
import com.mygdx.game.providers.VocabularyProvider;

import java.util.ArrayList;

public class WelcomeScreen extends ApplicationAdapter implements InputProcessor {

    private Texture background;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private String LanguageToLearn;
    private String LanguageToShowText;

    private Label titleLabel;
    private Label informationLabel;

    private ArrayList<Button> buttons;
    private ArrayList<Label> buttonsLabel;

    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 900;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        background = new Texture(Gdx.files.internal("background.jpg"));

        titleLabel = new Label("Angry Wirds", Color.BLACK, 6);
        informationLabel = new Label(Color.BLACK);

        LanguageToShowText = VocabularyProvider.getInstance().getLanguage(VocabularyProvider.getInstance().getLanguage1());
        LanguageToLearn = VocabularyProvider.getInstance().getLanguage(VocabularyProvider.getInstance().getLanguageWantToLearn());

        Gdx.input.setInputProcessor(this);
    }

    public void update() {
        informationLabel.setText(String.format("Exercice de %s en %s", LanguageToShowText != null ? LanguageToShowText : "Choisir", LanguageToLearn != null ? LanguageToLearn : "Choisir"));
        placeButtons();
    }

    @Override
    public void render() {
        update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        titleLabel.draw(batch, camera.viewportWidth / 2 - titleLabel.getWidth() / 2, WORLD_HEIGHT - titleLabel.getHeight());
        informationLabel.draw(batch, camera.viewportWidth / 2 - informationLabel.getWidth() / 2, WORLD_HEIGHT - titleLabel.getHeight() * 2 - informationLabel.getHeight() - 10);
        for (Button button : buttons)
            button.draw(batch);
        for (Label label : buttonsLabel)
            label.draw(batch, label.getPosition().x, label.getPosition().y);

        batch.end();
    }

    private void placeButtons() {
        ArrayList<String> languages = VocabularyProvider.getInstance().getLanguages();
        buttons = buttons == null  ? new ArrayList() : buttons;
        buttonsLabel = buttonsLabel == null  ? new ArrayList() : buttonsLabel;
        if(LanguageToShowText == null) {
            for ( int i = 0; i < languages.size()-1 ; i++) {
                Label label = new Label(languages.get(i), Color.BLACK, 3);
                Button button = new Button("button_flat.png", "Left_" + languages.get(i), new Vector2(50, 0), 400, 100);
                button.setY(WORLD_HEIGHT - 400 - button.getHeight() * i - 20 * i);
                label.setPosition(button.getX() + 20, button.getY() + button.getHeight() / 2);
                buttons.add(button);
                buttonsLabel.add(label);
                i++;
            }
        }
        if(LanguageToLearn == null) {
            for ( int i = 0; i < languages.size()-1 ; i++) {
                Label label = new Label(languages.get(i), Color.BLACK, 3);
                Button button = new Button("button_flat.png", "right_" + languages.get(i), new Vector2(50 + 450 * 2, 0), 400, 100);
                button.setY(WORLD_HEIGHT - 400 - button.getHeight() * i - 20 * i);
                label.setPosition(button.getX() + 20, button.getY() + button.getHeight() / 2);
                buttons.add(button);
                buttonsLabel.add(label);
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        for(Button myButton : buttons) {
            if (myButton.getBoundingRectangle().contains(new Circle(screenX, screenY, 1)))
            {
                String langue = myButton.getName().substring(myButton.getName().indexOf("_"));
                if(myButton.getName().startsWith("left_"))
                    VocabularyProvider.getInstance().setLanguage1(VocabularyProvider.getInstance().findLanguage(langue));
                else if(myButton.getName().startsWith("left_"))
                    VocabularyProvider.getInstance().setLanguage1(VocabularyProvider.getInstance().findLanguage(langue));
            }
        }

//        VocabularyProvider.getInstance().setLanguage1(Language.fr);
//        VocabularyProvider.getInstance().setLanguageWantToLearn(Language.en);
//        AngryBird.getInstance().push(ScreenName.Game);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
