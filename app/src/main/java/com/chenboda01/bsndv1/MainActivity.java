package com.chenboda01.bsndv1;

import android.app.*;import android.os.*;import android.graphics.*;import android.media.*;import android.view.*;import android.widget.*;

public class MainActivity extends Activity{
    ToneGenerator tone;
    LinearLayout root;
    int volume=80;
    TextView title,status;
    public void onCreate(Bundle b){super.onCreate(b);tone=new ToneGenerator(AudioManager.STREAM_MUSIC,100);build();}
    void build(){
        ScrollView sv=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(20,20,20,20);root.setBackgroundColor(Color.rgb(7,19,30));sv.addView(root);
        title=tv("B-Soundboard V1",31,Color.WHITE,true);status=tv("Tap buttons for sounds",16,Color.rgb(80,255,157),false);root.addView(title);root.addView(status);
        SeekBar vol=new SeekBar(this);vol.setMax(100);vol.setProgress(volume);vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){public void onProgressChanged(SeekBar s,int p,boolean f){volume=p;status.setText("Volume "+p+"%");}public void onStartTrackingTouch(SeekBar s){}public void onStopTrackingTouch(SeekBar s){}});root.addView(vol,new LinearLayout.LayoutParams(-1,70));
        addRow("Beep",ToneGenerator.TONE_PROP_BEEP,"Boop",ToneGenerator.TONE_PROP_BEEP2);
        addRow("Error",ToneGenerator.TONE_PROP_NACK,"OK",ToneGenerator.TONE_PROP_ACK);
        addRow("Busy",ToneGenerator.TONE_SUP_BUSY,"Ring",ToneGenerator.TONE_SUP_RINGTONE);
        addRow("Dial",ToneGenerator.TONE_DTMF_1,"Pound",ToneGenerator.TONE_DTMF_P);
        addRow("Zero",ToneGenerator.TONE_DTMF_0,"Nine",ToneGenerator.TONE_DTMF_9);
        addRow("Long",ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,"Alert",ToneGenerator.TONE_CDMA_ABBR_ALERT);
        root.addView(tv("V1 uses built-in Android tones, so it works without audio files.",14,Color.LTGRAY,false));setContentView(sv);
    }
    void addRow(String a,int ta,String b,int tb){LinearLayout r=row();r.addView(btn(a,v->play(ta,a)));r.addView(btn(b,v->play(tb,b)));root.addView(r);}    
    void play(int toneId,String name){try{tone.startTone(toneId,300);status.setText("Played: "+name);}catch(Exception e){status.setText("Sound failed");}}
    TextView tv(String s,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.CENTER);t.setPadding(4,8,4,8);if(bold)t.setTypeface(null,1);return t;}
    Button btn(String s,View.OnClickListener l){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextSize(18);b.setOnClickListener(l);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,70,1);p.setMargins(7,7,7,7);b.setLayoutParams(p);return b;}
    LinearLayout row(){LinearLayout r=new LinearLayout(this);r.setOrientation(LinearLayout.HORIZONTAL);return r;}
    protected void onDestroy(){super.onDestroy();try{tone.release();}catch(Exception e){}}
}
