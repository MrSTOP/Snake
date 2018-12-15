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
        soundsByteArray.put("BGM", initAudioByteArray(this.getURL("Alarm01.wav")));
        soundsByteArray.put("S2", initAudioByteArray(this.getURL("Alarm02.wav")));
        soundsByteArray.put("S3", initAudioByteArray(this.getURL("Alarm03.wav")));
        soundsByteArray.put("S4", initAudioByteArray(this.getURL("Alarm04.wav")));
        soundsByteArray.put("S5", initAudioByteArray(this.getURL("Alarm05.wav")));
        soundsByteArray.put("S6", initAudioByteArray(this.getURL("Alarm06.wav")));
        soundsByteArray.put("S7", initAudioByteArray(this.getURL("Alarm07.wav")));
        soundsByteArray.put("S8", initAudioByteArray(this.getURL("Alarm08.wav")));
        soundsByteArray.put("S9", initAudioByteArray(this.getURL("Alarm09.wav")));
        soundsByteArray.put("S10", initAudioByteArray(this.getURL("Alarm10.wav")));
        soundsByteArray.put("C", initAudioByteArray(this.getURL("C.wav")));
        BGM = getAudioClip("BGM");
    }

    private URL getURL(String fileName) {
        return this.getClass().getResource(path + fileName);
    }

    public Clip getBGM() {
        return BGM;
    }

    public boolean setBGM(Clip BGM) {
        if (BGM != null) {
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
        return this.playSound(soundName, false);
    }

    public Clip playSound(String soundName, boolean repeat) {
        Clip sound = getAudioClip(soundName);
        if (sound != null){
            sound.start();
            if (repeat) {
                sound.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            System.out.println("Sound don't exist");
        }
        return sound;
    }

    public void stopSound(Clip sound) {
        sound.close();
    }

    public void setVolume(Clip sound, String soundName, int volume) {
        if (sound != null) {
            FloatControl controller = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = (float) Math.log10(volume / 100);
            controller.setValue(gain);
        } else {
            System.out.println("Sound Clip don't exist,can't adjust volume");
        }
    }
}
