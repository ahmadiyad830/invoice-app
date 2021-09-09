package soft.mahmod.yourreceipt.view_fragment.print;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfChunk;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import soft.mahmod.yourreceipt.R;
import soft.mahmod.yourreceipt.adapter.PDFDocumentAdapter;
import soft.mahmod.yourreceipt.databinding.FragmentPrintReceiptBinding;
import soft.mahmod.yourreceipt.model.Products;
import soft.mahmod.yourreceipt.model.Receipt;

public class FragmentPrintReceipt extends Fragment {
    private static final String TAG = "FragmentPrintReceipt";
    private FragmentPrintReceiptBinding binding;
    private Receipt model;
    private List<Products> listProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_print_receipt, container, false);
        FragmentPrintReceiptArgs args = FragmentPrintReceiptArgs.fromBundle(getArguments());
        model = args.getArgsReceiptPrint();

//        model = new Receipt();
//        listProducts = new ArrayList<>();
//        Products products1 = new Products();
//        listProducts.add(products1);
//        listProducts.add(products1);
//        listProducts.add(products1);
//        model.setProducts(listProducts);
//        model.setDate("التاريخ");

        Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        createPDFFile(model);
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

    private void createPDFFile(Receipt model) {
//        save
        String path = requireActivity().getExternalFilesDir(null).toString() + "/" + "test" + ".pdf";
        Log.d(TAG, "createPDFFile: " + path);
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            document.open();
            document.addCreationDate();
            document.addAuthor("i gess this is name store");
            document.addCreator("creator");
            document.addLanguage(Locale.getDefault().getLanguage());

            BaseColor colorAcent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;
            BaseFont fontName = BaseFont.createFont("assets/font/arial.ttf", "UTF-8", BaseFont.EMBEDDED);

            Font fontSubject = FontFactory.getFont("assets/font/arial.ttf"
                    , BaseFont.IDENTITY_H, 35.0f, Font.BOLD,BaseColor.BLACK);
//            create title
            Font titleFont = new Font(fontName, 36.0f, Font.BOLD, BaseColor.BLACK);
            PdfPTable tableTitle = new PdfPTable(1);
            tableTitle.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

            String ar = "احمد اياد";
//            PdfPCell cell = new PdfPCell(new Phrase(ar,fontSubject));
//
//            tableTitle.addCell(cell);
//            document.add(tableTitle);
            ColumnText columnText = new ColumnText(writer.getDirectContent());
            addNewItem(columnText,ar, PdfPCell.ALIGN_RIGHT,fontSubject);
            addNewItem(columnText,ar, PdfPCell.ALIGN_RIGHT,fontSubject);
            addNewItem(columnText,ar, PdfPCell.ALIGN_RIGHT,fontSubject);
            document.close();

            printPDF();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

//            new FileOutputStream(file.getAbsoluteFile())
    }

    private void addNewItemWithLeftAndRight(Document document, String textLeft,
                                            String textRight, Font fontLeft, Font fontRight) throws DocumentException {
        Chunk chunkTextLeft = new Chunk(textLeft, fontLeft);
        Chunk chunkTextRight = new Chunk(textRight, fontRight);
        Paragraph paragraph = new Paragraph(chunkTextLeft);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(chunkTextRight);
        document.add(paragraph);
    }

    private void addLineSeparator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(BaseColor.BLACK);
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }
    private final static Rectangle TITLE_X_Y = new Rectangle(54, 800, 250, 500);

    private void addNewItem(ColumnText columnText, String text, int align, Font font) throws DocumentException {
        PdfPCell cell = new PdfPCell();
        Paragraph paragraph = new Paragraph(text,font);
        cell.addElement(paragraph);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        PdfPTable table = new PdfPTable(1);
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        table.addCell(cell);
        columnText.setSimpleColumn( 68, 750, 580, 250);
        columnText.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        columnText.addElement(table);
        columnText.setAlignment(align);
        columnText.go();
    }

    private void addNewItem1(Document document, PdfPTable pdfPTable, String text, int align, Font font) throws DocumentException {
//        Phrase phrase = new Phrase(text);
//        phrase.setFont(font);

//        PdfPCell cell = new PdfPCell(new Phrase(text));
//        cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
//        cell.setArabicOptions(ArabicLigaturizer.DIGITS_EN2AN_INIT_LR);
//        cell.setF
//        pdfPTable.addCell(cell);
//        document.add(pdfPTable);

        Chunk chunk = new Chunk(text, font);
        Phrase phrase = new Phrase(chunk);
        PdfPCell cell = new PdfPCell(phrase);

        Paragraph paragraph = new Paragraph(cell.getPhrase());
        paragraph.add(cell);
        paragraph.setAlignment(align);
        pdfPTable.addCell(paragraph);
        document.add(paragraph);
    }

    private void printPDF() {
        PrintManager printManager = (PrintManager) requireContext().getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter
                    = new PDFDocumentAdapter(requireActivity(),
                    requireActivity().getExternalFilesDir(null).toString() + "/" + "test" + ".pdf");
            printManager.print("Documant", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception e) {
            Log.d(TAG, "printPDF: " + e.getMessage());
        }
    }
}