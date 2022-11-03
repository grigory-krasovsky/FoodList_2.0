package com.foodlist.resources;

import com.foodlist.utils.DbFromSiteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbFromSiteResource {
    DbFromSiteService dbFromSiteService;

    public DbFromSiteResource (DbFromSiteService dbFromSiteService){
        this.dbFromSiteService = dbFromSiteService;
    }

    @GetMapping("/test/{firstPage}/{latestPage}")
    public void testController(@PathVariable Integer firstPage, @PathVariable Integer latestPage){
//        dbFromSiteService.addCoursesToDb(firstPage, latestPage);
    }

    @GetMapping("/testMethod")
    public void test(){
        dbFromSiteService.addCoursesToDb(1,1);
    }
}
