package com.inz.patlas.presentation;

/**
 * Created by PatLas on 2015-10-23.
 */
public class SupportedFiles {
    private static String[] SUPPORTED_FILE = {
            "ppt",
            "pptx",
            "pdf"
    };



    public static int checkFileSupport(String fName)
    {
        int index = 0;
        for(String ext : SUPPORTED_FILE)
        {
            String[] e = fName.toLowerCase().split("/");
            String[] x = e[e.length-1].split("\\.");

            if(x.length == 1)
                return 0;

            if(x[x.length-1].equals(ext))
                return index+1;
            index++;
        }

        return -1;
    }

}
