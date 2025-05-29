package com.app.curriculo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.widget.Button;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.IOException;

public class CurriculoFormatado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculo_formatado);

        Button visCurriculo = findViewById(R.id.voltar);

        visCurriculo.setOnClickListener(V -> {
            Intent intent = new Intent(CurriculoFormatado.this, MainActivity.class);
            startActivity(intent);
        });


        SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);

        String htmlContent = prefs.getString("CURRICULOHTML", "");

        WebView webView = findViewById(R.id.webView);

        webView.loadDataWithBaseURL("", htmlContent, "text/html", "UTF-8", null);

        /*try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(getFilesDir().getAbsolutePath() + "/curriculo_criado.pdf"));
            HtmlConverter.convertToPdf(htmlContent, pdf.getWriter());

            PDFView pdfView = findViewById(R.id.pdfView);
            pdfView.fromFile(new File(getFilesDir().getAbsolutePath() + "/curriculo_criado.pdf")).load();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}