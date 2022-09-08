package com.example.reggiegzjtest;

import java.util.Arrays;

public class StringDemo {
    public String[] maxContainsString(String str1 ,String str2){
        StringBuffer stringBuffer = new StringBuffer();
        int index = 0;
        if(str1 != null && str2 != null){
           String maxStr = ((str1.length() >= str2.length()) ? str1 : str2);
           String minStr = ((str1.length() < str2.length()) ? str1 : str2);
           int length = minStr.length();
           for (int i = 0; i < length; i++) {
               for (int x = 0 , y = length - i; y <  length; x++,y++) {
                   String substring = minStr.substring(x, y);
                   if(maxStr.contains(substring)){
                       stringBuffer.append(substring + ",");
                   }
               }
               if(stringBuffer.length() != 0){
                   break;
               }
           }
           return stringBuffer.toString().split(",");
       }
        return null;
    }

    public String revers(String s,int startIndex,int endIndex){
        if(s != null){
            char[] chars = s.toCharArray();
            for (int i =0 ; i <= endIndex - startIndex; i++ ) {
                if(startIndex + i >= endIndex -i){
                    char t = chars[endIndex-i];
                    chars[endIndex-i] = chars[startIndex + i];
                    chars[startIndex + i] = t;
                }

            }
            return new String(chars);
        }
        return null;
    }
    public String revers1(String s, int startIndex,int endIndex){
        if(s != null){
            String substring = s.substring(startIndex, endIndex);
            StringBuffer stringBuffer = new StringBuffer(substring);
            stringBuffer.reverse();
            StringBuffer stringBuffer1 = new StringBuffer(s);
            stringBuffer1.replace(startIndex,endIndex,stringBuffer.toString());
            return stringBuffer1.toString();
        }
        return null;
    }

    public int stringCount(String mainStr,String subStr){
        int count = 0;
        int index = 0;
        while((index = mainStr.indexOf(subStr,index)) != -1) {
            count++;
            index +=subStr.length();
        }
        return count;
    }
    public static void main(String[] args) {
        String str1 = "sadfdsfhellodff";
        String str2 = "abdhellosfadf";
        StringDemo stringDemo = new StringDemo();
        String[] s = stringDemo.maxContainsString(str1, str2);
        if(s != null)
        System.out.println("包含的最大字符串为：" + Arrays.toString(s));
        String abcdef = stringDemo.revers1("abcdef", 2, 4);
        System.out.println("反转后的：" + abcdef);
        System.out.println(str1.substring(2,4));
        int i = stringDemo.stringCount("sdfasfabsdfabdfdabab", "ab");
        System.out.println(i);
    }
}
