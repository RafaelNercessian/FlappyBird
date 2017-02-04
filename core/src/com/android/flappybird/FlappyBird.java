package com.android.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;

    private int larguraDispositivo;
    private int alturaDispositivo;
    private float variacao=0;
    private float velocidadeQueda=0;
    private float posicaoVertical=0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        passaros=new Texture[3];
        passaros[0]=new Texture("passaro1.png");
        passaros[1]=new Texture("passaro2.png");
        passaros[2]=new Texture("passaro3.png");
        fundo=new Texture("fundo.png");


        larguraDispositivo=Gdx.graphics.getWidth();
        alturaDispositivo=Gdx.graphics.getHeight();
        posicaoVertical=alturaDispositivo/2;

    }

    @Override
    public void render() {
        variacao=variacao+Gdx.graphics.getDeltaTime()*10;
        velocidadeQueda++;
        if(variacao>2){
            variacao=0;
        }


        if(Gdx.input.justTouched()){
            velocidadeQueda=-20;
        }


        if(posicaoVertical>0 || velocidadeQueda<0) {
            posicaoVertical -= velocidadeQueda/1.25;
        }

        batch.begin();
        batch.draw(fundo,0,0, larguraDispositivo,alturaDispositivo);
        batch.draw(passaros[(int) variacao], 30, posicaoVertical);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
