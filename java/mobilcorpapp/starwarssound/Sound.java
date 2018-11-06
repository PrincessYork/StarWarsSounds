package mobilcorpapp.starwarssound;

public class Sound {
    private String assetPath;
    private String name;
    private Integer soundID;

    public Sound(String assetPath, String name) {
        this.assetPath = assetPath;
        this.name = name;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public String getName() {
        return name;
    }

    public Integer getSoundID() {
        return soundID;
    }

    public void setSoundID(Integer soundID) {
        this.soundID = soundID;
    }
}
