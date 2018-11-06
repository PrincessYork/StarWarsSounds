package mobilcorpapp.starwarssound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    private final static String SOUNDS_FOLDER = "my_sounds";
    private final static int MAX_SOUND_THREADS = 1;

    private List<Sound> sounds;
    private SoundPool soundPool;
    private AssetManager assetManager;

    private Context context;

    public Player(Context context) {
        this.context = context;
        assetManager = context.getAssets();
        soundPool = new SoundPool(MAX_SOUND_THREADS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String [] fileNames;
        String [] soundNames = context.getResources().getStringArray(R.array.sound_names);
        try {
            fileNames = assetManager.list(SOUNDS_FOLDER);
        } catch (IOException e) {
            return;
        }

        if (fileNames.length != soundNames.length)
            Toast.makeText(context, "Массивы ресурсов имеют разную длину", Toast.LENGTH_SHORT).show();

        sounds = new ArrayList<>();
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            try {
                String assetPath = SOUNDS_FOLDER + "/" + fileName;
                Sound sound = new Sound(assetPath, soundNames[i]);
                load(sound);
                sounds.add(sound);
            } catch (IOException e) {
                continue;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(sound.getAssetPath());
        int soundID = soundPool.load(fileDescriptor, 1);
        sound.setSoundID(soundID);
    }

    public void play(Sound sound) {
        Integer soundID = sound.getSoundID();
        if (soundID != null) {
            soundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void release() {
        soundPool.release();
    }

    public List<Sound> getSounds() {
        return sounds;
    }
}
