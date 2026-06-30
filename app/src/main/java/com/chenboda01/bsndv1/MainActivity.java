package com.chenboda01.bsndv1;

import android.app.*;import android.os.*;import android.graphics.*;import android.graphics.drawable.*;import android.media.*;import android.view.*;import android.widget.*;

public class MainActivity extends Activity{
    ToneGenerator tone; LinearLayout root,grid; TextView status,volText; int volume=80;
    public void onCreate(Bundle b){super.onCreate(b);tone=new ToneGenerator(AudioManager.STREAM_MUSIC,100);build();}
    void build(){
        ScrollView sv=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(18),dp(18),dp(18));root.setBackgroundColor(Color.rgb(7,19,30));sv.addView(root);
        TextView title=txt("B-Sounds V1.2",34,Color.WHITE,true);TextView sub=txt("Clean soundboard",15,Color.LTGRAY,false);status=txt("Ready",18,Color.rgb(80,255,157),true);
        root.addView(title);root.addView(sub);root.addView(status);
        LinearLayout card=card();volText=txt("Volume 80%",17,Color.WHITE,true);SeekBar vol=new SeekBar(this);vol.setMax(100);vol.setProgress(volume);vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){public void onProgressChanged(SeekBar s,int p,boolean f){volume=p;volText.setText("Volume "+p+"%");}public void onStartTrackingTouch(SeekBar s){}public void onStopTrackingTouch(SeekBar s){}});card.addView(volText);card.addView(vol,new LinearLayout.LayoutParams(-1,dp(55)));root.addView(card);
        grid=new LinearLayout(this);grid.setOrientation(LinearLayout.VERTICAL);root.addView(grid);
        addRow("Beep",ToneGenerator.TONE_PROP_BEEP,"Boop",ToneGenerator.TONE_PROP_BEEP2);
        addRow("Error",ToneGenerator.TONE_PROP_NACK,"OK",ToneGenerator.TONE_PROP_ACK);
        addRow("Busy",ToneGenerator.TONE_SUP_BUSY,"Ring",ToneGenerator.TONE_SUP_RINGTONE);
        addRow("Dial",ToneGenerator.TONE_DTMF_1,"Pound",ToneGenerator.TONE_DTMF_P);
        addRow("Zero",ToneGenerator.TONE_DTMF_0,"Nine",ToneGenerator.TONE_DTMF_9);
        addRow("Long",ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,"Alert",ToneGenerator.TONE_CDMA_ABBR_ALERT);
        root.addView(txt("Built-in Android tones. No internet. No audio files needed.",14,Color.LTGRAY,false));setContentView(sv);
    }
    LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(14),dp(12),dp(14),dp(12));l.setBackground(bg(Color.rgb(19,40,56),24));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,dp(12),0,dp(12));l.setLayoutParams(p);return l;}
    void addRow(String a,int ta,String b,int tb){LinearLayout r=new LinearLayout(this);r.setOrientation(LinearLayout.HORIZONTAL);r.addView(soundBtn(a,ta));r.addView(soundBtn(b,tb));grid.addView(r);}    
    Button soundBtn(String name,int toneId){Button b=new Button(this);b.setText(name);b.setTextSize(20);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setBackground(bg(Color.rgb(32,68,90),26));b.setOnClickListener(v->play(toneId,name));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,dp(92),1);p.setMargins(dp(7),dp(7),dp(7),dp(7));b.setLayoutParams(p);return b;}
    void play(int toneId,String name){try{tone.startTone(toneId,260);status.setText("Played: "+name);}catch(Exception e){status.setText("Sound failed");}}
    TextView txt(String s,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.CENTER);t.setPadding(4,8,4,8);if(bold)t.setTypeface(null,1);return t;}
    GradientDrawable bg(int color,int rad){GradientDrawable g=new GradientDrawable();g.setColor(color);g.setCornerRadius(dp(rad));return g;}
    int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}    
    protected void onDestroy(){super.onDestroy();try{tone.release();}catch(Exception e){}}
}
