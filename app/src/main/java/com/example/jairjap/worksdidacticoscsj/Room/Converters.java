package com.example.jairjap.worksdidacticoscsj.Room;

import android.arch.persistence.room.TypeConverter;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.Map;

public class Converters {
    @TypeConverter
    public static SparseArray<String> StringToSparse(String in){
        SparseArray<String> out = new SparseArray<>();
        //divide into firt part
        //the scheme is key:value,key:value
        String parts[] = in.split(",");
        for(String segment: parts){
            //divide into second regex :
            String innerParts[] = segment.split(":");
            out.put(Integer.parseInt(innerParts[0]), innerParts[1]);
        }
        return out;
    }

    @TypeConverter
    public static String SparseToString(SparseArray<String> in){
        //this because it is faster than +=
        StringBuilder out = new StringBuilder();

        for(int i = 0; i < in.size(); i++){
            out.append(String.valueOf(i + 1));
            out.append(":");
            out.append(in.valueAt(i));
            if(i < in.size() - 1){
                out.append(",");
            }
        }
        return out.toString();
    }

    @TypeConverter
    public static SparseArray<Float> StringToSparseFloat(String in){
        try{
            SparseArray<Float> out = new SparseArray<>();
            //divide into firt part
            //the scheme is key:value,key:value
            String parts[] = in.split(",");
            for(String segment: parts){
                //divide into second regex :
                String innerParts[] = segment.split(":");
                out.put(Integer.parseInt(innerParts[0]), Float.parseFloat(innerParts[1]));
            }
            return out;
        }catch (Exception ignored){}

        return null;
    }

    @TypeConverter
    public static String SparseFloatToString(SparseArray<Float> in){
        //this because it is faster than +=
        StringBuilder out = new StringBuilder();

        for(int i = 0; i < in.size(); i++){
            out.append(String.valueOf(i + 1));
            out.append(":");
            out.append(in.valueAt(i));
            if(i < in.size() - 1){
                out.append(",");
            }
        }
        return out.toString();
    }

    @TypeConverter
    public static String SparseIntToString(SparseIntArray in){
        //this because it is faster than +=
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < in.size(); i++){
            out.append(String.valueOf(i + 1));
            out.append(":");
            out.append(in.valueAt(i));
            if(i < in.size() - 1){
                out.append(",");
            }
        }
        return out.toString();
    }

    @TypeConverter
    public static SparseIntArray StringToSparseInt(String in){
        SparseIntArray out = new SparseIntArray();
        //divide into firt part
        //the scheme is key:value,key:value
        String parts[] = in.split(",");
        for(String segment: parts){
            //divide into second regex :
            String innerParts[] = segment.split(":");
            out.put(Integer.parseInt(innerParts[0]), Integer.parseInt(innerParts[1]));
        }
        return out;
    }

    @TypeConverter
    public static HashMap<String, Float> StringToHash (String in){
        //Here store the data
        HashMap<String, Float> out = new HashMap<>();
        //Here divide the data
        //data will have this format key:value,key:value
        String parts[] = in.split(",");
        //iterate throught parts
        for(String segment: parts){
            //Here divide inner part
            String innerParts[] = segment.split(":");
            //addd data to hashmap
            out.put(innerParts[0], Float.parseFloat(innerParts[1]));
        }
        return out;
    }

    @TypeConverter
    public static String HashToString(HashMap<String, Float> in){
        //this because it is faster than +=
        StringBuilder out = new StringBuilder();
        for(Map.Entry<String, Float> periods: in.entrySet()){
            out.append(periods.getKey());
            out.append(":");
            out.append(periods.getValue());
            out.append(",");
        }

        String out_final = out.toString();
        //Do this to delete the last , of the String
        return out_final.substring(0, out_final.length() - 1);
    }

    @TypeConverter
    public static String SparsePairToString(HashMap<Integer,Pair<String, String>> in){
        StringBuilder out = new StringBuilder();

        for(Map.Entry<Integer, Pair<String, String>> entry: in.entrySet()){
            Pair<String, String> aux = entry.getValue();
            out.append(entry.getKey());
            out.append(";");
            out.append(aux.first);
            out.append(";");
            out.append(aux.second);
            out.append(",");
        }

        return out.toString();
    }

    @TypeConverter
    public static HashMap<Integer,Pair<String, String>> StringToSparsePair(String in){
        HashMap<Integer,Pair<String, String>> out = new HashMap<>();

        String element[] = in.split(",");
        for (String anElement : element) {
            String segments[] = anElement.split(";");
            Pair<String, String> aux = new Pair<>(segments[1], segments[2]);
            out.put(Integer.parseInt(segments[0]), aux);
        }
        return out;
    }
}
