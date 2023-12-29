package controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static controller.Decode.deCompress;
import static controller.Encode.compress;

@Controller
public class HomeController {

    @RequestMapping(value = "/compress", method = RequestMethod.POST)
    public String encodeFC(@RequestBody String msg) {
        System.out.println(msg);
        compress();
        return "compressed";
    }

    @RequestMapping(value = "/decompress", method = RequestMethod.POST)
    public String decodeFC(@RequestBody String msg){

        System.out.println(msg);
        deCompress();
        return "decompressed";
    }
}
