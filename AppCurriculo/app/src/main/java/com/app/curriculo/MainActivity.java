package com.app.curriculo;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;
import static java.lang.String.valueOf;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.print.PdfConverter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.android.identity.document.Document;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.kal.rackmonthpicker.BuildConfig;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import org.w3c.dom.Text;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;


public class MainActivity extends AppCompatActivity {
    TextInputEditText nome;
    TextInputEditText cargo;
    TextInputEditText numero;
    TextInputEditText email;
    TextInputEditText sobreMim;
    TextInputEditText competencias;
    TextInputEditText informacoesAd;

    ArrayList redeLayoutList = new ArrayList<LinearLayout>();
    Integer redeQtd = 0;
    ArrayList redeIndexList = new ArrayList<TextView>();
    BooVariable redeChange = new BooVariable();
    boolean redeApagar = false;
    int redeApagarIndex = -1;

    ArrayList profLayoutList = new ArrayList<LinearLayout>();
    Integer profQtd = 0;
    ArrayList profIndexList = new ArrayList<TextView>();
    BooVariable profChange = new BooVariable();
    boolean profApagar = false;
    int profApagarIndex = -1;

    ArrayList acadLayoutList = new ArrayList<LinearLayout>();
    Integer acadQtd = 0;
    ArrayList acadIndexList = new ArrayList<TextView>();
    BooVariable acadChange = new BooVariable();
    boolean acadApagar = false;
    int acadApagarIndex = -1;

    final static int APP_STORAGE_ACCESS_REQUEST_CODE = 501; // Any value

    PdfConverter converter = PdfConverter.getInstance();
    InterstitialAd interstitialAd;

