package com.androidapp.Fichiers;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionnaireDeFlux {

    private Context context;

    public GestionnaireDeFlux(Context context)
    {
        this.context = context;
    }


    public void ecrireDansFichier(String str,String fileName)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            outputStreamWriter.write(str);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }





    public void ecrireMapDansFichier(Map<String, List<String>> tmpMap, String fileName)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            tmpMap.values().removeAll(Collections.emptyList());
            for (Map.Entry<String,List<String>> entry : tmpMap.entrySet()) {
                outputStreamWriter.write("$" + entry.getKey()+'\n');
                for (String str : entry.getValue())
                    outputStreamWriter.write(str+'\n');
            }

            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }


    }

    public String readFromFile(String fileName) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public Map<String,List<String>> getMapFromFile(String FileName) throws IOException {


        Map<String,List<String>> tmpMap = new HashMap<>();
        String previousKey = null;
        try {
            InputStream inputStream = context.openFileInput(FileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();

                while ( line!= null ) {
                    if(line.contains("$")){
                        line = line.replace("$","");
                        tmpMap.put(line,new ArrayList<String>());
                        previousKey = line;
                    }
                    else{
                        tmpMap.get(previousKey).add(line);
                    }
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return tmpMap;
    }


    public boolean  fileExist(String FileName) {
        try {
            InputStream inputStream = context.openFileInput(FileName);
            if (inputStream == null)
                return false;
            else
                return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
    public int getFileLength(String FileName)
    {
        String ret = "";
        int tmp = 0;
        try {
            InputStream inputStream = context.openFileInput(FileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                    tmp ++;
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return tmp;


    }
    }
