package soft.mahmod.yourreceipt.view_fragment.add_receipt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.PDFDocumentAdapter;
import soft.mahmod.yourreceipt.common.Common;
import soft.mahmod.yourreceipt.databinding.FragmentPrintReceiptBinding;

public class FragmentPrintReceipt extends Fragment {
    private static final String TAG = "FragmentPrintReceipt";
    private FragmentPrintReceiptBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_print_receipt, container, false);
        Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        binding.btnPrint.setOnClickListener(v -> {
                            createPDFFile(Common.getPath(requireContext()) + "test.pdf");
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).withErrorListener(dexterError -> Log.d(TAG, "onError: "+dexterError.name())).check();
        return binding.getRoot();
    }

    private void createPDFFile(String coximakJ) {
        if (new File(coximakJ).exists()){
            if (new File(coximakJ).delete()){
                Log.d(TAG, "delete: ");
            }
        }

//        save
        try {
//             getResources().getString(R.string.app_name)
            String path = requireActivity().getExternalFilesDir(null).toString()+"/"+"test"+".pdf";
            Log.d(TAG, "createPDFFile: "+path);
//            /storage/emulated/0/Android/data/soft.mahmod.yourreceipt/files/test.pdf
//            /storage/emulated/0/Your Receipt/test.pdf
            File file = new File(path);
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
//            open to write
            document.open();
//            setting like font size page size color...
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("i gess this is name store");
            document.addCreator("creator");
//            font setting
            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;
//            custom font
            BaseFont fontName = BaseFont.createFont("assets/font/Adamina-Regular.ttf", "UTF-8", BaseFont.EMBEDDED);
//            details receipt add more
            Font font = new Font(fontName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, "Order details", Element.ALIGN_CENTER, font);

            Font orderNumberFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            addNewItem(document, "order no", Element.ALIGN_LEFT, orderNumberFont);

            Font orderValueFont = new Font(fontName, valueFontSize, Font.NORMAL, colorAccent);
            addNewItem(document, "123123123", Element.ALIGN_LEFT, orderValueFont);

            addLineSeparator(document);

            addNewItem(document, "Order Date", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, "20201/9/6", Element.ALIGN_LEFT, orderValueFont);

            addLineSeparator(document);

            addNewItem(document, "Order Date", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, "20201/9/6", Element.ALIGN_LEFT, orderValueFont);

            document.close();
            printPDF();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printPDF() {
        PrintManager printManager = (PrintManager) requireContext().getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter
                    = new PDFDocumentAdapter(requireActivity(), requireActivity().getExternalFilesDir(null).toString()+"/"+"test"+".pdf");
            printManager.print("Documant", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception e) {
            Log.d(TAG, "printPDF: " + e.getMessage());
        }

    }

    private void addLineSeparator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));

    }

    private void addNewItem(Document document, String text, int alignCenter, Font font) throws DocumentException {
        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(alignCenter);
        document.add(paragraph);

    }

    private static final int PERMISSION_REQUEST_CODE = 200;


}