    public File generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            //Toast.makeText(context, "Curriculo criado em Downloads", Toast.LENGTH_SHORT).show();
            return gpxfile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void salvarHTML(SharedPreferences.Editor editor, SharedPreferences prefs, boolean paraWebView) {
        RadioButton radioPadrao = findViewById(R.id.padrao);
        RadioButton radioPago1 = findViewById(R.id.pago1);
        RadioButton radioPago2 = findViewById(R.id.pago2);

        if (radioPadrao.isChecked()) {
            String s = "<!DOCTYPE html> <html> <head> <style>";

            if (paraWebView == false) {
                s += "* {       box-sizing: border-box;       margin: 0;       padding: 0; }      body {       font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;       background-color: #e0e0e0;       color: #222;       padding: 40px;     }      .container {       max-width: 800px;       margin: auto;       background-color: #fff;       padding: 40px 50px;       border-radius: 10px;       box-shadow: 0 0 10px rgba(0,0,0,0.1);     }      h1 {       font-size: 32px;       margin-bottom: 5px;     }      .social {       font-size: 14px;       color: #555;       margin-bottom: 30px;     }      .line {       height: 2px;       background-color: #ccc;       margin: 20px 0;     }      .section {       margin-bottom: 30px;     }      .section-title {       font-weight: bold;       font-size: 18px;       margin-bottom: 10px;       color: #003c8f;     }      .section-content {       font-size: 15px;       line-height: 1.8;       margin-top: 5px;     }      .section-content p {       margin-bottom: 10px;     }      .section-content br {       line-height: 2;     }  ";
            }
            else{
                s += "* {       box-sizing: border-box;       margin: 0;       padding: 0; }      body {       font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;       background-color: #e0e0e0;       color: #222;       padding: 20px;     }      .container {       max-width: 800px;       margin: auto;       background-color: #fff;       padding: 20px 25px;       border-radius: 10px;       box-shadow: 0 0 10px rgba(0,0,0,0.1);     }      h1 {       font-size: 16px;       margin-bottom: 5px;     }      .social {       font-size: 7px;       color: #555;       margin-bottom: 15px;     }      .line {       height: 2px;       background-color: #ccc;       margin: 10px 0;     }      .section {       margin-bottom: 15px;     }      .section-title {       font-weight: bold;       font-size: 9px;       margin-bottom: 10px;       color: #003c8f;     }      .section-content {       font-size: 7px;       line-height: 1.8;       margin-top: 5px;     }      .section-content p {       margin-bottom: 5px;     }      .section-content br {       line-height: 2;     }  ";
            }
            s+="</style></head><body>   <div class='container'>     <h1>" + prefs.getString("NOME", "") + "</h1>     " +
                    "<h1>" + prefs.getString("CARGO", "") + "</h1>" +
                    "<h1>" + prefs.getString("NUMERO", "") + "</h1>" +
                    "<h1>" + prefs.getString("EMAIL", "") + "</h1>" +
                    "<div class='social'>Redes sociais: <br>";

            for (int i = 1; i <= prefs.getInt("REDEQTD", 0); i++) {
                s += prefs.getString("SITE" + i, "") + ": " + prefs.getString("LINK" + i, "") + "<br>";
            }
            s += "</div>     <div class='line'></div>      <div class='section'>       " +
                    "<div class='section-title'>Informações sobre mim</div>       <div class='section-content'>";
            s += prefs.getString("SOBREMIM", "");

            s += "</div>     </div>";

            if (prefs.getInt("PROFQTD", 0) > 0) {
                s += "<div class='section'><div class='section-title'>Experiência profissional</div>       ";

                for (int i = 1; i <= prefs.getInt("PROFQTD", 0); i++) {
                    s += "<div class='section-content'>" + prefs.getString("PROFEMPRESA" + i, "") + "<br><small>";


                    if (!prefs.getString("PROFCOMECOMONTH" + i, "").equals("")) {
                        s += prefs.getString("PROFCOMECOMONTH" + i, "") + "/" + prefs.getString("PROFCOMECOYEAR" + i, "") + " - ";
                    }

                    if (!prefs.getString("PROFFINALMONTH" + i, "").equals("")) {
                        if (prefs.getInt("PROFEMPREGOATUAL" + i, 0) != 1) {
                            s += prefs.getString("PROFFINALMONTH" + i, "") + "/" + prefs.getString("PROFCOMECOYEAR" + i, "");

                        }
                    }

                    s += "</small><br><small>" + prefs.getString("PROFDESCRICAO" + i, "") + "</small></div>     ";
                }

                s+= "</div>";
            }

            if (prefs.getInt("ACADQTD", 0) > 0) {
                s += "<div class='section'><div class='section-title'>Formação acadêmica</div>       ";

                for (int i = 1; i <= prefs.getInt("ACADQTD", 0); i++) {
                    s += "<div class='section-content'>" + prefs.getString("ACADEMPRESA" + i, "") + "<br><small>";

                    if (!prefs.getString("ACADCOMECOMONTH" + i, "").equals("")) {
                        s += prefs.getString("ACADCOMECOMONTH" + i, "") + "/" + prefs.getString("ACADCOMECOYEAR" + i, "") + " - ";
                    }

                    if (!prefs.getString("ACADFINALMONTH" + i, "").equals("")) {
                        if (prefs.getInt("ACADEMPREGOATUAL" + i, 0) != 1) {
                            s += prefs.getString("ACADFINALMONTH" + i, "") + "/" + prefs.getString("ACADFINALYEAR" + i, "");

                        }
                    }

                    s += "</small><br><small>" + prefs.getString("ACADDESCRICAO" + i, "") + "</small></div>     ";
                }

                s += "</div>";
            }


            if (!prefs.getString("COMPETENCIAS", "").equals("")) {
                s += "   <div class='section'><div class='section-title'>Competências</div>       <div class='section-content'>";
                s += prefs.getString("COMPETENCIAS", "") + "</div>     </div>      ";
            }

            if (!prefs.getString("INFORMACOESAD", "").equals("")) {
                s += "   <div class='section'><div class='section-title'>Informações adicionais</div>       <div class='section-content'>";
                s += prefs.getString("INFORMACOESAD", "") + "</div>     </div>      ";
            }

            s += "</div> </body> </html>";

            editor.putString("CURRICULOHTML", s);
            editor.apply();
        }

        if (radioPago1.isChecked()) {
            String s = "<!DOCTYPE html><html><head><style>";

            if (paraWebView == false) {
                s += "* {      box-sizing: border-box;    }    body {    margin: 0;      font-family: Arial, sans-serif;    }    .container { padding-left: 150px; background-color: #003c8f;  width: 100%;  }    .data {  color: white;  font-size: 14px;  position: relative;  right: 150px;  bottom: 17px; height: 0px;}    .main {  width: 100%;      background-color: #e0e0e0;      padding: 40px 30px;      color: #000; height: 100%;    }    h1 {      margin-top: 0;      font-size: 28px;    }    .section {      margin-bottom: 30px;    }    .section-title {      font-weight: bold;      font-size: 18px;      margin-bottom: 10px;    }.section-content {      flex: left; max-width: 10px; text-wrap: nowrap;    }    .text-muted {      color: #444;    }.conteudo{ margin-bottom: 30px;}";
            }
            else{
                s += "* {      box-sizing: border-box;    }    body { font-size: 8px;    margin: 0;      font-family: Arial, sans-serif;    }    .container { padding-left: 75px; background-color: #003c8f;  width: 100%;  }    .data {  color: white;  font-size: 7px;  position: relative;  right: 82px;  bottom: 8px; height: 0px;}    .main {  width: 100%;      background-color: #e0e0e0;      padding: 20px 15px;      color: #000; height: 100%;    }    h1 {      margin-top: 0;      font-size: 14px;    }    .section {      margin-bottom: 15px;    }    .section-title {      font-weight: bold;      font-size: 9px;      margin-bottom: 5px;    }.section-content {      flex: left; max-width: 10px; text-wrap: nowrap;    }    .text-muted {      color: #444;    }.conteudo{ margin-bottom: 15px;}";
            }
            s+= "</style></head><body>  <div class='container'>    ";

            s += "<div class='main'>      <h1>" + prefs.getString("NOME", "") + "</h1>"
                    + prefs.getString("CARGO", "") + "<br>"
                    + prefs.getString("NUMERO", "") + "<br>"
                    + prefs.getString("EMAIL", "") + "<div><br>Redes Sociais:<br>";

            for (int i = 1; i <= prefs.getInt("REDEQTD", 0); i++) {
                s += prefs.getString("SITE" + i, "") + ": " + prefs.getString("LINK" + i, "") + "<br>";
            }

            s += "<br></div>      ";
            s += "<div class='section'>        <div class='section-title'>Informações sobre mim</div>        <div class='text-muted'>";
            s += prefs.getString("SOBREMIM", "");
            s += "</div>      </div>";

            if (prefs.getInt("PROFQTD", 0) > 0) {
                s += "<div class='section'><div class='section-title'>Experiência profissional</div>       ";

                for (int i = 1; i <= prefs.getInt("PROFQTD", 0); i++) {

                    s += "<span class 'section-content'>" + prefs.getString("PROFEMPRESA" + i, "") + "</span>";

                    s += "<div class='data'>";
                    if (!prefs.getString("PROFCOMECOMONTH" + i, "").equals("")) {
                        s += prefs.getString("PROFCOMECOMONTH" + i, "") + "/" + prefs.getString("PROFCOMECOYEAR" + i, "") + " - ";
                    }

                    if (!prefs.getString("PROFFINALMONTH" + i, "").equals("")) {
                        if (prefs.getInt("PROFEMPREGOATUAL" + i, 0) != 1) {
                            s += prefs.getString("PROFFINALMONTH" + i, "") + "/" + prefs.getString("PROFCOMECOYEAR" + i, "");

                        }
                    }
                    s += "</div>";

                    s += "<div class='conteudo'>" + prefs.getString("PROFDESCRICAO" + i, "") + "</div>";
                }

                s+= "</div>";
            }

            if (prefs.getInt("ACADQTD", 0) > 0) {
                s += "<div class='section'><div class='section-title'>Formação acadêmica</div>       ";

                for (int i = 1; i <= prefs.getInt("ACADQTD", 0); i++) {
                    s+= "<span class 'section-content'>" + prefs.getString("ACADEMPRESA" + i, "") + "</span>";

                    s += "<div class='data'>";
                    if (!prefs.getString("ACADCOMECOMONTH" + i, "").equals("")) {
                        s += prefs.getString("ACADCOMECOMONTH" + i, "") + "/" + prefs.getString("ACADCOMECOYEAR" + i, "") + " - ";
                    }

                    if (!prefs.getString("ACADFINALMONTH" + i, "").equals("")) {
                        if (prefs.getInt("ACADEMPREGOATUAL" + i, 0) != 1) {
                            s += prefs.getString("ACADFINALMONTH" + i, "") + "/" + prefs.getString("ACADFINALYEAR" + i, "");

                        }
                    }
                    s += "</div>";

                    s += "<div class='conteudo'>" + prefs.getString("ACADDESCRICAO" + i, "") + "</div>";
                }

                s+= "</div>";
            }

            if (!prefs.getString("COMPETENCIAS", "").equals("")) {
                s += "   <div class='section'><div class='section-title'>Competências</div>       <div>";
                s += prefs.getString("COMPETENCIAS", "") + "</div>     </div>      ";
            }

            if (!prefs.getString("INFORMACOESAD", "").equals("")) {
                s += "   <div class='section'><div class='section-title'>Informações adicionais</div>       <div>";
                s += prefs.getString("INFORMACOESAD", "") + "</div>     </div>      ";
            }

            s += "</div>";

            s +="</div>  </div></body></html>";

            editor.putString("CURRICULOHTML", s);
            editor.apply();
        }

        if (radioPago2.isChecked()) {

        }
    }

    public void createRede(LinearLayout redesLayout, SharedPreferences.Editor editor, SharedPreferences prefs, String site, String link, boolean start){
        LinearLayout redeLayout = new LinearLayout(redesLayout.getContext());
        redeLayout.setOrientation(LinearLayout.VERTICAL);

        redeLayoutList.add(redeLayout);

        redeQtd = redeLayoutList.indexOf(redeLayout)+1;

        if (!start) {
            editor.putInt("REDEQTD", redeQtd);
            editor.apply();
        }

        // Parametros
        LinearLayout.LayoutParams padraoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        padraoParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                0);

