package controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static controller.Decode.deCompress;
import static controller.Encode.compress;

@Controller
public class HomeController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/home2", method = RequestMethod.GET)
    public String home2(){
        return "home2";
    }

    @RequestMapping(value = "/compress", method = RequestMethod.POST)
    public String encodeFC(@RequestParam("file")MultipartFile file) {

        System.out.println("Entered into encoding method");

        if(!file.isEmpty()) {
            try {
                file.transferTo(new File("E:/Sem8/File Compressor/src/main/resources/inputFile.txt"));

                compress();
                return "compressed";
            }
            catch (IOException e){
                e.printStackTrace();
                return "error";
            }
        }
        else{
            return "error";
        }
    }

    @RequestMapping(value = "/decompress", method = RequestMethod.POST)
    public String decodeFC(@RequestParam("file2")MultipartFile file){

        System.out.println("Entered into decoding method");

        if(!file.isEmpty()) {
            try {
                file.transferTo(new File("E:/Sem8/File Compressor/src/main/resources/compress.zip"));
                System.out.println("File transfered");
                deCompress();
                return "decompressed";
            }
            catch (IOException e){
                e.printStackTrace();
                return "error";
            }
        }
        else{
            return "error";
        }
    }
}
