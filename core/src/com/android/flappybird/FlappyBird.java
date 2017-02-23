package com.android.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.logging.StreamHandler;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoAlto;
    private Random numerosRandomico;

    private int larguraDispositivo;
    private int alturaDispositivo;
    private float variacao=0;
    private float velocidadeQueda=0;
    private float posicaoVertical=0;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoCanos;
    private float alturaEntreCanosRandomica;
    private int estadoJogo=0;
    private BitmapFont fonte;
    private int pontuacao=0;
    private boolean marcouPonto;

    @Override
    public void create() {
        batch = new SpriteBatch();
        passaros=new Texture[3];
        passaros[0]=new Texture("passaro1.png");
        passaros[1]=new Texture("passaro2.png");
        passaros[2]=new Texture("passaro3.png");
        fundo=new Texture("fundo.png");
        canoBaixo=new Texture("cano_baixo_maior.png");
        canoAlto=new Texture("cano_topo_maior.png");
        numerosRandomico=new Random();
        fonte=new BitmapFont();

        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);
        larguraDispositivo=Gdx.graphics.getWidth();
        alturaDispositivo=Gdx.graphics.getHeight();
        posicaoVertical=alturaDispositivo/2;
        posicaoMovimentoCanoHorizontal=larguraDispositivo-100;
        espacoCanos=300;

    }

    @Override
    public void render() {

        variacao = variacao + Gdx.graphics.getDeltaTime() * 10;
        if (variacao > 2) {
            variacao = 0;
        }


        if(estadoJogo==0){
            if(Gdx.input.justTouched()){
                estadoJogo=1;
            }
        } else {


            posicaoMovimentoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 300;
            velocidadeQueda++;

            if (posicaoMovimentoCanoHorizontal < -canoBaixo.getWidth()) {
                posicaoMovimentoCanoHorizontal = larguraDispositivo;
                alturaEntreCanosRandomica = numerosRandomico.nextInt(400) - 200;
                marcouPonto=false;
            }

            if (Gdx.input.justTouched()) {
                velocidadeQueda = -20;
            }


            if (posicaoVertical > 0 || velocidadeQueda < 0) {
                posicaoVertical -= velocidadeQueda / 1.25;
            }

            if(posicaoMovimentoCanoHorizontal<120){
                if(!marcouPonto){
                    marcouPonto=true;
                    pontuacao++;
                }
            }

        }
        batch.begin();
        batch.draw(fundo,0,0, larguraDispositivo,alturaDispositivo);
        batch.draw(canoAlto,posicaoMovimentoCanoHorizontal,alturaDispositivo/2+espacoCanos/2+alturaEntreCanosRandomica);
        batch.draw(canoBaixo,posicaoMovimentoCanoHorizontal,alturaDispositivo/2-canoBaixo.getHeight()-espacoCanos/2+alturaEntreCanosRandomica);
        batch.draw(passaros[(int) variacao], 180, posicaoVertical);
        fonte.draw(batch, String.valueOf(pontuacao),larguraDispositivo/2,alturaDispositivo-50);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
