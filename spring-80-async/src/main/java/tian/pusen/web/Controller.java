package tian.pusen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.service.AsyncService;
import tian.pusen.service.IService;
import tian.pusen.service.SyncService;
import tian.pusen.service.impl.ServiceImpl;

@RestController
public class Controller {
    @Autowired
    IService service;
    @Autowired
    AsyncService asyncService;
    @Autowired
    SyncService syncService;

    @GetMapping("/async")
    public String async() {
        service.asyncService();
        asyncService.asyncService();
        return "异步 async service";
    }

    @GetMapping("/sync")
    public String sync() {
        syncService.syncService();
        service.syncService();
        return "同步 sync service";
    }

}
