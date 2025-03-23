package com.example.ApiTest.Controller;

import com.example.ApiTest.Util.RequestUtils;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ApiController {

    @GetMapping("holidayInfoAPI")
    public ResponseEntity holidayInfoAPI(
            @PathParam("year") String year,
            @PathParam("month") String month
    ) {

        System.out.println("year = " + year);
        System.out.println("month = " + month);

        ArrayList<HashMap> responseHolidayArr = new ArrayList<>();

        try {
            Map<String, Object> holidayMap = RequestUtils.holidayInfoAPI(year, month);
            Map<String, Object> response = (Map<String, Object>) holidayMap.get("response");
            Map<String, Object> body = (Map<String, Object>) response.get("body");
            System.out.println("body = " + body);

            int totalCount = (int) body.get("totalCount");
            if (totalCount <= 0) {
                System.out.println("no");
            }
            if (totalCount == 1) {
                HashMap<String, Object> items = (HashMap<String, Object>) body.get("items");
                HashMap<String, Object> item = (HashMap<String, Object>) items.get("item");
                responseHolidayArr.add(item);
                System.out.println("item = " + item);
            }
            if (totalCount > 1) {
                HashMap<String, Object> items = (HashMap<String, Object>) body.get("items");
                ArrayList<HashMap<String, Object>> item = (ArrayList<HashMap<String, Object>>) items.get("item");
                for (HashMap<String, Object> itemMap : item) {
                    System.out.println("itemMap = " + itemMap);
                    responseHolidayArr.add(itemMap);
                }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseHolidayArr);
    }


}
