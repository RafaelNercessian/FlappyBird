package com.android.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoAlto;
    private Texture gameOver;
    private Random numerosRandomico;
    private BitmapFont mensagem;

    private float larguraDispositivo;
    private float alturaDispositivo;
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
    private Circle passaroCirculo;
    private Rectangle retanguloCanoTopo;
    private Rectangle retanguloCanoBaixo;
    private ShapeRenderer shape;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH=768;
    private final float VIRTUAL_HEIGHT=1024;

    @Override
    public void create() {
        batch = new SpriteBatch();
        passaros=new Texture[3];
        passaros[0]=new Texture("passaro1.png");
        passaros[1]=new Texture("passaro2.png");
        passaros[2]=new Texture("passaro3.png");
        fundo=new Texture("fundo.png");
        gameOver=new Texture("game_over.png");
        canoBaixo=new Texture("cano_baixo_maior.png");
        canoAlto=new Texture("cano_topo_maior.png");
        numerosRandomico=new Random();
        fonte=new BitmapFont();
        passaroCirculo=new Circle();
        mensagem=new BitmapFont();

        camera=new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH/2,VIRTUAL_HEIGHT/2,0);
        viewport=new StretchViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera);

        mensagem.setColor(Color.WHITE);
        mensagem.getData().setScale(3);

        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);
        larguraDispositivo=VIRTUAL_WIDTH;
        alturaDispositivo=VIRTUAL_HEIGHT;
        posicaoVertical=alturaDispositivo/2;
        posicaoMovimentoCanoHorizontal=larguraDispositivo-100;
        espacoCanos=300;

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void render() {

        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        variacao = variacao + Gdx.graphics.getDeltaTime() * 10;
        if (variacao > 2) {
            variacao = 0;
        }


        if(estadoJogo==0){
            if(Gdx.input.justTouched()){
                estadoJogo=1;
            }
        } else {

            velocidadeQueda++;
            if (posicaoVertical > 0 || velocidadeQueda < 0) {
                posicaoVertical -= velocidadeQueda / 1.25;
            }


            if(estadoJogo==1){
                posicaoMovimentoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 300;

                if (Gdx.input.justTouched()) {
                    velocidadeQueda = -20;
                }

                if (posicaoMovimentoCanoHorizontal < -canoBaixo.getWidth()) {
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                    alturaEntreCanosRandomica = numerosRandomico.nextInt(400) - 200;
                    marcouPonto=false;
                }

                if(posicaoMovimentoCanoHorizontal<120){
                    if(!marcouPonto){
                        marcouPonto=true;
                        pontuacao++;
                    }
                }

            } else{

                if(Gdx.input.justTouched()){
                    pontuacao=0;
                    estadoJogo=0;
                    velocidadeQueda=0;
                    posicaoVertical=alturaDispositivo/2;
                    posicaoMovimentoCanoHorizontal=larguraDispositivo;
                }

            }





        }

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(fundo,0,0, larguraDispositivo,alturaDispositivo);
        batch.draw(canoAlto,posicaoMovimentoCanoHorizontal,alturaDispositivo/2+espacoCanos/2+alturaEntreCanosRandomica);
        batch.draw(canoBaixo,posicaoMovimentoCanoHorizontal,alturaDispositivo/2-canoBaixo.getHeight()-espacoCanos/2+alturaEntreCanosRandomica);
        batch.draw(passaros[(int) variacao], 180, posicaoVertical);
        fonte.draw(batch, String.valueOf(pontuacao),larguraDispositivo/2,alturaDispositivo-50);
        if(estadoJogo==2){
            batch.draw(gameOver,larguraDispositivo/2-gameOver.getWidth()/2,alturaDispositivo/2);
            mensagem.draw(batch,"Toque para reiniciar",larguraDispositivo/2-200,alturaDispositivo/2-gameOver.getHeight()/2);
        }
        batch.end();


        passaroCirculo.set(180+passaros[0].getWidth()/2,posicaoVertical+passaros[0].getHeight()/2,30);

        retanguloCanoBaixo=new Rectangle(posicaoMovimentoCanoHorizontal,alturaDispositivo/2-canoBaixo.getHeight()-espacoCanos/2+alturaEntreCanosRandomica,canoBaixo.getWidth(),canoBaixo.getHeight());

        retanguloCanoTopo=new Rectangle(posicaoMovimentoCanoHorizontal,alturaDispositivo/2+espacoCanos/2+alturaEntreCanosRandomica,canoAlto.getWidth(),canoAlto.getHeight());

        //Teste de colisão
        if(Intersector.overlaps(passaroCirculo,retanguloCanoBaixo) || Intersector.overlaps(passaroCirculo,retanguloCanoTopo) || posicaoVertical<=0 || posicaoVertical>=alturaDispositivo){
           estadoJogo=2;
        }


    }

    @Override
    public void dispose() {

    }
}