        LinearLayout.LayoutParams indexParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        indexParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),0);

        LinearLayout.LayoutParams outroParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        outroParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics())
        );


        // Index
        TextView text = new TextView(redeLayout.getContext());
        text.setLayoutParams(indexParams);
        text.setText(redeQtd.toString());
        text.setGravity(Gravity.CENTER_HORIZONTAL);
        redeIndexList.add(text);
        redeChange.setListener(new BooVariable.ChangeListener() {
            @Override
            public void onChange() {
                for (int i = 0; i < redeIndexList.size(); i++) {
                    TextView txt = (TextView) redeIndexList.get(i);
                    txt.setText(valueOf(i+1));

                    if (redeApagar) {
                        if (redeApagarIndex <= i+1){
                            editor.putString("SITE" + (i+1), prefs.getString("SITE" + (i + 2), ""));
                            editor.apply();
                            editor.putString("LINK" + (i+1), prefs.getString("LINK" + (i + 2), ""));
                            editor.apply();
                        }
                    }
                }

                redeApagar = false;
            }
        });

        redeLayout.addView(text);


        // Ícones
        LinearLayout icones = new LinearLayout(redeLayout.getContext());
        icones.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams iconesParams = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(150,150));

        ImageView image1 = new ImageView(redeLayout.getContext());
        image1.setImageResource(R.drawable.angle_small_up);
        image1.setLayoutParams(iconesParams);
        image1.setOnClickListener(b -> {
            int indexLayout = redeLayoutList.indexOf(redeLayout);

            if ((indexLayout-1) != -1) {
                String tempSite = prefs.getString("SITE" + (indexLayout+1), "");
                String tempLink = prefs.getString("LINK" + (indexLayout+1), "");

                editor.putString("SITE" + (indexLayout+1), prefs.getString("SITE" + indexLayout, ""));
                editor.apply();
                editor.putString("LINK" + (indexLayout+1), prefs.getString("LINK" + indexLayout, ""));
                editor.apply();
                editor.putString("SITE" + indexLayout, tempSite);
                editor.apply();
                editor.putString("LINK" + indexLayout, tempLink);
                editor.apply();

                Collections.swap(redeLayoutList, indexLayout, indexLayout - 1);
                Collections.swap(redeIndexList, indexLayout, indexLayout - 1);

                redesLayout.removeAllViews();

                for (int i = 0; i < redeLayoutList.size(); i++) {
                    LinearLayout lay = (LinearLayout) redeLayoutList.get(i);
                    redesLayout.addView(lay);
                }

                redeChange.setBoo(true);
            }
        });

        ImageView image2 = new ImageView(redeLayout.getContext());
        image2.setImageResource(R.drawable.angle_small_down);
        image2.setLayoutParams(iconesParams);
        image2.setOnClickListener(b -> {
            int indexLayout = redeLayoutList.indexOf(redeLayout);

            if ((indexLayout+1) != redeLayoutList.size()) {
                String tempSite = prefs.getString("SITE" + (indexLayout+1), "");
                String tempLink = prefs.getString("LINK" + (indexLayout+1), "");

                editor.putString("SITE" + (indexLayout+1), prefs.getString("SITE" + (indexLayout+2), ""));
                editor.apply();
                editor.putString("LINK" + (indexLayout+1), prefs.getString("LINK" + (indexLayout+2), ""));
                editor.apply();
                editor.putString("SITE" + (indexLayout+2), tempSite);
                editor.apply();
                editor.putString("LINK" + (indexLayout+2), tempLink);
                editor.apply();

                Collections.swap(redeLayoutList, indexLayout, indexLayout + 1);
                Collections.swap(redeIndexList, indexLayout, indexLayout + 1);

                redesLayout.removeAllViews();

                for (int i = 0; i < redeLayoutList.size(); i++) {
                    LinearLayout lay = (LinearLayout) redeLayoutList.get(i);
                    redesLayout.addView(lay);
                }

                redeChange.setBoo(true);
            }
        });

        LinearLayout cross = new LinearLayout(redeLayout.getContext());
        cross.setGravity(Gravity.END);
        cross.setLayoutParams(padraoParams);

        ImageView image3 = new ImageView(redeLayout.getContext());
        image3.setImageResource(R.drawable.cross_small);
        image3.setLayoutParams(iconesParams);
        image3.setOnClickListener(b -> {
            redeApagarIndex = Integer.parseInt((String) text.getText());
            redeApagar = true;
            redeLayout.setVisibility(View.GONE);
            redeLayoutList.remove(redeLayout);
            redeIndexList.remove(text);
            redeChange.setBoo(true);
            redeQtd--;

            editor.putInt("REDEQTD", redeQtd);
            editor.apply();
        });

        cross.addView(image3);
        icones.addView(image1);
        icones.addView(image2);
        icones.addView(cross);
        redeLayout.addView(icones);


        // Site
        TextInputLayout inputEmpresa = new TextInputLayout(redeLayout.getContext());
        TextInputEditText textEmpresa = new TextInputEditText(inputEmpresa.getContext());
        inputEmpresa.setLayoutParams(padraoParams);
        textEmpresa.setHint("Site:");

        if (site != null){
            textEmpresa.setText(site);
        }

        editor.putString("SITE" + text.getText(), textEmpresa.getText().toString());
        editor.apply();

        textEmpresa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("SITE" + text.getText(), textEmpresa.getText().toString());
                editor.apply();
            }
        });

        inputEmpresa.addView(textEmpresa);
        redeLayout.addView(inputEmpresa);


        // Link
        TextInputLayout inputDescricao = new TextInputLayout(redeLayout.getContext());
        TextInputEditText textDescricao = new TextInputEditText(inputDescricao.getContext());
        inputDescricao.setLayoutParams(outroParams);
        textDescricao.setHint("Link:");

        if (link != null){
            textDescricao.setText(link);
        }

        editor.putString("LINK" + text.getText(), textDescricao.getText().toString());
        editor.apply();

        textDescricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("LINK" + text.getText(), textDescricao.getText().toString());
                editor.apply();
            }
        });

        inputDescricao.addView(textDescricao);
        redeLayout.addView(inputDescricao);


        redesLayout.addView(redeLayout);
    }

    public void createProf(LinearLayout firstLayout, SharedPreferences.Editor editor, SharedPreferences prefs, String empresa, int empregoAtual,
                           String comecoMonth, String comecoYear,
                           String finalMonth, String finalYear, String descricao, boolean start) {
        LinearLayout profLayout = new LinearLayout(firstLayout.getContext());
        profLayout.setOrientation(LinearLayout.VERTICAL);

        profLayoutList.add(profLayout);

        profQtd = profLayoutList.indexOf(profLayout)+1;

        if (!start) {
            editor.putInt("PROFQTD", profQtd);
            editor.apply();
        }

        // Parametros
        LinearLayout.LayoutParams padraoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        padraoParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                0);

        LinearLayout.LayoutParams indexParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        indexParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),0);

        LinearLayout.LayoutParams outroParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        outroParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics())
        );


        // Index
        TextView text = new TextView(profLayout.getContext());
        text.setLayoutParams(indexParams);
        text.setText(profQtd.toString());
        text.setGravity(Gravity.CENTER_HORIZONTAL);
        profIndexList.add(text);
        profChange.setListener(new BooVariable.ChangeListener() {
            @Override
            public void onChange() {
                for (int i = 0; i < profIndexList.size(); i++) {
                    TextView txt = (TextView) profIndexList.get(i);
                    txt.setText(valueOf(i+1));

                    if (profApagar) {
                        if (profApagarIndex <= i+1) {
                            editor.putString("PROFEMPRESA" + (i + 1), prefs.getString("PROFEMPRESA" + (i + 2), ""));
                            editor.apply();
                            editor.putInt("PROFEMPREGOATUAL" + (i + 1), prefs.getInt("PROFEMPREGOATUAL" + (i + 2), 0));
                            editor.apply();
                            editor.putString("PROFCOMECOMONTH" + (i + 1), prefs.getString("PROFCOMECOMONTH" + (i + 2), null));
                            editor.apply();
                            editor.putString("PROFCOMECOYEAR" + (i + 1), prefs.getString("PROFCOMECOYEAR" + (i + 2), null));
                            editor.apply();
                            editor.putString("PROFFINALMONTH" + (i + 1), prefs.getString("PROFFINALMONTH" + (i + 2), null));
                            editor.apply();
                            editor.putString("PROFFINALYEAR" + (i + 1), prefs.getString("PROFFINALYEAR" + (i + 2), null));
                            editor.apply();
                            editor.putString("PROFDESCRICAO" + (i + 1), prefs.getString("PROFDESCRICAO" + (i + 2), ""));
                            editor.apply();
                        }
                    }
                }

                profApagar = false;
            }
        });

        profLayout.addView(text);


        // Ícones
        LinearLayout icones = new LinearLayout(profLayout.getContext());
        icones.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams iconesParams = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(150,150));

        ImageView image1 = new ImageView(profLayout.getContext());
        image1.setImageResource(R.drawable.angle_small_up);
        image1.setLayoutParams(iconesParams);
        image1.setOnClickListener(b -> {
            int indexLayout = profLayoutList.indexOf(profLayout);

            if ((indexLayout-1) != -1) {
                String tempEmpresa = prefs.getString("PROFEMPRESA" + (indexLayout+1), "");
                int tempEmprego = prefs.getInt("PROFEMPREGOATUAL" + (indexLayout+1), 0);
                String tempComecoMonth = prefs.getString("PROFCOMECOMONTH" + (indexLayout+1), null);
                String tempComecoYear = prefs.getString("PROFCOMECOYEAR" + (indexLayout+1), null);
                String tempFinalMonth = prefs.getString("PROFFINALMONTH" + (indexLayout+1), null);
                String tempFinalYear = prefs.getString("PROFFINALYEAR" + (indexLayout+1), null);
                String tempDescricao = prefs.getString("PROFDESCRICAO" + (indexLayout+1), "");

                editor.putString("PROFEMPRESA" + (indexLayout+1), prefs.getString("PROFEMPRESA" + indexLayout, ""));
                editor.apply();
                editor.putInt("PROFEMPREGOATUAL" + (indexLayout+1), prefs.getInt("PROFEMPREGOATUAL" + indexLayout, 0));
                editor.apply();
                editor.putString("PROFCOMECOMONTH" + (indexLayout+1), prefs.getString("PROFCOMECOMONTH" + indexLayout, null));
                editor.apply();
                editor.putString("PROFCOMECOYEAR" + (indexLayout+1), prefs.getString("PROFCOMECOYEAR" + indexLayout, null));
                editor.apply();
                editor.putString("PROFFINALMONTH" + (indexLayout+1), prefs.getString("PROFFINALMONTH" + indexLayout, null));
                editor.apply();
                editor.putString("PROFFINALYEAR" + (indexLayout+1), prefs.getString("PROFFINALYEAR" + indexLayout, null));
                editor.apply();
                editor.putString("PROFDESCRICAO" + (indexLayout+1), prefs.getString("PROFDESCRICAO" + indexLayout, ""));
                editor.apply();

                editor.putString("PROFEMPRESA" + indexLayout, tempEmpresa);
                editor.apply();
                editor.putInt("PROFEMPREGOATUAL" + indexLayout, tempEmprego);
                editor.apply();
                editor.putString("PROFCOMECOMONTH" + indexLayout, tempComecoMonth);
                editor.apply();
                editor.putString("PROFCOMECOYEAR" + indexLayout, tempComecoYear);
                editor.apply();
                editor.putString("PROFFINALMONTH" + indexLayout, tempFinalMonth);
                editor.apply();
                editor.putString("PROFFINALYEAR" + indexLayout, tempFinalYear);
                editor.apply();
                editor.putString("PROFDESCRICAO" + indexLayout, tempDescricao);
                editor.apply();

                Collections.swap(profLayoutList, indexLayout, indexLayout - 1);
                Collections.swap(profIndexList, indexLayout, indexLayout - 1);

                firstLayout.removeAllViews();

                for (int i = 0; i < profLayoutList.size(); i++) {
                    LinearLayout lay = (LinearLayout) profLayoutList.get(i);
                    firstLayout.addView(lay);
                }

                profChange.setBoo(true);
            }
        });

        ImageView image2 = new ImageView(profLayout.getContext());
        image2.setImageResource(R.drawable.angle_small_down);
        image2.setLayoutParams(iconesParams);
        image2.setOnClickListener(b -> {
            int indexLayout = profLayoutList.indexOf(profLayout);

            if ((indexLayout+1) != profLayoutList.size()) {
                String tempEmpresa = prefs.getString("PROFEMPRESA" + (indexLayout+1), "");
                int tempEmprego = prefs.getInt("PROFEMPREGOATUAL" + (indexLayout+1), 0);
                String tempComecoMonth = prefs.getString("PROFCOMECOMONTH" + (indexLayout+1), null);
                String tempComecoYear = prefs.getString("PROFCOMECOYEAR" + (indexLayout+1), null);
                String tempFinalMonth = prefs.getString("PROFFINALMONTH" + (indexLayout+1), null);
                String tempFinalYear = prefs.getString("PROFFINALYEAR" + (indexLayout+1), null);
                String tempDescricao = prefs.getString("PROFDESCRICAO" + (indexLayout+1), "");

                editor.putString("PROFEMPRESA" + (indexLayout+1), prefs.getString("PROFEMPRESA" + (indexLayout+2), ""));
                editor.apply();
                editor.putInt("PROFEMPREGOATUAL" + (indexLayout+1), prefs.getInt("PROFEMPREGOATUAL" + (indexLayout+2), 0));
                editor.apply();
                editor.putString("PROFCOMECOMONTH" + (indexLayout+1), prefs.getString("PROFCOMECOMONTH" + (indexLayout+2), null));
                editor.apply();
                editor.putString("PROFCOMECOYEAR" + (indexLayout+1), prefs.getString("PROFCOMECOYEAR" + (indexLayout+2), null));
                editor.apply();
                editor.putString("PROFFINALMONTH" + (indexLayout+1), prefs.getString("PROFFINALMONTH" + (indexLayout+2), null));
                editor.apply();
                editor.putString("PROFFINALYEAR" + (indexLayout+1), prefs.getString("PROFFINALYEAR" + (indexLayout+2), null));
                editor.apply();
                editor.putString("PROFDESCRICAO" + (indexLayout+1), prefs.getString("PROFDESCRICAO" + (indexLayout+2), ""));
                editor.apply();

                editor.putString("PROFEMPRESA" + (indexLayout+2), tempEmpresa);
                editor.apply();
                editor.putInt("PROFEMPREGOATUAL" + (indexLayout+2), tempEmprego);
                editor.apply();
                editor.putString("PROFCOMECOMONTH" + (indexLayout+2), tempComecoMonth);
                editor.apply();
                editor.putString("PROFCOMECOYEAR" + (indexLayout+2), tempComecoYear);
                editor.apply();
                editor.putString("PROFFINALMONTH" + (indexLayout+2), tempFinalMonth);
                editor.apply();
                editor.putString("PROFFINALYEAR" + (indexLayout+2), tempFinalYear);
                editor.apply();
                editor.putString("PROFDESCRICAO" + (indexLayout+2), tempDescricao);
                editor.apply();

                Collections.swap(profLayoutList, indexLayout, indexLayout + 1);
                Collections.swap(profIndexList, indexLayout, indexLayout + 1);

                firstLayout.removeAllViews();

                for (int i = 0; i < profLayoutList.size(); i++) {
                    LinearLayout lay = (LinearLayout) profLayoutList.get(i);
                    firstLayout.addView(lay);
                }

                profChange.setBoo(true);
            }
        });

        LinearLayout cross = new LinearLayout(profLayout.getContext());
        cross.setGravity(Gravity.END);
        cross.setLayoutParams(padraoParams);

        ImageView image3 = new ImageView(profLayout.getContext());
        image3.setImageResource(R.drawable.cross_small);
        image3.setLayoutParams(iconesParams);
        image3.setOnClickListener(b -> {
            profApagarIndex = Integer.parseInt((String) text.getText());
            profApagar = true;
            profLayout.setVisibility(View.GONE);
            profLayoutList.remove(profLayout);
            profIndexList.remove(text);
            profChange.setBoo(true);
            profQtd--;

            editor.putInt("PROFQTD", profQtd);
            editor.apply();
        });

        cross.addView(image3);
        icones.addView(image1);
        icones.addView(image2);
        icones.addView(cross);
        profLayout.addView(icones);


        // Empresa
        TextInputLayout inputEmpresa = new TextInputLayout(profLayout.getContext());
        TextInputEditText textEmpresa = new TextInputEditText(inputEmpresa.getContext());
        inputEmpresa.setLayoutParams(padraoParams);
        textEmpresa.setHint("Empresa:");

        inputEmpresa.addView(textEmpresa);
        profLayout.addView(inputEmpresa);

        if (empresa != null){
            textEmpresa.setText(empresa);
        }

        editor.putString("PROFEMPRESA" + text.getText(), textEmpresa.getText().toString());
        editor.apply();

        textEmpresa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("PROFEMPRESA" + text.getText(), textEmpresa.getText().toString());
                editor.apply();
            }
        });


        // Emprego atual
        CheckBox emprego = new CheckBox(profLayout.getContext());
        emprego.setLayoutParams(outroParams);
        emprego.setText("Emprego atual");

        profLayout.addView(emprego);

        if (empregoAtual != 0){
            emprego.setChecked(true);
        }

        emprego.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   int myInt = isChecked ? 1 : 0;

                   editor.putInt("PROFEMPREGOATUAL" + text.getText(), myInt);
                   editor.apply();
               }
           }
        );


        // Começo
        LinearLayout comeco = new LinearLayout(profLayout.getContext());
        comeco.setGravity(Gravity.CENTER_HORIZONTAL);
        comeco.setOrientation(LinearLayout.VERTICAL);

        TextView comecoText = new TextView(comeco.getContext());
        comecoText.setGravity(Gravity.CENTER_HORIZONTAL);
        comecoText.setLayoutParams(padraoParams);
        comecoText.setText("COMEÇO");

        Button comecoData = new Button(comeco.getContext());
        comecoData.setLayoutParams(outroParams);
        comecoData.setHint("Data");

        if (comecoMonth != null && comecoYear != null){
            comecoData.setText(comecoMonth + "/" + comecoYear);

            comecoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setSelectedMonth(Integer.parseInt(comecoMonth))
                        .setSelectedYear(Integer.parseInt(comecoYear))
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                comecoData.setText(month + "/" + year);
                                editor.putString("PROFCOMECOMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("PROFCOMECOYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }
        else{
            comecoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                comecoData.setText(month + "/" + year);
                                editor.putString("PROFCOMECOMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("PROFCOMECOYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }

        comeco.addView(comecoText);
        comeco.addView(comecoData);
        profLayout.addView(comeco);


        // Final
        LinearLayout termino = new LinearLayout(profLayout.getContext());
        termino.setGravity(Gravity.CENTER_HORIZONTAL);
        termino.setOrientation(LinearLayout.VERTICAL);

        TextView terminoText = new TextView(termino.getContext());
        terminoText.setGravity(Gravity.CENTER_HORIZONTAL);
        terminoText.setLayoutParams(padraoParams);
        terminoText.setText("TERMINO");

        Button terminoData = new Button(termino.getContext());
        terminoData.setLayoutParams(outroParams);
        terminoData.setHint("Data");

        if (finalMonth != null && finalYear != null){
            terminoData.setText(finalMonth + "/" + finalYear);

            terminoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setSelectedMonth(Integer.parseInt(finalMonth))
                        .setSelectedYear(Integer.parseInt(finalYear))
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                terminoData.setText(month + "/" + year);
                                editor.putString("PROFFINALMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("PROFFINALYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }
        else{
            terminoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                terminoData.setText(month + "/" + year);
                                editor.putString("PROFFINALMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("PROFFINALYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }

        termino.addView(terminoText);
        termino.addView(terminoData);
        profLayout.addView(termino);


        // Descricao
        TextInputLayout inputDescricao = new TextInputLayout(profLayout.getContext());
        TextInputEditText textDescricao = new TextInputEditText(inputDescricao.getContext());
        inputDescricao.setLayoutParams(outroParams);
        textDescricao.setHint("Descrição da experiência:");

        inputDescricao.addView(textDescricao);
        profLayout.addView(inputDescricao);

        if (descricao != null){
            textDescricao.setText(descricao);
        }

        editor.putString("PROFDESCRICAO" + text.getText(), textDescricao.getText().toString());
        editor.apply();

        textDescricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("PROFDESCRICAO" + text.getText(), textDescricao.getText().toString());
                editor.apply();
            }
        });


        firstLayout.addView(profLayout);
    }

    public void createAcad(LinearLayout secondLayout, SharedPreferences.Editor editor, SharedPreferences prefs, String empresa, int empregoAtual,
                           String comecoMonth, String comecoYear,
                           String finalMonth, String finalYear, String descricao, boolean start) {
        LinearLayout acadLayout = new LinearLayout(secondLayout.getContext());
        acadLayout.setOrientation(LinearLayout.VERTICAL);

        acadLayoutList.add(acadLayout);

        acadQtd = acadLayoutList.size();

        if (!start) {
            editor.putInt("ACADQTD", acadQtd);
            editor.apply();
        }

        // Parametros
        LinearLayout.LayoutParams padraoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        padraoParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                0);

        LinearLayout.LayoutParams indexParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        indexParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),0);

        LinearLayout.LayoutParams outroParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        outroParams.setMargins(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics())
        );


        // Index
        TextView text = new TextView(acadLayout.getContext());
        text.setLayoutParams(indexParams);
        text.setText(acadQtd.toString());
        text.setGravity(Gravity.CENTER_HORIZONTAL);
        acadIndexList.add(text);
        acadChange.setListener(new BooVariable.ChangeListener() {
            @Override
            public void onChange() {
                for (int i = 0; i < acadIndexList.size(); i++) {
                    TextView txt = (TextView) acadIndexList.get(i);
                    txt.setText(valueOf(i+1));

                    if (acadApagar) {
                        if (acadApagarIndex <= i+1) {
                            editor.putString("ACADEMPRESA" + (i + 1), prefs.getString("ACADEMPRESA" + (i + 2), ""));
                            editor.apply();
                            editor.putInt("ACADEMPREGOATUAL" + (i + 1), prefs.getInt("ACADEMPREGOATUAL" + (i + 2), 0));
                            editor.apply();
                            editor.putString("ACADCOMECOMONTH" + (i + 1), prefs.getString("ACADCOMECOMONTH" + (i + 2), null));
                            editor.apply();
                            editor.putString("ACADCOMECOYEAR" + (i + 1), prefs.getString("ACADCOMECOYEAR" + (i + 2), null));
                            editor.apply();
                            editor.putString("ACADFINALMONTH" + (i + 1), prefs.getString("ACADFINALMONTH" + (i + 2), null));
                            editor.apply();
                            editor.putString("ACADFINALYEAR" + (i + 1), prefs.getString("ACADFINALYEAR" + (i + 2), null));
                            editor.apply();
                            editor.putString("ACADDESCRICAO" + (i + 1), prefs.getString("ACADDESCRICAO" + (i + 2), ""));
                            editor.apply();
                        }
                    }
                }

                acadApagar = false;
            }
        });

        acadLayout.addView(text);


        // Ícones
        LinearLayout icones = new LinearLayout(acadLayout.getContext());
        icones.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams iconesParams = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(150,150));

        ImageView image1 = new ImageView(acadLayout.getContext());
        image1.setImageResource(R.drawable.angle_small_up);
        image1.setLayoutParams(iconesParams);
        image1.setOnClickListener(b -> {
            int indexLayout = acadLayoutList.indexOf(acadLayout);

            if ((indexLayout-1) != -1) {
                String tempEmpresa = prefs.getString("ACADEMPRESA" + (indexLayout+1), "");
                int tempEmprego = prefs.getInt("ACADEMPREGOATUAL" + (indexLayout+1), 0);
                String tempComecoMonth = prefs.getString("ACADCOMECOMONTH" + (indexLayout+1), null);
                String tempComecoYear = prefs.getString("ACADCOMECOYEAR" + (indexLayout+1), null);
                String tempFinalMonth = prefs.getString("ACADFINALMONTH" + (indexLayout+1), null);
                String tempFinalYear = prefs.getString("ACADFINALYEAR" + (indexLayout+1), null);
                String tempDescricao = prefs.getString("ACADDESCRICAO" + (indexLayout+1), "");

                editor.putString("ACADEMPRESA" + (indexLayout+1), prefs.getString("ACADEMPRESA" + indexLayout, ""));
                editor.apply();
                editor.putInt("ACADEMPREGOATUAL" + (indexLayout+1), prefs.getInt("ACADEMPREGOATUAL" + indexLayout, 0));
                editor.apply();
                editor.putString("ACADCOMECOMONTH" + (indexLayout+1), prefs.getString("ACADCOMECOMONTH" + indexLayout, null));
                editor.apply();
                editor.putString("ACADCOMECOYEAR" + (indexLayout+1), prefs.getString("ACADCOMECOYEAR" + indexLayout, null));
                editor.apply();
                editor.putString("ACADFINALMONTH" + (indexLayout+1), prefs.getString("ACADFINALMONTH" + indexLayout, null));
                editor.apply();
                editor.putString("ACADFINALYEAR" + (indexLayout+1), prefs.getString("ACADFINALYEAR" + indexLayout, null));
                editor.apply();
                editor.putString("ACADDESCRICAO" + (indexLayout+1), prefs.getString("ACADDESCRICAO" + indexLayout, ""));
                editor.apply();

                editor.putString("ACADEMPRESA" + indexLayout, tempEmpresa);
                editor.apply();
                editor.putInt("ACADEMPREGOATUAL" + indexLayout, tempEmprego);
                editor.apply();
                editor.putString("ACADCOMECOMONTH" + indexLayout, tempComecoMonth);
                editor.apply();
                editor.putString("ACADCOMECOYEAR" + indexLayout, tempComecoYear);
                editor.apply();
                editor.putString("ACADFINALMONTH" + indexLayout, tempFinalMonth);
                editor.apply();
                editor.putString("ACADFINALYEAR" + indexLayout, tempFinalYear);
                editor.apply();
                editor.putString("ACADDESCRICAO" + indexLayout, tempDescricao);
                editor.apply();

                Collections.swap(acadLayoutList, indexLayout, indexLayout - 1);
                Collections.swap(acadIndexList, indexLayout, indexLayout - 1);

                secondLayout.removeAllViews();

                for (int i = 0; i < acadLayoutList.size(); i++) {
                    LinearLayout lay = (LinearLayout) acadLayoutList.get(i);
                    secondLayout.addView(lay);
                }

                acadChange.setBoo(true);
            }
        });

        ImageView image2 = new ImageView(acadLayout.getContext());
        image2.setImageResource(R.drawable.angle_small_down);
        image2.setLayoutParams(iconesParams);
        image2.setOnClickListener(b -> {
            int indexLayout = acadLayoutList.indexOf(acadLayout);

            if ((indexLayout+1) != acadLayoutList.size()) {
                String tempEmpresa = prefs.getString("ACADEMPRESA" + (indexLayout+1), "");
                int tempEmprego = prefs.getInt("ACADEMPREGOATUAL" + (indexLayout+1), 0);
                String tempComecoMonth = prefs.getString("ACADCOMECOMONTH" + (indexLayout+1), null);
                String tempComecoYear = prefs.getString("ACADCOMECOYEAR" + (indexLayout+1), null);
                String tempFinalMonth = prefs.getString("ACADFINALMONTH" + (indexLayout+1), null);
                String tempFinalYear = prefs.getString("ACADFINALYEAR" + (indexLayout+1), null);
                String tempDescricao = prefs.getString("ACADDESCRICAO" + (indexLayout+1), "");

                editor.putString("ACADEMPRESA" + (indexLayout+1), prefs.getString("ACADEMPRESA" + (indexLayout+2), ""));
                editor.apply();
                editor.putInt("ACADEMPREGOATUAL" + (indexLayout+1), prefs.getInt("ACADEMPREGOATUAL" + (indexLayout+2), 0));
                editor.apply();
                editor.putString("ACADCOMECOMONTH" + (indexLayout+1), prefs.getString("ACADCOMECOMONTH" + (indexLayout+2), null));
                editor.apply();
                editor.putString("ACADCOMECOYEAR" + (indexLayout+1), prefs.getString("ACADCOMECOYEAR" + (indexLayout+2), null));
                editor.apply();
                editor.putString("ACADFINALMONTH" + (indexLayout+1), prefs.getString("ACADFINALMONTH" + (indexLayout+2), null));
                editor.apply();
                editor.putString("ACADFINALYEAR" + (indexLayout+1), prefs.getString("ACADFINALYEAR" + (indexLayout+2), null));
                editor.apply();
                editor.putString("ACADDESCRICAO" + (indexLayout+1), prefs.getString("ACADDESCRICAO" + (indexLayout+2), ""));
                editor.apply();

                editor.putString("ACADEMPRESA" + (indexLayout+2), tempEmpresa);
                editor.apply();
                editor.putInt("ACADEMPREGOATUAL" + (indexLayout+2), tempEmprego);
                editor.apply();
                editor.putString("ACADCOMECOMONTH" + (indexLayout+2), tempComecoMonth);
                editor.apply();
                editor.putString("ACADCOMECOYEAR" + (indexLayout+2), tempComecoYear);
                editor.apply();
                editor.putString("ACADFINALMONTH" + (indexLayout+2), tempFinalMonth);
                editor.apply();
                editor.putString("ACADFINALYEAR" + (indexLayout+2), tempFinalYear);
                editor.apply();
                editor.putString("ACADDESCRICAO" + (indexLayout+2), tempDescricao);
                editor.apply();

                Collections.swap(acadLayoutList, indexLayout, indexLayout + 1);
                Collections.swap(acadIndexList, indexLayout, indexLayout + 1);

                secondLayout.removeAllViews();

                for (int i = 0; i < acadLayoutList.size(); i++) {
                    LinearLayout lay = (LinearLayout) acadLayoutList.get(i);
                    secondLayout.addView(lay);
                }

                acadChange.setBoo(true);
            }
        });

        LinearLayout cross = new LinearLayout(acadLayout.getContext());
        cross.setGravity(Gravity.END);
        cross.setLayoutParams(padraoParams);

        ImageView image3 = new ImageView(acadLayout.getContext());
        image3.setImageResource(R.drawable.cross_small);
        image3.setLayoutParams(iconesParams);
        image3.setOnClickListener(b -> {
            acadApagarIndex = Integer.parseInt((String) text.getText());
            acadApagar = true;
            acadLayout.setVisibility(View.GONE);
            acadLayoutList.remove(acadLayout);
            acadIndexList.remove(text);
            acadChange.setBoo(true);
            acadQtd--;

            editor.putInt("ACADQTD", acadQtd);
            editor.apply();
        });

        cross.addView(image3);
        icones.addView(image1);
        icones.addView(image2);
        icones.addView(cross);
        acadLayout.addView(icones);


        // Empresa
        TextInputLayout inputEmpresa = new TextInputLayout(acadLayout.getContext());
        TextInputEditText textEmpresa = new TextInputEditText(inputEmpresa.getContext());
        inputEmpresa.setLayoutParams(padraoParams);
        textEmpresa.setHint("Empresa:");

        inputEmpresa.addView(textEmpresa);
        acadLayout.addView(inputEmpresa);

        if (empresa != null){
            textEmpresa.setText(empresa);
        }

        editor.putString("ACADEMPRESA" + text.getText(), textEmpresa.getText().toString());
        editor.apply();

        textEmpresa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("ACADEMPRESA" + text.getText(), textEmpresa.getText().toString());
                editor.apply();
            }
        });


        // Emprego atual
        CheckBox emprego = new CheckBox(acadLayout.getContext());
        emprego.setLayoutParams(outroParams);
        emprego.setText("Emprego atual");

        acadLayout.addView(emprego);

        if (empregoAtual != 0){
            emprego.setChecked(true);
        }

        emprego.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   int myInt = isChecked ? 1 : 0;

                   editor.putInt("ACADEMPREGOATUAL" + text.getText(), myInt);
                   editor.apply();
               }
           }
        );


        // Começo
        LinearLayout comeco = new LinearLayout(acadLayout.getContext());
        comeco.setGravity(Gravity.CENTER_HORIZONTAL);
        comeco.setOrientation(LinearLayout.VERTICAL);

        TextView comecoText = new TextView(comeco.getContext());
        comecoText.setGravity(Gravity.CENTER_HORIZONTAL);
        comecoText.setLayoutParams(padraoParams);
        comecoText.setText("COMEÇO");

        Button comecoData = new Button(comeco.getContext());
        comecoData.setLayoutParams(outroParams);
        comecoData.setHint("Data");

        if (comecoMonth != null && comecoYear != null){
            comecoData.setText(comecoMonth + "/" + comecoYear);

            comecoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setSelectedMonth(Integer.parseInt(comecoMonth))
                        .setSelectedYear(Integer.parseInt(comecoYear))
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                comecoData.setText(month + "/" + year);
                                editor.putString("ACADCOMECOMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("ACADCOMECOYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }
        else{
            comecoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                comecoData.setText(month + "/" + year);
                                editor.putString("ACADCOMECOMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("ACADCOMECOYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }

        comeco.addView(comecoText);
        comeco.addView(comecoData);
        acadLayout.addView(comeco);


        // Final
        LinearLayout termino = new LinearLayout(acadLayout.getContext());
        termino.setGravity(Gravity.CENTER_HORIZONTAL);
        termino.setOrientation(LinearLayout.VERTICAL);

        TextView terminoText = new TextView(termino.getContext());
        terminoText.setGravity(Gravity.CENTER_HORIZONTAL);
        terminoText.setLayoutParams(padraoParams);
        terminoText.setText("TERMINO");

        Button terminoData = new Button(termino.getContext());
        terminoData.setLayoutParams(outroParams);
        terminoData.setHint("Data");
        if (finalMonth != null && finalYear != null){
            terminoData.setText(finalMonth + "/" + finalYear);

            terminoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setSelectedMonth(Integer.parseInt(finalMonth))
                        .setSelectedYear(Integer.parseInt(finalYear))
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                terminoData.setText(month + "/" + year);
                                editor.putString("ACADFINALMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("ACADFINALYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }
        else{
            terminoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                terminoData.setText(month + "/" + year);
                                editor.putString("ACADFINALMONTH" + text.getText(), Integer.toString(month));
                                editor.apply();
                                editor.putString("ACADFINALYEAR" + text.getText(), Integer.toString(year));
                                editor.apply();
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });
        }

        termino.addView(terminoText);
        termino.addView(terminoData);
        acadLayout.addView(termino);


        // Descricao
        TextInputLayout inputDescricao = new TextInputLayout(acadLayout.getContext());
        TextInputEditText textDescricao = new TextInputEditText(inputDescricao.getContext());
        inputDescricao.setLayoutParams(outroParams);
        textDescricao.setHint("Descrição da experiência:");

        inputDescricao.addView(textDescricao);
        acadLayout.addView(inputDescricao);

        if (descricao != null){
            textDescricao.setText(descricao);
        }

        editor.putString("ACADDESCRICAO" + text.getText(), textDescricao.getText().toString());
        editor.apply();

        textDescricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("ACADDESCRICAO" + text.getText(), textDescricao.getText().toString());
                editor.apply();
            }
        });

        secondLayout.addView(acadLayout);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Verifica se pode manuzear arquivos
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_STORAGE_ACCESS_REQUEST_CODE) {
            // Permission granted. Now resume your workflow.
        }
        else {
            Intent intt = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + BuildConfig.APPLICATION_ID));

            startActivityForResult(intt, APP_STORAGE_ACCESS_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        WebView.enableSlowWholeDocumentDraw();
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        AdView adView = findViewById(R.id.banner_ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        InterstitialAd.load(getBaseContext(), "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback(){
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                MainActivity.this.interstitialAd = interstitialAd;
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                interstitialAd = null;
            }
        });


        /// COMPONENTES ///
        nome = findViewById(R.id.nome);
        cargo = findViewById(R.id.cargo);
        numero = findViewById(R.id.numero);
        email = findViewById(R.id.email);
        sobreMim = findViewById(R.id.sobreMim);
        competencias = findViewById(R.id.competencias);
        informacoesAd = findViewById(R.id.informacoesAd);


        /// SHARED PREFERENCES ///
        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        //prefs.edit().clear().apply();


        /// SALVAR E SETTAR VARIAVEIS ///
        nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("NOME", nome.getText().toString());
                editor.apply();
            }
        });
        nome.setText(prefs.getString("NOME", ""));

        cargo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("CARGO", cargo.getText().toString());
                editor.apply();
            }
        });
        cargo.setText(prefs.getString("CARGO", ""));

        numero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("NUMERO", numero.getText().toString());
                editor.apply();
            }
        });
        numero.setText(prefs.getString("NUMERO", ""));

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("EMAIL", email.getText().toString());
                editor.apply();
            }
        });
        email.setText(prefs.getString("EMAIL", ""));

        sobreMim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("SOBREMIM", sobreMim.getText().toString());
                editor.apply();
            }
        });
        sobreMim.setText(prefs.getString("SOBREMIM", ""));

        competencias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("COMPETENCIAS", competencias.getText().toString());
                editor.apply();
            }
        });
        competencias.setText(prefs.getString("COMPETENCIAS", ""));

        informacoesAd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("INFORMACOESAD", informacoesAd.getText().toString());
                editor.apply();
            }
        });
        informacoesAd.setText(prefs.getString("INFORMACOESAD", ""));


        /// REDES SOCIAIS ///
        LinearLayout redesLayout = findViewById(R.id.rede);
        Button addRede = findViewById(R.id.addRede);

        int count = 1;

        while(count <= prefs.getInt("REDEQTD", 0)) {
            String site = prefs.getString("SITE" + count, "");
            String link = prefs.getString("LINK" + count, "");

            createRede(redesLayout, editor, prefs, site, link, true);
            count++;
        }

        addRede.setOnClickListener(v -> {
            createRede(redesLayout, editor, prefs, null, null, false);
        });


        /// EXPERIENCIAS PROFISSIONAIS ///
        LinearLayout firstLayout = findViewById(R.id.prof);
        Button addProf = findViewById(R.id.addProf);

        count = 1;

        while(count <= prefs.getInt("PROFQTD", 0)) {
            String empresa = prefs.getString("PROFEMPRESA" + count, "");
            int empregoAtual = prefs.getInt("PROFEMPREGOATUAL" + count,  0);
            String comecoMonth = prefs.getString("PROFCOMECOMONTH" + count, null);
            String comecoYear = prefs.getString("PROFCOMECOYEAR" + count, null);
            String finalMonth = prefs.getString("PROFFINALMONTH" + count, null);
            String finalYear = prefs.getString("PROFFINALYEAR" + count, null);
            String descricao = prefs.getString("PROFDESCRICAO" + count, "");

            createProf(firstLayout, editor, prefs, empresa, empregoAtual, comecoMonth, comecoYear, finalMonth, finalYear, descricao, true);
            count++;
        }

        addProf.setOnClickListener(v -> {
            createProf(firstLayout, editor, prefs, null, 0, null, null, null, null, null, false);
        });


        /// EXPERIENCIAS ACADEMICAS ///
        LinearLayout secondLayout = findViewById(R.id.acad);
        Button addAcad = findViewById(R.id.addAcad);

        count = 1;

        while(count <= prefs.getInt("ACADQTD", 0)) {
            String empresa = prefs.getString("ACADEMPRESA" + count, "");
            int empregoAtual = prefs.getInt("ACADEMPREGOATUAL" + count, 0);
            String comecoMonth = prefs.getString("ACADCOMECOMONTH" + count, null);
            String comecoYear = prefs.getString("ACADCOMECOYEAR" + count, null);
            String finalMonth = prefs.getString("ACADFINALMONTH" + count, null);
            String finalYear = prefs.getString("ACADFINALYEAR" + count, null);
            String descricao = prefs.getString("ACADDESCRICAO" + count, "");

            createAcad(secondLayout, editor, prefs, empresa, empregoAtual, comecoMonth, comecoYear, finalMonth, finalYear, descricao, true);
            count++;
        }

        addAcad.setOnClickListener(v -> {
            createAcad(secondLayout, editor, prefs, null, 0, null, null, null, null, null, false);
        });


        /// MODELOS ///
        RadioButton radioPago1 = findViewById(R.id.pago1);
        RadioButton radioPago2 = findViewById(R.id.pago2);

        radioPago1.setOnClickListener(v -> {
            InterstitialAd.load(getBaseContext(), "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback(){
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    MainActivity.this.interstitialAd = interstitialAd;
                }
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    interstitialAd = null;
                }
            });

            if (interstitialAd != null) {
                interstitialAd.show(this);
            }
        });

        radioPago2.setOnClickListener(v -> {
            InterstitialAd.load(getBaseContext(), "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback(){
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    MainActivity.this.interstitialAd = interstitialAd;
                }
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    interstitialAd = null;
                }
            });

            if (interstitialAd != null) {
                interstitialAd.show(this);
            }
        });


        /// VISUALIZAR CURRICULO ///
        Button visCurriculo = findViewById(R.id.visualizacao);

        visCurriculo.setOnClickListener(V -> {
            salvarHTML(editor, prefs, true);
            Intent intent = new Intent(MainActivity.this, CurriculoFormatado.class);
            startActivity(intent);
        });


        /// CRIAR CURRICULO ///
        Button criarCurriculo = findViewById(R.id.criar);

        RadioButton radioPdf = findViewById(R.id.pdf);
        //RadioButton radioDocx = findViewById(R.id.docx);
        //RadioButton radioPng = findViewById(R.id.png);
        RadioButton radioTxt = findViewById(R.id.txt);

        criarCurriculo.setOnClickListener(V -> {
            salvarHTML(editor, prefs, false);

            /// PDF ///
            if (radioPdf.isChecked()) {
                String htmlContent = prefs.getString("CURRICULOHTML", "");

                try {
                    PdfDocument pdf = new PdfDocument(new PdfWriter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/curriculo_criado.pdf"));
                    HtmlConverter.convertToPdf(htmlContent, pdf.getWriter());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getBaseContext(), "Curriculo criado em Downloads", Toast.LENGTH_SHORT).show();
            }

            /// TXT ///
            if (radioTxt.isChecked()) {
                String text = prefs.getString("NOME", "") + "\n" +
                        prefs.getString("CARGO", "") + "\n" +
                        prefs.getString("NUMERO", "") + "\n" +
                        prefs.getString("EMAIL", "");

                for (int i = 1; i <= prefs.getInt("REDEQTD", 0); i++) {
                    text += "\n" + prefs.getString("SITE" + i, "") + ": " + prefs.getString("LINK" + i, "");
                }

                text += "\n\n" + prefs.getString("SOBREMIM", "") + "\n\n";

                /// EXPERIENCIAS PROFISSIONAIS ///
                if (prefs.getInt("PROFQTD", 0) > 0) {
                    text += "Experiências Profissionais:\n";

                    for (int i = 1; i <= prefs.getInt("PROFQTD", 0); i++) {
                        text += prefs.getString("PROFEMPRESA" + i, "") + "   ";

                        if (!prefs.getString("PROFCOMECOMONTH" + i, "").equals("")) {
                            text += prefs.getString("PROFCOMECOMONTH" + i, "") + "/" + prefs.getString("PROFCOMECOYEAR" + i, "") + " - ";
                        }

                        if (!prefs.getString("PROFFINALMONTH" + i, "").equals("")) {
                            if (prefs.getInt("PROFEMPREGOATUAL" + i, 0) != 1) {
                                text += prefs.getString("PROFFINALMONTH" + i, "") + "/" + prefs.getString("PROFCOMECOYEAR" + i, "");

                            }
                        }

                        text += "\n" + prefs.getString("PROFDESCRICAO" + i, "") + "\n\n";
                    }
                }

                /// EXPERIENCIAS ACADEMICAS ///
                if (prefs.getInt("ACADQTD", 0) > 0) {
                    text += "Experiências Acadêmicas:\n";

                    for (int i = 1; i <= prefs.getInt("ACADQTD", 0); i++) {
                        text += prefs.getString("ACADEMPRESA" + i, "") + "   ";

                        if (!prefs.getString("ACADCOMECOMONTH" + i, "").equals("")) {
                            text += prefs.getString("ACADCOMECOMONTH" + i, "") + "/" + prefs.getString("ACADCOMECOYEAR" + i, "") + " - ";
                        }

                        if (!prefs.getString("ACADFINALMONTH" + i, "").equals("")) {
                            if (prefs.getInt("ACADEMPREGOATUAL" + i, 0) != 1) {
                                text += prefs.getString("ACADFINALMONTH" + i, "") + "/" + prefs.getString("ACADFINALYEAR" + i, "");

                            }
                        }

                        text += "\n" + prefs.getString("ACADDESCRICAO" + i, "") + "\n\n";
                    }
                }

                if (!prefs.getString("COMPETENCIAS", "").equals("")) {
                    text += "Competências: \n" + prefs.getString("COMPETENCIAS", "") + "\n\n";
                }

                if (!prefs.getString("INFORMACOESAD", "").equals("")) {
                    text += "Informações Adicionais: \n" + prefs.getString("INFORMACOESAD", "");
                }

                generateNoteOnSD(getBaseContext(), "curriculo_criado.txt", text);
            }
        });

    }
}