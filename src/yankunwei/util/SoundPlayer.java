package yankunwei.util;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private Map<String, byte[]> soundsByteArray;
    private Clip BGM;
    private static final String path = "/resources/sounds/";
    private static final SoundPlayer player = new SoundPlayer();

    public static SoundPlayer getInstant(){
        return player;
    }

    private SoundPlayer() {
        soundsByteArray = new HashMap<>();
        soundsByteArray.put("BGM", initAudioByteArray(ToolHelper.getInstant().getURL(path + "BGM.wav")));
        soundsByteArray.put("Eat", initAudioByteArray(ToolHelper.getInstant().getURL(path + "Eat.wav")));
        BGM = getAudioClip("BGM");
    }

    public Clip playBGM() {
        this.BGM = this.playSound("BGM", true, 10);
        return this.BGM;
    }

    public void stopBGM() {
        this.stopSound(this.BGM);
    }

    public Clip getBGM() {
        return BGM;
    }

    public boolean setBGM(Clip BGM) {
        if (BGM != null) {
            this.BGM.close();
            this.BGM = BGM;
            return true;
        }
        return false;
    }

    private byte[] initAudioByteArray(URL url) {
        int read;
        byte[] audio;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            while ((read = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        audio = out.toByteArray();
        return audio;
    }

    public byte[] getAudioByteArray(String soundName) {
        return soundsByteArray.get(soundName);
    }

    public Clip getAudioClip(String soundName) {
        byte[] audioArray = this.getAudioByteArray(soundName);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioArray);
        AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
        AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, audioArray.length);
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clip;
    }

    public Clip playSound(String soundName) {
        return this.playSound(soundName, false, 50);
    }

    public Clip playSound(String soundName, int volumn) {
        return this.playSound(soundName, false, volumn);
    }

    public Clip playSound(String soundName, boolean repeat, int volumn) {
        Clip sound = getAudioClip(soundName);
        if (sound != null){
            this.setVolume(sound, volumn);
            sound.start();
            if (repeat) {
                sound.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            throw new IllegalArgumentException("Sound don't exist");
        }
        return sound;
    }

    public void stopSound(Clip sound) {
        sound.close();
    }

    public void setVolume(Clip sound, double volume) {
        if (sound != null) {
            FloatControl controller = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = (float) (volume - 50);
            System.out.println("V: " + gain);
            controller.setValue(gain);
        } else {
            throw new IllegalArgumentException("Sound Clip don't exist,can't adjust volume");
        }
    }
}
