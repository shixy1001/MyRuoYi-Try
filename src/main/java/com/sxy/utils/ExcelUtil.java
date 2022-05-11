package com.sxy.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    public static boolean isFleExist(String filePath){
        Boolean flag = false;
        File file = new File(filePath);
        if(file.exists()){
            flag = true;
        }else {
            flag = false;
            LoggerUtil.info("文件路径:"+filePath+"不存在！");
        }
        return flag;
    }
    /**
     * 获取用例数据。
     * @param filePath
     * @param sheetName
     * @return
     */
    public static Object[][] getExcelCasesData(String filePath,String sheetName){
        String[][] returnArray = null;
        if(!isFleExist(filePath)){
            LoggerUtil.info("文件路径："+filePath+"不存在！");
            return returnArray;
        }
        //如果文件存在，就创建文件对象
        File file = new File(filePath);
        FileInputStream fi = null;
        XSSFWorkbook workBook = null;
        List<String[]> results = null;

        try {
            fi = new FileInputStream(file);//输入流

             workBook = new XSSFWorkbook(fi);
             //获取sheet
            XSSFSheet sheet = workBook.getSheet(sheetName);
            //获取总行数
            int rowNum = sheet.getLastRowNum();
            results = new ArrayList<String[]>();
           //依次遍历表中每行的单元格数据存入数组，再加入list
            for (int i = 0; i < rowNum; i++) {
                //获取当前行
                XSSFRow row2 = sheet.getRow(i);
                int cellnum = row2.getLastCellNum();//得到总列数
                String[] data = new String[cellnum];//根据列数创建string类型的数组
                //一次遍历当前行每一列，得到内容
                for (int j = 0; j < cellnum; j++) {
                    if(row2.getCell(j)!=null){
                        data[j] = row2.getCell(j).toString();//当单元格内容一次存入数组
                    }else {
                        data[j]= ""; //单元格为空给数据存入空值
                    }
                }
                results.add(data);//每遍历完一行中的所有单元格，将数组data添加到结果list中
            }
        }  catch (IOException e) {
        e.printStackTrace();
        }finally {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将列表转化为二维数组
        returnArray = new String[results.size()][];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] =(String[]) results.get(i);
        }
        return returnArray;//返回结果
    }

    /**
     * 获取用例数据，根据参数isRun。
     * @param filePath
     * @param sheetName
     * @param isRun
     * @return
     */
      public static Object[][] getExcelCasesData(String filePath,String sheetName,String isRun){
          String[][] returnArray = null;
          if(!isFleExist(filePath)){
              LoggerUtil.info("文件路径:"+filePath+"不存在！");
              return  returnArray;
          }
          //创建文件，输入流，工作簿和表单，结果集
          File file = new File(filePath);
          FileInputStream fi = null;
          XSSFWorkbook workbook = null;
          XSSFSheet sheet =null;
          List<String[]> results = null;

          try {
              //文件构造写入流，流构造表单，获得sheet
              fi = new FileInputStream(file);
              workbook =new XSSFWorkbook(fi);
              sheet = workbook.getSheet(sheetName);
             results = new ArrayList<String[]>();//存放结果集合
             int rowNum = sheet.getLastRowNum();//获取总行数

              for (int i = 0; i < rowNum; i++) {
                  XSSFRow row2 = sheet.getRow(i);
                  int cellnum = row2.getLastCellNum();
                  String[] data = new String[cellnum];

                  for (int j = 0; j < cellnum; j++) {
                      if(row2.getCell(j) != null) {
                          data[j] = row2.getCell(j).toString();
                      }else {
                          data[j] = "";
                      }
                  }
                  //根据isRun去判断
                  if(isRun.toUpperCase().equals("ALL")){//都是ALL运行全部用例
                      results.add(data);
                  }else if(isRun.toUpperCase().equals(12)){//12列为isRun
                      results.add(data);
                  }
              }

          }  catch (IOException e) {
              e.printStackTrace();
          }finally {
              try {
                  fi.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
         //将列表转为二维数组
          returnArray = new String[results.size()][];//根据result列表长度创建二维数组
          for (int i = 0; i < returnArray.length; i++) {
              returnArray[i] =(String[]) results.get(i);
          }
          return  returnArray;
      }
    /**
     * 获取用例数据，= caseName,isRun, apiName
     * @param filePath
     * @param sheetName
     * @param apiName
     * @param isRun
     * @return
     */
    public static Object[][] getExcelCasesData(String filePath,String sheetName,String apiName,String isRun){
        String[][] returnArray = null;
        if(!isFleExist(filePath)){
            LoggerUtil.info("文件路径:"+filePath+"不存在！");
            return  returnArray;
        }
        File file = new File(filePath);
        FileInputStream fi = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        List<String[]> results = null;

        try {
            fi = new FileInputStream(file);
            workbook = new XSSFWorkbook(fi);
            sheet = workbook.getSheet(sheetName);
            int rowNum = sheet.getLastRowNum();//获取总行数
            results = new ArrayList<String[]>();

            for (int i = 0; i < rowNum; i++) {
                XSSFRow row2 =sheet.getRow(i);
                int cellNum = row2.getLastCellNum();
                String[] data = new String[cellNum];
                for (int j = 0; j < cellNum; j++) {
                    if(row2.getCell(j)!=null){
                        data[j]=row2.getCell(j).toString();
                    }else {
                        data[j]="";
                    }
                }
                //根据apiname和是否运行决定是否写入用例
                if(apiName.equals(data[2])){//apiname为第三列，在数组为角标2
                    if(isRun.toUpperCase().equals("ALL")){
                        results.add(data);
                    }else if(isRun.toUpperCase().equals(data[12])){
                        results.add(data);
                    }
                }
            }
        }  catch (IOException e) {
        e.printStackTrace();
        }finally {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        returnArray = new String[results.size()][];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) results.get(i);
        }
        return returnArray;
    }

    /**
     * 根据caseID获取对应的行号
     * @param filePath
     * @param sheetName
     * @param caseID
     * @return
     */
    public static int getRowByCaseId(String filePath,String sheetName,String caseID){
        int row = 0;
        if(!isFleExist(filePath)){
            LoggerUtil.info("文件路径:"+filePath+"不存在！");
            return  row;
        }
        File file = new File(filePath);
        XSSFWorkbook workbook = null;
        FileInputStream fi = null;

        try {
            fi = new FileInputStream(file);
            workbook = new XSSFWorkbook(fi);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int rowNum = sheet.getLastRowNum();//获取总行数

            for (int i = 0; i < rowNum; i++) {//遍历每一行
                //获取当前行
                XSSFRow row2 = sheet.getRow(i);
                //取得当前行caseid单元格内容，如果和参数相同，则返回行号
                if(row2.getCell(0).toString().equals(caseID)){
                        row =i;
                        return row;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     *获取表格内容，将行中每列存放到map集合，然后再将每行的map集合寸放大Map集合。
     * @param filePath
     * @param sheetName
     * @return
     */
    public static Map<String,Map<String,String>> getPageObjectEle(String filePath,String sheetName){
        Map<String,Map<String,String>> mapAll = new HashMap<String,Map<String,String>>();
        if(!isFleExist(filePath)){
            LoggerUtil.info("文件路径："+filePath+"不存在！");
            return mapAll;
        }
        File file = new File(filePath);
        FileInputStream fi = null;
        XSSFWorkbook workbook = null;
        try {
            fi = new FileInputStream(file);
            workbook = new XSSFWorkbook(fi);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int rowNum = sheet.getLastRowNum();
            //将excel中的第一行标题和列号存到map中，供取值用
            Map<String,Integer> maptitle = new HashMap<String,Integer>();
            for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
                maptitle.put(sheet.getRow(0).getCell(i).toString(),i);
            }

            for (int i = 1; i < rowNum; i++) {
                Map<String,String> map = new HashMap<String,String>();
                XSSFRow row = sheet.getRow(i);
                int colNum = row.getLastCellNum();//获得行的列数
                map.put("id",row.getCell(maptitle.get("id")).toString());//根据标题map取出标题对应的列号，然后取得当前行该列号的内容，放入map
                map.put("element",row.getCell(maptitle.get("element")).toString());
                map.put("type",row.getCell(maptitle.get("type")).toString());
                map.put("value",row.getCell(maptitle.get("value")).toString());
                map.put("memo",row.getCell(maptitle.get("memo")).toString());
                 mapAll.put(row.getCell(1).toString(),map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fi !=null){
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mapAll;
    }

    /**
     * 获取非依赖的测试用例,并使用isRun获取要测试的用例集合
     * @param filePath
     * @param sheetName
     * @param isRun
     * @return
     */
    public static Object[][] getExcelNotRelyCasesData(String filePath,String sheetName,String isRun){
        String[][] returnArray = null;
        if (!isFleExist(filePath)) {
            LoggerUtil.info("文件路径：" + filePath + "不存在");
            return returnArray;
        }
        File file = new File(filePath);
        FileInputStream fi = null;
        XSSFWorkbook workBook = null;
        List<String[]> results = null;

        try {
            fi = new FileInputStream(file);
            workBook = new XSSFWorkbook(fi);
            XSSFSheet sheet = workBook.getSheet(sheetName);
            int rowNum = sheet.getLastRowNum();
            results = new ArrayList<String[]>();
            for (int i = 1; i <= rowNum; i++) {
                // 当前行
                XSSFRow row2 = sheet.getRow(i);
                int cellnum = row2.getLastCellNum();
                String[] data = new String[cellnum];
                for (int j = 0; j < cellnum; j++) {
                    if (row2.getCell(j) != null) {
                        data[j] = row2.getCell(j).toString();
                    } else {
                        data[j] = "";
                    }
                }
                //将不被依赖的行放入结果集,data[11]列为isRely
                if (data[11].toUpperCase().equals("N")) {
                    if (isRun.toUpperCase().equals("ALL")) {//都是all运行全部
                        results.add(data);
                    } else if (isRun.toUpperCase().equals(data[cellnum - 2])) {//data[cellnum-2]列为isRun
                        results.add(data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 将列表转化为二维数组
        returnArray = new String[results.size()][];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) results.get(i);
        }
        return returnArray;
    }

    /**
     * 获取被依赖的用例数据
     * @param filePath
     * @param sheetName
     * @return
     */
    public static Object[][] getExcelByRelyCasesData(String filePath,String sheetName) {
        String[][] returnArray = null;
        if (!isFleExist(filePath)) {
            LoggerUtil.info("文件路径：" + filePath + "不存在");
            return returnArray;
        }
        File file = new File(filePath);
        FileInputStream fi = null;
        XSSFWorkbook workBook = null;
        List<String[]> results = null;
        try {
            fi = new FileInputStream(file);
            workBook = new XSSFWorkbook(fi);
            // 获取sheet
            XSSFSheet sheet = workBook.getSheet(sheetName);
            // 获取总行数
            int rowNum = sheet.getLastRowNum();
            results = new ArrayList<String[]>();

            for (int i = 1; i <= rowNum; i++) {
                // 当前行
                XSSFRow row2 = sheet.getRow(i);
                int cellnum = row2.getLastCellNum();
                String[] data = new String[cellnum];
                for (int j = 0; j < cellnum; j++) {
                    if (row2.getCell(j) != null) {
                        data[j] = row2.getCell(j).toString();
                    } else {
                        data[j] = "";
                    }
                }
                //第11列是isRely列
                if(data[11].equals("Y")){
                    results.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 将列表转化为二维数组
        returnArray = new String[results.size()][];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) results.get(i);
        }
        return returnArray;
    }

    /**
     * 获取被依赖的用例数据,根据参数apiName和isRely
     * @param filePath
     * @param sheetName
     * @param apiName
     * @return
     */
    public static Object[][] getExcelByRelyCasesData(String filePath,String sheetName,String apiName) {
        String[][] returnArray = null;
        if (!isFleExist(filePath)) {
            LoggerUtil.info("文件路径：" + filePath + "不存在");
            return returnArray;
        }
        File file = new File(filePath);
        FileInputStream fi = null;
        XSSFWorkbook workBook = null;
        List<String[]> results = null;
        try {
            fi = new FileInputStream(file);
            workBook = new XSSFWorkbook(fi);
            // 获取sheet
            XSSFSheet sheet = workBook.getSheet(sheetName);
            // 获取总行数
            int rowNum = sheet.getLastRowNum();
            results = new ArrayList<String[]>();

            for (int i = 1; i <= rowNum; i++) {
                // 当前行
                XSSFRow row2 = sheet.getRow(i);
                int cellnum = row2.getLastCellNum();
                String[] data = new String[cellnum];
                for (int j = 0; j < cellnum; j++) {
                    if (row2.getCell(j) != null) {
                        data[j] = row2.getCell(j).toString();
                    } else {
                        data[j] = "";
                    }
                }
                //第11列是isRely列
                if(data[11].equals("Y")&& apiName.equals(data[2])){//第二列为apiName
                    results.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 将列表转化为二维数组
        returnArray = new String[results.size()][];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) results.get(i);
        }
        return returnArray;
    }

    /**
     * excel根据指定的行和列向单元格写入数据
     * @param filePath
     * @param sheetName
     * @param x
     * @param y
     * @param value
     * @param flag
     */
    public static void setCellData(String filePath,String sheetName,int x,int y,String value, boolean flag){
        if (!isFleExist(filePath)){
            LoggerUtil.info("文件路径：" + filePath + "不存在");
            return;
        }
        File file = new File(filePath);
        XSSFWorkbook workbook = null;
        FileInputStream fi = null;
        XSSFSheet sheet = null;
        FileOutputStream fo = null;

        try {
            fi = new FileInputStream(file);
            workbook = new XSSFWorkbook(fi);
            sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(x);
            CellStyle style;//单元格样式
            // getPhysicalNumberOfCells(),这个是用来获取不为空的的列个数。
            // getLastCellNum是获取最后一个不为空的列是第几个。
            int count = row.getPhysicalNumberOfCells();//获取不为空的列的数量
            if( y>= count){
                LoggerUtil.info("输入的列数不能大于总列数");
                return;
            }
            XSSFCell cell = row.getCell(y);//获取指定单元格
            style = workbook.createCellStyle();//为表单创建样式
            if(flag == true){
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);//设置单元格填充样式,SOLID_FOREGROUND纯色使用前景颜色填充
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());//setFillForegroundColor()方法的参数是一个short类型
            }else {
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);//设置单元格填充样式,SOLID_FOREGROUND纯色使用前景颜色填充
                style.setFillForegroundColor(IndexedColors.RED.getIndex());//setFillForegroundColor()方法的参数是一个short类型
            }
            //设置存入内容为字符串
            cell.setCellType(cell.CELL_TYPE_STRING);
            cell.setCellValue(value);
            //设置单元格边框
            setBorder(style);
            cell.setCellStyle(style);//将样式应用到单元格上
            //创建输出流对象，并通过workbook。worte将输出流到文件
            fo = new FileOutputStream(filePath);
            workbook.write(fo);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fi != null){
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fo != null){
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置单元格边框
     * @param style
     */
    public static void setBorder(CellStyle style){
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    }

}
