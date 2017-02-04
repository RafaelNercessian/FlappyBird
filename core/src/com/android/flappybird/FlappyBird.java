package com.android.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] passaro;
    private Texture fundo;

    private int movimento=0;
    private int larguraDispositivo;
    private int alturaDispositivo;

    @Override
    public void create() {
        batch = new SpriteBatch();
        passaro=new Texture[3];
        passaro[0]=new Texture("passaro1.png");
        passaro[1]=new Texture("passaro2.png");
        passaro[2]=new Texture("passaro3.png");
        fundo=new Texture("fundo.png");
        

        larguraDispositivo=Gdx.graphics.getWidth();
        alturaDispositivo=Gdx.graphics.getHeight();

    }

    @Override
    public void render() {
        movimento++;
        batch.begin();
        batch.draw(fundo,0,0, larguraDispositivo,alturaDispositivo);
        batch.draw(passaro[0], movimento, alturaDispositivo/2);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
