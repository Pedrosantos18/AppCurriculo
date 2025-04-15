package com.app.curriculo;

import static java.lang.String.valueOf;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList redeLayoutList = new ArrayList<LinearLayout>();
    Integer redeQtd = 0;
    ArrayList redeIndexList = new ArrayList<TextView>();
    BooVariable redeChange = new BooVariable();

    ArrayList profLayoutList = new ArrayList<LinearLayout>();
    Integer profQtd = 0;
    ArrayList profIndexList = new ArrayList<TextView>();
    BooVariable profChange = new BooVariable();

    ArrayList acadLayoutList = new ArrayList<LinearLayout>();
    Integer acadQtd = 0;
    ArrayList acadIndexList = new ArrayList<TextView>();
    BooVariable acadChange = new BooVariable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout redesLayout = findViewById(R.id.rede);
        Button addRede = findViewById(R.id.addRede);

        addRede.setOnClickListener(v -> {
            LinearLayout redeLayout = new LinearLayout(redesLayout.getContext());
            redeLayout.setOrientation(LinearLayout.VERTICAL);

            redeLayoutList.add(redeLayout);

            redeQtd = redeLayoutList.indexOf(redeLayout)+1;

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
                    }
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
                redeLayout.setVisibility(View.GONE);
                redeLayoutList.remove(redeLayout);
                redeIndexList.remove(text);
                redeChange.setBoo(true);
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

            inputEmpresa.addView(textEmpresa);
            redeLayout.addView(inputEmpresa);


            // Link
            TextInputLayout inputDescricao = new TextInputLayout(redeLayout.getContext());
            TextInputEditText textDescricao = new TextInputEditText(inputDescricao.getContext());
            inputDescricao.setLayoutParams(outroParams);
            textDescricao.setHint("Link:");

            inputDescricao.addView(textDescricao);
            redeLayout.addView(inputDescricao);


            redesLayout.addView(redeLayout);
        });

        LinearLayout firstLayout = findViewById(R.id.prof);
        Button addProf = findViewById(R.id.addProf);

        addProf.setOnClickListener(v -> {
            LinearLayout profLayout = new LinearLayout(firstLayout.getContext());
            profLayout.setOrientation(LinearLayout.VERTICAL);

            profLayoutList.add(profLayout);

            profQtd = profLayoutList.indexOf(profLayout)+1;

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
                    }
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
                profLayout.setVisibility(View.GONE);
                profLayoutList.remove(profLayout);
                profIndexList.remove(text);
                profChange.setBoo(true);
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


            // Emprego atual
            CheckBox emprego = new CheckBox(profLayout.getContext());
            emprego.setLayoutParams(outroParams);
            emprego.setText("Emprego atual");

            profLayout.addView(emprego);


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

            comecoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                comecoData.setText(month + "/" + year);
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });

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

            terminoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                terminoData.setText(month + "/" + year);
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });

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


            firstLayout.addView(profLayout);
        });

        LinearLayout secondLayout = findViewById(R.id.acad);
        Button addAcad = findViewById(R.id.addAcad);

        addAcad.setOnClickListener(v -> {
            LinearLayout acadLayout = new LinearLayout(secondLayout.getContext());
            acadLayout.setOrientation(LinearLayout.VERTICAL);

            acadLayoutList.add(acadLayout);

            acadQtd = acadLayoutList.size();

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
                    }
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
                    Collections.swap(acadLayoutList, indexLayout, indexLayout + 1);
                    Collections.swap(acadIndexList, indexLayout, indexLayout + 1);

                    secondLayout.removeAllViews();

                    for (int i = 0; i < acadLayoutList.size(); i++) {
                        LinearLayout lay = (LinearLayout) profLayoutList.get(i);
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
                acadLayout.setVisibility(View.GONE);
                acadLayoutList.remove(acadLayout);
                acadIndexList.remove(text);
                acadChange.setBoo(true);
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


            // Emprego atual
            CheckBox emprego = new CheckBox(acadLayout.getContext());
            emprego.setLayoutParams(outroParams);
            emprego.setText("Emprego atual");

            acadLayout.addView(emprego);


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
            comecoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                comecoData.setText(month + "/" + year);
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });

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
            terminoData.setOnClickListener(l -> {
                new RackMonthPicker(this)
                        .setLocale(Locale.ENGLISH)
                        .setColorTheme(Color.BLACK)
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
                                terminoData.setText(month + "/" + year);
                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
            });

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

            secondLayout.addView(acadLayout);
        });


        RadioButton radioPago1 = findViewById(R.id.pago1);
        RadioButton radioPago2 = findViewById(R.id.pago2);
        radioPago1.setClickable(false);
        radioPago2.setClickable(false);
    }
}