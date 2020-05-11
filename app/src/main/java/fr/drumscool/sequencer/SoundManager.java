package fr.drumscool.sequencer;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseArray;

public class SoundManager {

    public static int SOUND_DEFAULT      = 0;
    public static int SOUND_KICK         = 0;
    public static int SOUND_SNARE        = 1;
    public static int SOUND_HHC          = 2;

    public static boolean isSoundTurnedOff;

    private static SoundManager mSoundManager;

    private SoundPool mSoundPool;
    private SparseArray<Integer> mSoundPoolMap;

    public static final int maxSounds = 6;

    public static SoundManager getInstance(Context context)
    {
        if (mSoundManager == null){
            mSoundManager = new SoundManager(context);
        }

        return mSoundManager;
    }

    public SoundManager(Context mContext)
    {
        mSoundPool = new SoundPool(maxSounds, AudioManager.STREAM_MUSIC, 0);

 //       final boolean[] loaded = {false};
 //       mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
 //           public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
 //              loaded[0] = true;
 //           }
 //       });

        mSoundPoolMap = new SparseArray<Integer>();
        mSoundPoolMap.put(SOUND_KICK, mSoundPool.load(mContext, R.raw.kick, 1));
        mSoundPoolMap.put(SOUND_SNARE, mSoundPool.load(mContext, R.raw.snare, 1));
        mSoundPoolMap.put(SOUND_HHC, mSoundPool.load(mContext, R.raw.hhc, 1));

        // testing simultaneous playing
        //int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //mSoundPool.play(mSoundPoolMap.get(0), streamVolume, streamVolume, 1, 20, 1f);
        //mSoundPool.play(mSoundPoolMap.get(1), streamVolume, streamVolume, 1, 2, 1f);
        //mSoundPool.play(mSoundPoolMap.get(2), streamVolume, streamVolume, 1, 0, 1f);


    }

    public void playSound(int index) {
        if (isSoundTurnedOff)
            return;
        mSoundPool.play(mSoundPoolMap.get(index), 1, 1, 1, 0, 1f);
    }

    public static void clear()
    {
        if (mSoundManager != null){
            mSoundManager.mSoundPool = null;
            mSoundManager.mSoundPoolMap = null;
        }
        mSoundManager = null;
    }
}
