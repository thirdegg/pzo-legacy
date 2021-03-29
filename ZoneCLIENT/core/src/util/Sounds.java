package util;

import GameWorld.Game;
import Unit.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    public static boolean onoffsounds=true;
    private static Sound vistrel, electra,rain,walking,recvibros,radiomiter,esayanomaly,hardanomaly;
    private static Music fonmusic;
    public static boolean isplayrad;

    public Sounds() {
        recvibros = Gdx.audio.newSound(Gdx.files.internal("sounds/recvibros.mp3"));
        vistrel = Gdx.audio.newSound(Gdx.files.internal("sounds/vistrel.mp3"));
        electra = Gdx.audio.newSound(Gdx.files.internal("sounds/electra.mp3"));
        rain = Gdx.audio.newSound(Gdx.files.internal("sounds/dojd.mp3"));
        walking = Gdx.audio.newSound(Gdx.files.internal("sounds/walking.mp3"));
        radiomiter = Gdx.audio.newSound(Gdx.files.internal("sounds/radiomiter.mp3"));
        esayanomaly = Gdx.audio.newSound(Gdx.files.internal("sounds/esayanomaly.mp3"));
        hardanomaly = Gdx.audio.newSound(Gdx.files.internal("sounds/hardanomaly.mp3"));
        fonmusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/fonmusic.mp3"));
        fonmusic.setLooping(true);
        fonmusic.setVolume(0.6f);
    }
    public static void soundOn(){onoffsounds=true;if(Game.rain)playRain();}
    public static void soundOff(){onoffsounds=false;stopRain();}

    public static void playVistrel() {if (onoffsounds) vistrel.play();
    }
    public static void playRecVibros() {if (onoffsounds) recvibros.play(0.3f);
    }
    public static void playElectra() {if (onoffsounds) electra.play();
    }
    public static void playRain(){if (onoffsounds) rain.loop(0.5f);
    }
    public static void stopRain(){rain.stop();
    }
    public static void playFonMusic(){if (onoffsounds)fonmusic.play();
    }
    public static void stopFonMusic(){fonmusic.pause();
    }
    public static void playWalking(){if (onoffsounds) walking.loop(0.25f);
    }
    public static void stopWalking(){walking.stop();
    }

    public static void playRadiomiter(){if (onoffsounds) radiomiter.loop(0.5f);
        isplayrad=true;}
    public static void stopRadiomiter(){radiomiter.stop();isplayrad=false;
    }
    public static void playEsayAnomaly(){if (onoffsounds) esayanomaly.loop(0.5f);
    }
    public static void stopEsayAnomaly(){esayanomaly.stop();
    }
    public static void playHardAnomaly(){if (onoffsounds) hardanomaly.loop(0.5f);
    }
    public static void stopHardAnomaly(){hardanomaly.stop();
    }

}
