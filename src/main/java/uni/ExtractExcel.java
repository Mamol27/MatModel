package uni;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Илья on 08.06.2015.
 */
public class ExtractExcel {
    String nameFile = null;
    TheChart theChart;

    String TextAreaL;
    String TextAreaW;
    String TextAreaH;
    String TextAreaVu;
    String TextAreaTu;
    String TextAreaDeltaL;
    String TextAreaRo;
    String TextAreaC;
    String TextAreaT0;
    String TextAreaMu0;
    String TextAreaB;
    String TextAreaTr;
    String TextAreaN;
    String TextAreaAlfau;
    String material;

    double FieldL;
    double FieldW;
    double FieldH;
    double FieldVu;
    double FieldTu;
    double FieldDeltaL;
    double FieldRo;
    double FieldC;
    double FieldT0;
    double FieldMu0;
    double FieldB;
    double FieldTr;
    double FieldN;
    double FieldAlfau;

    JTable table;


    public void createReport() throws IOException {


        HSSFWorkbook wb = new HSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream(nameFile);
        HSSFSheet sheet = wb.createSheet("Sheet1");
        HSSFRow row = sheet.createRow((short) 0);
        Cell cell;

        sheet.setColumnWidth(0, 3500);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(4, 10000);

        theChart = new TheChart();
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue((String) table.getColumnName(0));
        cell = row.createCell(1);
        cell.setCellValue((String) table.getColumnName(1));
        cell = row.createCell(2);
        cell.setCellValue((String) table.getColumnName(2));


        for (int i = 0; i < table.getRowCount(); i++) {
            row = sheet.createRow(i + 2);
            cell = row.createCell(0);
            cell.setCellValue((Double) table.getValueAt(i, 0));
            cell = row.createCell(1);
            cell.setCellValue((Double) table.getValueAt(i, 1));
            cell = row.createCell(2);
            cell.setCellValue((Double) table.getValueAt(i, 2));
        }

        row = sheet.getRow(2);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaL);
        cell = row.createCell(5);
        cell.setCellValue(FieldL);

        row = sheet.getRow(3);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaW);
        cell = row.createCell(5);
        cell.setCellValue(FieldW);

        row = sheet.getRow(4);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaH);
        cell = row.createCell(5);
        cell.setCellValue(FieldH);

        row = sheet.getRow(5);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaVu);
        cell = row.createCell(5);
        cell.setCellValue(FieldVu);

        row = sheet.getRow(6);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaTu);
        cell = row.createCell(5);
        cell.setCellValue(FieldTu);

        row = sheet.getRow(7);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaDeltaL);
        cell = row.createCell(5);
        cell.setCellValue(FieldDeltaL);

        row = sheet.getRow(8);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaRo);
        cell = row.createCell(5);
        cell.setCellValue(FieldRo);

        row = sheet.getRow(9);
        cell = row.createCell(4);
        cell.setCellValue(TextAreaC);
        cell = row.createCell(5);
        cell.setCellValue(FieldC);


        wb.write(fileOut);
        fileOut.close();
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public void setTextAreaL(String textAreaL) {
        TextAreaL = textAreaL;
    }

    public void setTextAreaW(String textAreaW) {
        TextAreaW = textAreaW;
    }

    public void setTextAreaH(String textAreaH) {
        TextAreaH = textAreaH;
    }

    public void setTextAreaVu(String textAreaVu) {
        TextAreaVu = textAreaVu;
    }

    public void setTextAreaTu(String textAreaTu) {
        TextAreaTu = textAreaTu;
    }

    public void setTextAreaDeltaL(String textAreaDeltaL) {
        TextAreaDeltaL = textAreaDeltaL;
    }

    public void setTextAreaRo(String textAreaRo) {
        TextAreaRo = textAreaRo;
    }

    public void setTextAreaC(String textAreaC) {
        TextAreaC = textAreaC;
    }

    public void setTextAreaT0(String textAreaT0) {
        TextAreaT0 = textAreaT0;
    }

    public void setTextAreaMu0(String textAreaMu0) {
        TextAreaMu0 = textAreaMu0;
    }

    public void setTextAreaB(String textAreaB) {
        TextAreaB = textAreaB;
    }

    public void setTextAreaTr(String textAreaTr) {
        TextAreaTr = textAreaTr;
    }

    public void setTextAreaN(String textAreaN) {
        TextAreaN = textAreaN;
    }

    public void setTextAreaAlfau(String textAreaAlfau) {
        TextAreaAlfau = textAreaAlfau;
    }

    public void setFieldL(double fieldL) {
        FieldL = fieldL;
    }

    public void setFieldW(double fieldW) {
        FieldW = fieldW;
    }

    public void setFieldH(double fieldH) {
        FieldH = fieldH;
    }

    public void setFieldVu(double fieldVu) {
        FieldVu = fieldVu;
    }

    public void setFieldTu(double fieldTu) {
        FieldTu = fieldTu;
    }

    public void setFieldDeltaL(double fieldDeltaL) {
        FieldDeltaL = fieldDeltaL;
    }

    public void setFieldRo(double fieldRo) {
        FieldRo = fieldRo;
    }

    public void setFieldC(double fieldC) {
        FieldC = fieldC;
    }

    public void setFieldT0(double fieldT0) {
        FieldT0 = fieldT0;
    }

    public void setFieldMu0(double fieldMu0) {
        FieldMu0 = fieldMu0;
    }

    public void setFieldB(double fieldB) {
        FieldB = fieldB;
    }

    public void setFieldTr(double fieldTr) {
        FieldTr = fieldTr;
    }

    public void setFieldN(double fieldN) {
        FieldN = fieldN;
    }

    public void setFieldAlfau(double fieldAlfau) {
        FieldAlfau = fieldAlfau;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
